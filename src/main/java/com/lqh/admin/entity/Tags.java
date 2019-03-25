package com.lqh.admin.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class Tags implements Serializable {

    private long id; //编号
    @NotNull
    private String name; //标签名称

    public Tags() {
    }

    public Tags(String name) {
        this.name = name;
    }
}
