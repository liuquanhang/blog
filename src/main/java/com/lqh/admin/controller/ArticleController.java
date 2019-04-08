package com.lqh.admin.controller;

import com.alibaba.fastjson.JSON;
import com.lqh.admin.dto.QueryPage;
import com.lqh.admin.dto.ResponseCode;
import com.lqh.admin.entity.Article;
import com.lqh.admin.entity.Tags;
import com.lqh.admin.exception.GlobalException;
import com.lqh.admin.service.ArticleService;
import com.lqh.admin.service.ArticleTagsService;
import com.lqh.admin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 *@Author: null
 *@Date: 12:03 2019/3/27
 */
@RestController
@RequestMapping("/article")
public class ArticleController extends BaseController{
    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleTagsService articleTagsService;

    @GetMapping(value = "/findAll")
    public ResponseCode findAll() {
        return ResponseCode.success(articleService.findAll());
    }

    @GetMapping(value = "/findAllCount")
    public ResponseCode findAllCount() {
        return ResponseCode.success(articleService.findAllCount());
    }

    @GetMapping(value = "/findByPage")
    public ResponseCode findByPage(QueryPage queryPage, Article article){
        return ResponseCode.success(super.selectByPageNumSize(queryPage,()->articleService.findByPage(article)));
    }

    @GetMapping(value = "/findById")
    public ResponseCode findById(@RequestParam("id")Long id, Model model){
        Article article = articleService.findById(id);
        if(article.getId()!=0){
            List<String> tags = new ArrayList<>();
            List<Tags> tagsList = articleTagsService.findByArticleId(article.getId());
            tagsList.forEach(tag ->{
                tags.add(tag.getName());
            });
            article.setTags(JSON.toJSONString(tags));
            return ResponseCode.success(article);
        }else {
            return ResponseCode.error();
        }
    }

    @GetMapping(value = "/findTags")
    public ResponseCode findTags(@RequestParam("id")Long id){
        List<String> list = new ArrayList<>();
        List<Tags> tagsList = articleTagsService.findByArticleId(id);
        for(Tags tag:tagsList){
            list.add(tag.getName());
        }
        return ResponseCode.success(list);
    }

    @GetMapping(value = "/findArchives")
    public ResponseCode findArchives(){
        return ResponseCode.success(articleService.findArchives());
    }

    @PostMapping(value = "/save")
    public ResponseCode save(@Validated @RequestBody Article article){
        try {
            articleService.save(article);
            return ResponseCode.success();
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @PostMapping(value = "/delete")
    public ResponseCode delete(@RequestBody Long...ids){
        try{
            articleService.delete(ids);
            return ResponseCode.success();
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @PutMapping(value = "/update")
    public ResponseCode update(@RequestBody Article article) {
        try {
            articleService.update(article);
            return ResponseCode.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }
}
