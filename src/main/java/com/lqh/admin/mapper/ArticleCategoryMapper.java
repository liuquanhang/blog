package com.lqh.admin.mapper;

import com.lqh.admin.entity.ArticleCategory;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleCategoryMapper {

    void save(ArticleCategory articleCategory);

    boolean exists(@Param("articleId") long articleId, @Param("categoryId") long categoryId);

    @Delete("DELETE FROM tb_article_category WHERE article_id = #{id}")
    void deleteByArticleId(long id);

    @Delete("DELETE FROM tb_article_category WHERE category_id = #{id}")
    void deleteByCategoryId(long id);
}
