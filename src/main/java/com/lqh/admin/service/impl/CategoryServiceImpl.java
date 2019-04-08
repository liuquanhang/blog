package com.lqh.admin.service.impl;

import com.lqh.admin.entity.Category;
import com.lqh.admin.exception.GlobalException;
import com.lqh.admin.mapper.CategoryMapper;
import com.lqh.admin.service.ArticleCategoryService;
import com.lqh.admin.service.ArticleService;
import com.lqh.admin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 *@Author:null
 *@Date:12:05 2019/3/25
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleCategoryService articleCategoryService;

    @Override
    public Category findByName(String name) {
        if(!name.isEmpty()){
            return categoryMapper.findByName(name);
        }else{
            throw new GlobalException("参数错误");
        }
    }

    @Override
    public List<Category> findByArticleId(Long id) {
        if (!id.equals(null) && id != 0) {
            return categoryMapper.findCategoryByArticleId(id);
        } else {
            throw new GlobalException("参数错误");
        }
    }

    @Override
    public Long findAllCount() {
        return null;
    }

    @Override
    public List<Category> findAll() {
        return categoryMapper.findAll();
    }

    @Override
    public List<Category> findByPage(Category category) {
        return categoryMapper.findByPage(category);
    }

    @Override
    public Category findById(Long id) {
        if(!id.equals(null)&&id!=0){
            return categoryMapper.findById(id);
        }else{
            throw new GlobalException("参数错误");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Category category) {
        try{
            if(!exists(category)){
                categoryMapper.save(category);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    private boolean exists(Category category) {
        return categoryMapper.exists(category.getName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Category category) {
        try {
            if(category.getId()!=0){
                categoryMapper.update(category);
            }
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
                    categoryMapper.delete(id);
                    //删除该分类与文章关联的行
                    articleCategoryService.deleteByCategoryId(id);
                }
            }catch (Exception e){
                e.printStackTrace();
                throw new GlobalException(e.getMessage());
            }
        }
    }
}
