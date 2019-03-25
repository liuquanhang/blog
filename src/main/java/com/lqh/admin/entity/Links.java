package com.lqh.admin.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class Links implements Serializable {

    private long id; //编号
    @NotNull
    private String name; //连接名称
    private String url; //连接URL
}

