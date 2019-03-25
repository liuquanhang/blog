package com.lqh.admin.mapper;

import com.lqh.admin.entity.Tags;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TagsMapper {

    @Select("SELECT * FROM tb_tags")
    List<Tags> findAll();

    @Select("SELECT * FROM tb_tags")
    List<Tags> findByPage(Tags tags);

    @Select("SELECT * FROM tb_tags WHERE id = #{id}")
    Tags findById(long id);

    void save(Tags tags);

    void update(Tags tags);

    @Select("DELETE FROM tb_tags WHERE id = #{id}")
    void delete(long id);

    @Select("SELECT COUNT(1) FROM tb_tags WHERE name = #{name}")
    boolean exists(String name);

    @Select("SELECT * FROM tb_tags WHERE name = #{name}")
    Tags findByName(String name);

    @Select("SELECT COUNT(*) FROM tb_tags")
    Long findAllCount();

    List<Tags> findByArticleId(long id);
}

