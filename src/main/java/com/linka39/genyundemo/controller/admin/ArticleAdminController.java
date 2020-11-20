package com.linka39.genyundemo.controller.admin;

import com.linka39.genyundemo.entity.Article;
import com.linka39.genyundemo.service.ArticleService;
import com.linka39.genyundemo.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理控制器
 */
@RestController
@RequestMapping("/admin/article")
public class ArticleAdminController {
    @Autowired
    private ArticleService articleService;

    /**
     * 根据条件分页查询
     * @param s_article
     * @param page
     */
    @RequestMapping("/list")
    public Map<String,Object> list(Article s_article, @RequestParam(value = "page",required = false)Integer page, @RequestParam(value = "limit",required = false)Integer limit)throws Exception{
        Map<String,Object> resultMap=new HashMap<String,Object>();
        List<Article> articleList = articleService.adminList(s_article,page,limit, Sort.Direction.DESC,"includeDate");
        Long count = articleService.getAdminCount(s_article);
        resultMap.put("code",0);
        resultMap.put("count",count);
        resultMap.put("data",articleList);
        return resultMap;
    }

    /**
     * 添加资源信息
     * @param article
     * @return
     * @throws Exception
     */
    @RequestMapping("/add")
    public Map<String,Object> add(Article article) throws Exception{
        article.setincludeDate(DateUtil.getCurrentDateStr());
        articleService.save(article);
        Map<String,Object> resultMap=new HashMap<String,Object>();
        resultMap.put("success",true);
        return resultMap;
    }

    /**
     * 删除帖子信息
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public Map<String,Object> delete(Integer id)throws Exception{
        articleService.delete(id);
        // TODO 对应的索引信息也要删除
        Map<String,Object> resultMap=new HashMap<String,Object>();
        resultMap.put("success",true);
        return resultMap;
    }

    /**
     * 多选删除
     * @param ids
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteSelected")
    public Map<String,Object> deleteSelected(String ids)throws Exception{
        String[] idsStr = ids.split(",");
        for(int i=0;i<idsStr.length;i++){
            String articleId = idsStr[i];
            articleService.delete(Integer.parseInt(articleId));
            // TODO 对应的索引信息也要删除
        }
        Map<String,Object> resultMap=new HashMap<String,Object>();
        resultMap.put("success",true);
        return resultMap;
    }

    /**
     * 查询该帖子信息
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/findById")
    public Map<String,Object> findById(String id)throws Exception{
        Article article = articleService.get(Integer.parseInt(id));
        Map<String,Object> map = new HashMap<>();
        map.put("article",article);
        map.put("success",true);
        return map;
    }
}
