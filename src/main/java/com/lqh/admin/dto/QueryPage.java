package com.lqh.admin.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class QueryPage implements Serializable {

    private int pageCode;
    //当前页
    private int pageSize;
    //每页显示的记录数
}