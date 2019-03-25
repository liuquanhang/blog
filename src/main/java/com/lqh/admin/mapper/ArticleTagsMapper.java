package com.lqh.admin.mapper;

import com.lqh.admin.entity.ArticleTags;
import com.lqh.admin.entity.Tags;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleTagsMapper {

    void save(ArticleTags articleTags);

    boolean exists(@Param("articleId") long articleId, @Param("tagsId") long tagsId);

    List<Tags> findByArticleId(long articleId);

    @Delete("DELETE FROM tb_article_tags WHERE article_id = #{id}")
    void deleteByArticleId(long id);

    @Delete("DELETE FROM tb_article_tags WHERE tags_id = #{id}")
    void deleteByTagsId(long id);
}
