package com.lqh.admin.controller;

import com.lqh.admin.dto.ResponseCode;
import com.lqh.admin.exception.GlobalException;
import com.lqh.admin.service.UserService;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *@Author: null
 *@Date: 11:57 2019/3/28
 * 登陆处理器
 */
@Controller
public class LoginController extends BaseController{

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService){
        this.userService = userService;
    }

    /**
     *@Author: null
     *@Date: 12:09 2019/3/28
     * 跳转到后台页面
     */
    @GetMapping("/admin")
    public String admin(Model model){
        return "admin/index";
    }

    /**
     *@Author: null
     *@Date: 12:11 2019/3/28
     *跳转登陆页面
     */
    @GetMapping("/login")
    public String login(){
        return "admin/login";
    }

    /**
     *@Author: null
     *@Date: 12:17 2019/3/28
     * 登陆校验，返回JSON格式字符
     */
    @RequestMapping("/admin/login")
    @ResponseBody
    public ResponseCode login(Model model,
                              @RequestParam(value = "username",required = false)String username,
                              @RequestParam(value = "password",required = false)String password,
                              @RequestParam(value = "remember",required = false)String remember){

        if(username!=null && password != null){
            Subject subject = getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            if(remember != null){
                if("true".equals(remember)){
                    //用户选择"记住我"
                    token.setRememberMe(true);
                }else {
                    token.setRememberMe(false);
                }
            }else {
                token.setRememberMe(false);
            }
            try {
                subject.login(token);
                model.addAttribute("username",getSubject().getPrincipal());
                return ResponseCode.success();
            }catch (Exception e){
                e.printStackTrace();
                throw new GlobalException(e.getMessage());
            }
        }else {
            throw new GlobalException("用户名或密码错误");
        }
    }

    /**
     *@Author: null
     *@Date: 16:20 2019/3/28
     * 注销登陆
     */
    @GetMapping(value = "/admin/logout")
    public String logout(){
        Subject subject = getSubject();
        subject.logout();
        return "redirect:/admin";
    }

    /**
     *@Author: null
     *@Date: 16:20 2019/3/28
     *
     */
    @GetMapping("/admin/info")
    @ResponseBody
    public ResponseCode getInfo(){
        return ResponseCode.success(userService.findByName((String)getSubject().getPrincipal()));
    }
}
