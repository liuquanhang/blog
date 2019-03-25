package com.lqh.admin.service;

import com.lqh.admin.entity.Category;

import java.util.List;

public interface CategoryService extends BaseService<Category> {

    Category findByName(String name);

    /**
     * 根据文章ID查询其关联的分类数据
     *
     * @param id
     * @return
     */
    List<Category> findByArticleId(Long id);
}
