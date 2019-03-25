package com.lqh.admin.service;

import com.lqh.admin.entity.User;

public interface UserService extends BaseService<User> {

    User findByName(String username);
}