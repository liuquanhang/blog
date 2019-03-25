package com.lqh.admin.mapper;

import com.lqh.admin.entity.Links;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LinksMapper {

    @Select("SELECT * FROM tb_links")
    List<Links> findAll();

    @Select("SELECT * FROM tb_links")
    List<Links> findByPage(Links links);

    @Select("SELECT * FROM tb_links WHERE id = #{id}")
    Links findById(long id);

    void save(Links links);

    void update(Links links);

    @Delete("DELETE FROM tb_links WHERE id = #{id}")
    void delete(long id);

    @Select("SELECT COUNT(*) FROM tb_links")
    Long findAllCount();
}