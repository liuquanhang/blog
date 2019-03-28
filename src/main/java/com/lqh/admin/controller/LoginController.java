package com.lqh.admin.controller;

import com.lqh.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *@Author: null
 *@Date: 11:57 2019/3/28
 * 登陆处理器
 */
@Controller
public class LoginController extends BaseController{

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String admin(Model model){
        return "admin/index";
    }

    @GetMapping("/login")
    public String login(){
        return "admin/login";
    }
}
