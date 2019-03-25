package com.lqh.admin.service;

import java.util.List;

public interface BaseService<T> {

    Long findAllCount();

    List<T> findAll();

    List<T> findByPage(T t);

    T findById(Long id);

    void save(T t);

    void update(T t);

    void delete(Long...ids);
}
