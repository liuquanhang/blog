package com.lqh.site.controller;

import com.lqh.admin.entity.Article;
import com.lqh.admin.entity.Links;
import com.lqh.admin.service.ArticleService;
import com.lqh.admin.service.CommentsService;
import com.lqh.admin.service.LinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 *@Author: null
 *@Date: 21:50 2019/3/25
 * 用于博客前端页面跳转的控制层
 */

@Controller
public class SiteController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private LinksService linksService;

    @GetMapping(value = {"","/","/index"})
    public String index(Model model){
        initIndex(1,model);
        initFooter(model);
        return "site/index";
    }

    @GetMapping("/about")
    public String abount(Model model,@RequestParam(value = "cp",required = false)Integer cp){
        if(cp==null){
            //查询第一页评论
            cp = 1;
        }
        //参数cp:第几页 sort:0文章详情页的评论信息,1友链页的评论信息,2关于我页的评论信息
        getPage(commentsService.findCommentsList(cp,6,0,2),model,cp,2);
        return "site/page/about";
    }
    /**
     *@Author: null
     *@Date: 12:28 2019/3/26
     * 返回分页信息
     */
    private void getPage(Map<String,Object> map,Model model,Integer cp,Integer sort){
        model.addAttribute("commentsCount",map.get("total"));
        map.put("total",(long)Math.ceil(((Long)map.get("total")).doubleValue()/(double)6));
        model.addAttribute("talkList",map);
        model.addAttribute("cp",cp);
        model.addAttribute("sort",sort);
        initFooter(model);
    }

    private void initIndex(Integer pageCode, Model model){
        //分页展示文章
        Map<String, Object> map = articleService.findByPageForSite(pageCode, 6);
        //总页数=总数/每页数,放入map覆盖原total值
        map.put("total", (long) Math.ceil(((Long) map.get("total")).doubleValue() / (double) 6));
        model.addAttribute("page",map);
        model.addAttribute("pageCode",pageCode);
    }

    private void initFooter(Model model){
        //网站footer显示数据
        model.addAttribute("articleList",articleService.findAll());
        model.addAttribute("commentsList",commentsService.findAll());
    }
    /**
     *@Author: null
     *@Date: 16:50 2019/3/26
     * 显示文章页
     */
    @RequestMapping("/article/{id}")
    public String generate(@PathVariable("id") Long id, @RequestParam(value = "cp",required = false)Integer cp,Model model){
        if(id!=null && id!=0){
            //添加浏览量
            articleService.addEyeCount(id);
            //查找文章
            Article article = articleService.findById(id);
            model.addAttribute("article",article);
            if(cp==null){
                //查询第一页评论数据
                cp = 1;
            }
        getPage(commentsService.findCommentsList(cp,6,new Long(id).intValue(),0),model,cp,0);
            return "site/page/content";
        }else{
            return "site/index";
        }
    }

    /**
     *@Author: null
     *@Date: 16:27 2019/3/26
     * 归档页面
     */
    @GetMapping("/archives")
    public String archives(Model model) {
        model.addAttribute("archives", articleService.findArchives());
        initFooter(model);
        return "site/page/archives";
    }

    @GetMapping("/link")
    public String link(Model model, @RequestParam(value = "cp", required = false) Integer cp) {
        //加载友情链接数据
        List<Links> linksList = linksService.findAll();
        model.addAttribute("links", linksList);
        if (cp == null) {
            //查询的第一页评论数据
            cp = 1;
        }
        getPage(commentsService.findCommentsList(cp, 6, 0, 1), model, cp, 1);
        return "site/page/link";
    }

    @RequestMapping("/page/{pageCode}")
    public String page(@PathVariable("pageCode") Integer pageCode, Model model) {
        if (pageCode != null && pageCode != 0) {
            //初始化页面数据
            initIndex(pageCode, model);
            initFooter(model);
            return "site/index";
        } else {
            return "site/index";
        }
    }

    @RequestMapping("/search/{name}")
    public String findArchivesByTitle(@PathVariable("name") String title, Model model) {
        if (title != null && !"".equals(title)) {
            model.addAttribute("article", articleService.findFuzzyByTitle(title));
            model.addAttribute("title", title);
            initFooter(model);
            return "/site/page/search";
        } else {
            return "site/index";
        }
    }
}
