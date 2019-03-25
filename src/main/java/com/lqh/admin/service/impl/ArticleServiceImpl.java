package com.lqh.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lqh.admin.dto.ArticleArchives;
import com.lqh.admin.entity.*;
import com.lqh.admin.exception.GlobalException;
import com.lqh.admin.mapper.ArticleMapper;
import com.lqh.admin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *@Author: null
 *@Date: 21:09 2019/3/25
 */

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagsService tagsService;
    @Autowired
    private ArticleCategoryService articleCategoryService;
    @Autowired
    private ArticleTagsService articleTagsService;

    @Override
    public Map<String, Object> findByPageForSite(int pageCode, int pageSize) {
        return null;
    }

    @Override
    public List<Article> findByCategory(String category) {
        return articleMapper.findByCategory(category);
    }

    @Override
    public List<ArticleArchives> findArchives() {
        ArrayList<ArticleArchives> articleArchivesList = new ArrayList<>();
        try {
            List<String> dates = articleMapper.findArchivesDates();
            dates.forEach(date->{
                List<Article> articleList = articleMapper.findArchivesByDate(date);
                ArticleArchives articleArchives = new ArticleArchives(date, articleList);
                articleArchivesList.add(articleArchives);
            });
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
        return articleArchivesList;
    }

    @Override
    public List<Article> findFuzzyByTitle(String title) {
        if (!title.isEmpty()) {
            return articleMapper.findFuzzyByTitle(title);
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addEyeCount(Long id) {
        if(!id.equals(null) && id!=0){
            try {
                articleMapper.addEyeCount(id);
            }catch (Exception e){
                e.printStackTrace();
                throw new GlobalException(e.getMessage());
            }
        }
    }

    @Override
    public Long findAllCount() {
        return articleMapper.findAllCount();
    }

    @Override
    public List<Article> findAll() {
        return articleMapper.findAll();
    }

    @Override
    public List<Article> findByPage(Article article) {
        List<Article> articleList = articleMapper.findByPage(article);
        findInit(articleList);
        return articleList;
    }

    private void findInit(List<Article> list){
        if(!list.isEmpty()){
            list.forEach(article -> {
                List<Category> categoryList = categoryService.findByArticleId(article.getId());
                if(categoryList.size()>0){
                    article.setCategory(categoryList.get(0).getName());
                }
                List<Tags> tagsList = tagsService.findByArticleId(article.getId());
                List<String> stringList = new ArrayList<>();
                tagsList.forEach(tags -> {
                    stringList.add(tags.getName());
                });
                article.setTags(JSON.toJSONString(tagsList));
            });
        }
    }

    @Override
    public Article findById(Long id) {
        if (!id.equals(null) && id != 0) {
            Article article = articleMapper.findById(id);
            List<Article> articleList = new ArrayList<>();
            articleList.add(article);
            findInit(articleList);
            return article;
        } else {
            return new Article();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Article article) {
        try{
            if(article.getState()=="1"){
                article.setPublishTime(new Date());
            }
            article.setEditTime(new Date());
            articleMapper.save(article);
            article.setId(articleMapper.getLastId());
            updateArticleCategoryTags(article);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    protected void updateArticleCategoryTags(Article article){
        if(article.getId()!=0){
            if(article.getCategory()!=null){
                //证明新插入的文章有分类信息，将这个文章分类保存到分类表中
                categoryService.save(new Category(article.getCategory()));
                //保存了分类信息再保存分类-文章的关联信息
                Category category = categoryService.findByName(article.getCategory());
                articleCategoryService.save(new ArticleCategory(article.getId(),category.getId()));
            }
            if(article.getTags()!=null){
                //证明新插入的文章有标签数据，将标签数据保存到标签表中
                //前端传来的标签是JSON字符串格式的标签名称
                List<String> list = (List<String>) JSONArray.parse(article.getTags());
                if(list.size()>0){
                    list.forEach(name->{
                        tagsService.save(new Tags(name));
                        //因为标签是多个的，需要依次将标签信息保存到标签表中
                        Tags tags = tagsService.findByName(name);
                        if (tags!=null){
                            //该标签插入成功或已存在，建立标签-文章关联信息
                            articleTagsService.save(new ArticleTags(article.getId(),tags.getId()));
                        }
                    });
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Article article) {
        try{
            articleMapper.update(article);
            updateArticleCategoryTags(article);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long... ids) {
        if(ids.length>0 && !ids.equals(null)){
            try {
                for (long id:ids){
                    articleMapper.delete(id);
                    articleCategoryService.deleteByCategoryId(id);
                    articleTagsService.deleteByArticleId(id);
                }
            }catch (Exception e){
                e.printStackTrace();
                throw new GlobalException(e.getMessage());
            }
        }
    }
}
