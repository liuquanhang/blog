package com.lqh.admin.service;

import com.lqh.admin.entity.Tags;

import java.util.List;

public interface TagsService extends BaseService<Tags>{
    Tags findByName(String name);

    List<Tags> findByArticleId(Long id);
}
