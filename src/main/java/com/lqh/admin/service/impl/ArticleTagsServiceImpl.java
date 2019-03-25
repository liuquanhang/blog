package com.lqh.admin.service.impl;

import com.lqh.admin.entity.Article;
import com.lqh.admin.entity.ArticleTags;
import com.lqh.admin.entity.Tags;
import com.lqh.admin.exception.GlobalException;
import com.lqh.admin.mapper.ArticleTagsMapper;
import com.lqh.admin.service.ArticleTagsService;
import jdk.nashorn.internal.objects.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleTagsServiceImpl implements ArticleTagsService {

    @Autowired
    ArticleTagsMapper articleTagsMapper;
    @Override
    public List<Tags> findByArticleId(Long articleId) {
        if(!articleId.equals(null)&&articleId!=0){
            return articleTagsMapper.findByArticleId(articleId);
        }else{
            throw new GlobalException("参数错误");
        }
    }

    @Override
    @Transactional
    public void deleteByArticleId(Long id) {
        if (!id.equals(null) && id != 0) {
            try {
                if (exists(new ArticleTags(id, 0))) {  //从表中删除tag为空的一行数据
                    articleTagsMapper.deleteByArticleId(id);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new GlobalException(e.getMessage());
            }
        } else {
            throw new GlobalException("参数错误");
        }
    }

    @Override
    @Transactional
    public void deleteByTagsId(Long id) {
        if (!id.equals(null) && id != 0) {
            try {
                if (exists(new ArticleTags(0, id))) {  //删除article为空的一行
                    articleTagsMapper.deleteByTagsId(id);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new GlobalException(e.getMessage());
            }
        } else {
            throw new GlobalException("参数错误");
        }
    }

    @Override
    public Long findAllCount() {
        return null;
    }

    @Override
    public List<ArticleTags> findAll() {
        return null;
    }

    @Override
    public List<ArticleTags> findByPage(ArticleTags articleTags) {
        return null;
    }

    @Override
    public ArticleTags findById(Long id) {
        return null;
    }


    @Override
    @Transactional
    public void save(ArticleTags articleTags) {
        try {
            if(!exists(articleTags)){
                articleTagsMapper.save(articleTags);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    private boolean exists(ArticleTags articleTags){
        return articleTagsMapper.exists(articleTags.getArticleId(),articleTags.getTagsId());
    }

    @Override
    public void update(ArticleTags articleTags) {

    }

    @Override
    public void delete(Long... ids) {

    }


}
