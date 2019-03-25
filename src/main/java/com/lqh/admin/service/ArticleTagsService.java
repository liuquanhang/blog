package com.lqh.admin.service;

import com.lqh.admin.entity.ArticleTags;
import com.lqh.admin.entity.Tags;

import java.util.List;

public interface ArticleTagsService extends BaseService<ArticleTags>{
    List<Tags> findByArticleId(Long articleId);

    /**
     * 根据文章ID删除
     * @param id
     */
    void deleteByArticleId(Long id);

    /**
     * 根据标签ID删除
     * @param id
     */
    void deleteByTagsId(Long id);
}
