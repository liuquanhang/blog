package com.lqh.admin.dto;

import com.lqh.admin.enums.StatusEnums;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *@Author: null
 *@Date: 12:16 2019/3/27
 * 封装响应信息和数据的类
 */
@Data
@AllArgsConstructor
public class ResponseCode {
    private Integer code;
    private String msg;
    private Object data;

    public ResponseCode(StatusEnums enums) {
        this.code = enums.getCode();
        this.msg = enums.getInfo();
    }

    public ResponseCode(StatusEnums enums, Object data) {
        this.code = enums.getCode();
        this.msg = enums.getInfo();
        this.data = data;
    }

    public ResponseCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResponseCode success() {
        return new ResponseCode(StatusEnums.SUCCESS);
    }

    public static ResponseCode success(Object data) {
        return new ResponseCode(StatusEnums.SUCCESS, data);
    }

    public static ResponseCode error() {
        return new ResponseCode(StatusEnums.SYSTEM_ERROR);
    }
}
