package com.linka39.genyundemo.service.impl;

import com.linka39.genyundemo.entity.Article;
import com.linka39.genyundemo.repository.ArticleRepository;
import com.linka39.genyundemo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 帖子Service实现类
 * @author linka39
 */
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public List<Article> list(Integer page, Integer pageSize, Sort.Direction direction, String... properties) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, direction, properties);
        Page<Article> articlePage = articleRepository.findAll(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Predicate predicate=cb.conjunction();
                predicate.getExpressions().add(cb.equal(root.get("state"),1)); // 资源解析成功
                predicate.getExpressions().add(cb.equal(root.get("isIndex"),true)); // es索引添加成功
                return predicate;
            }
        }, pageRequest);
        return articlePage.getContent();
    }

    @Override
    public List<Article> adminList(Article s_article, Integer page, Integer pageSize, Sort.Direction direction, String... properties) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, direction, properties);
        Page<Article> articlePage = articleRepository.findAll(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Predicate predicate=cb.conjunction();
                if(s_article!=null){
                    if(s_article.getId()!=null){
                        predicate.getExpressions().add(cb.equal(root.get("id"),s_article.getId()));
                    }
                    if(s_article.getName()!=null){
                        predicate.getExpressions().add(cb.like(root.get("name"),"%"+s_article.getName()+"%"));
                    }
                }
                return predicate;
            }
        }, pageRequest);
        return articlePage.getContent();
    }

    @Override
    public Long getAdminCount(Article s_article) {
        return articleRepository.count(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Predicate predicate=cb.conjunction();
                if(s_article!=null){
                    if(s_article.getId()!=null){
                        predicate.getExpressions().add(cb.equal(root.get("id"),s_article.getId()));
                    }
                    if(s_article.getName()!=null){
                        predicate.getExpressions().add(cb.like(root.get("name"),"%"+s_article.getName()+"%"));
                    }
                }
                return predicate;
            }
        });
    }

    @Override
    public Long getCount() {
        return articleRepository.count(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Predicate predicate=cb.conjunction();
                predicate.getExpressions().add(cb.equal(root.get("state"),1)); // 资源解析成功
                predicate.getExpressions().add(cb.equal(root.get("isIndex"),true)); // es索引添加成功
                return predicate;
            }
        });
    }

    @Override
    public Article get(Integer id) {
        return articleRepository.getOne(id);
    }

    @Override
    public void save(Article article) {
        articleRepository.save(article);
    }

    @Override
    public void delete(Integer id) {
        articleRepository.deleteById(id);
    }
}
