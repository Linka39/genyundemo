package com.linka39.genyundemo.service;

import com.linka39.genyundemo.entity.Article;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * 帖子Service接口
 * @author linka39
 */
public interface ArticleService {

    /**
     * 分页查询所有的帖子信息
     * @param page
     * @param pageSize
     * @param direction
     * @param properties
     * @return
     */
    public List<Article> list(Integer page, Integer pageSize, Sort.Direction direction, String... properties);


    /**
     * 分页查询所有的帖子管理信息
     * @param page
     * @param pageSize
     * @param direction
     * @param properties
     * @return
     */
    public List<Article> adminList(Article s_article, Integer page, Integer pageSize, Sort.Direction direction, String... properties);

    /**
     * 获取管理信息总记录数
     * @return
     */
    public Long getAdminCount(Article s_article);

    /**
     * 获取总记录数
     * @return
     */
    public Long getCount();

    /**
     * 根据id获取实体
     * @param id
     * @return
     */
    public Article get(Integer id);
    /**
     * 存储实体数据
     * @param article
     * @return
     */
    public void save(Article article);

    /**
     * 根据id获取实体
     * @param id
     * @return
     */
    public void delete(Integer id);


}
