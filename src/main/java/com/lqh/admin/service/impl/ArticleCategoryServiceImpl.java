package com.lqh.admin.service.impl;

import com.lqh.admin.entity.ArticleCategory;
import com.lqh.admin.exception.GlobalException;
import com.lqh.admin.mapper.ArticleCategoryMapper;
import com.lqh.admin.service.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *@Author: null
 *@Date: 12:09 2019/3/25
 */

@Service
public class ArticleCategoryServiceImpl implements ArticleCategoryService {
    @Autowired
    private ArticleCategoryMapper articleCategoryMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByArticleId(Long id) {
        try {
            if(!id.equals(null)&& id!=0){
                //删除分类为空的行
                if(exists(new ArticleCategory(id,0))){
                    articleCategoryMapper.deleteByArticleId(id);
                }
            }else{
                throw new GlobalException("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByCategoryId(Long id) {
        try {
            if(!id.equals(null) && id !=0){
                if(exists(new ArticleCategory(0,id))){
                    articleCategoryMapper.deleteByCategoryId(id);
                }
            }else{
                throw new GlobalException("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    public Long findAllCount() {
        return null;
    }

    @Override
    public List<ArticleCategory> findAll() {
        return null;
    }

    @Override
    public List<ArticleCategory> findByPage(ArticleCategory articleCategory) {
        return null;
    }

    @Override
    public ArticleCategory findById(Long id) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ArticleCategory articleCategory) {
        try {
            if(!exists(articleCategory)){
                articleCategoryMapper.save(articleCategory);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    private boolean exists(ArticleCategory articleCategory) {
        return articleCategoryMapper.exists(articleCategory.getArticleId(), articleCategory.getCategoryId());
    }

    @Override
    public void update(ArticleCategory articleCategory) {

    }

    @Override
    public void delete(Long... ids) {

    }
}
