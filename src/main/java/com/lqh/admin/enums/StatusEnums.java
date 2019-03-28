package com.lqh.admin.enums;

import lombok.Getter;
import lombok.Setter;

/**
 *@Author: null
 *@Date: 12:09 2019/3/27
 * 枚举类，封装响应状态信息
 */
public enum StatusEnums {

    SUCCESS(200,"操作成功"),
    ACCOUNT_UNKNOWN(500,"账户不存在"),
    ACCOUNT_ERROR(500,"用户名或密码错误"),
    SYSTEM_ERROR(500, "系统错误"),
    PARAM_ERROR(400, "参数错误"),
    PARAM_REPEAT(400, "参数已存在"),
    OTHER(-100, "其他错误");

    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private String info;

    StatusEnums(int code,String info){
        this.code = code;
        this.info = info;
    }
}
