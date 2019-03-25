package com.lqh.admin.service.impl;

import com.lqh.admin.dto.PasswordHelper;
import com.lqh.admin.entity.User;
import com.lqh.admin.exception.GlobalException;
import com.lqh.admin.mapper.UserMapper;
import com.lqh.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    private PasswordHelper passwordHelper;
    @Override
    public User findByName(String username) {
        if(!username.isEmpty()){
            return userMapper.findByName(username);
        }else{
            return new User();
        }
    }

    @Override
    public Long findAllCount() {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public List<User> findByPage(User user) {
        return userMapper.findByPage(user);
    }

    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    @Transactional
    public void save(User user) {
        try{
            passwordHelper.encryptPassword(user);
            userMapper.save(user);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void update(User user) {
        if(user.getId()!=0){
            try{
                if(user.getPassword()!=null&&!user.getPassword().equals("")){
                    passwordHelper.encryptPassword(user);
                }
                userMapper.update(user);
            }catch (Exception e){
                e.printStackTrace();
                throw new GlobalException(e.getMessage());
            }
        }
    }

    @Override
    @Transactional
    public void delete(Long... ids) {
        if(!ids.equals(null)&&ids.length>0){
            try {
                for (long id:ids){
                    userMapper.delete(id);
                }
            }catch (Exception e){
                throw new GlobalException(e.getMessage());
            }
        }
    }
}
