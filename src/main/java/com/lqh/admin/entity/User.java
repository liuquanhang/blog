package com.lqh.admin.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class User implements Serializable {
    private long id; //编号
    @NotNull
    private String username; //用户名
    private String nickname; //昵称
    @NotNull
    private String password; //密码
    private String salt; //盐
    private String email; //邮箱
    private String avatar; //头像
    private String checkPass; //用于旧密码校验的属性

}
