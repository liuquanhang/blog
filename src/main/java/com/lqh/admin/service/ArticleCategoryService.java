package com.lqh.admin.service;

import com.lqh.admin.entity.ArticleCategory;

public interface ArticleCategoryService extends BaseService<ArticleCategory> {

    /**
     * 根据文章ID删除
     * @param id
     */
    void deleteByArticleId(Long id);

    /**
     * 根据分类ID删除
     * @param id
     */
    void deleteByCategoryId(Long id);
}
