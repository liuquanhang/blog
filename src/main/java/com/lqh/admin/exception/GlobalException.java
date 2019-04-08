package com.lqh.admin.exception;


import lombok.Getter;
import lombok.Setter;
/**
 *@Author: null
 *@Date: 12:37 2019/3/29
 *
 */
public class GlobalException extends RuntimeException{
    @Getter
    @Setter
    private String msg;

    public GlobalException(String msg){
        this.msg = msg;
    }
}
