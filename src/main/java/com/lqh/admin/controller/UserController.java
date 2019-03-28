package com.lqh.admin.controller;

import com.lqh.admin.dto.PasswordHelper;
import com.lqh.admin.dto.QueryPage;
import com.lqh.admin.dto.ResponseCode;
import com.lqh.admin.entity.User;
import com.lqh.admin.exception.GlobalException;
import com.lqh.admin.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordHelper passwordHelper;

    @PostMapping(value = "/findByPage")
    public ResponseCode findByPage(QueryPage queryPage, User user) {
        return ResponseCode.success(super.selectByPageNumSize(queryPage, ()->userService.findByPage(user)));
    }

    @GetMapping(value = "/findById")
    public ResponseCode findById(@RequestParam("id") Long id) {
        return ResponseCode.success(userService.findById(id));
    }

    @PostMapping(value = "/save")
    public ResponseCode save(@RequestBody User user) {
        try {
            userService.save(user);
            return ResponseCode.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseCode update(@RequestBody User user){
        try {
            if(user.getPassword()!=null&& !user.getPassword().equals("")&& user.getCheckPass() != null && !"".equals(user.getCheckPass())) {
                User u = userService.findByName((String) SecurityUtils.getSubject().getPrincipal());
                //设置u.checkPass=旧密码
                u.setCheckPass(user.getPassword());
                // 设置user盐值
                user.setSalt(u.getSalt());
                // 设置user.password=旧密码
                user.setPassword(user.getCheckPass());
                //将新密码加密
                passwordHelper.encryptPassword(user);
                if (!u.getPassword().equals(user.getPassword())) {
                    logger.info("输入的旧密码不匹配：new:" + user.getPassword() + ", old:" + u.getPassword());
                    throw new GlobalException("输入的旧密码不匹配");
                } else {
                    logger.info("输入的旧密码匹配，执行更新操作");
                    logger.info(u.getCheckPass());
                    user.setPassword(u.getCheckPass());
                    userService.update(user);
                }
            }else{
                userService.update(user);
            }
            return ResponseCode.success();
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @PostMapping(value = "/delete")
    public ResponseCode delete(@RequestBody Long... ids) {
        try {
            userService.delete(ids);
            return ResponseCode.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }
}
