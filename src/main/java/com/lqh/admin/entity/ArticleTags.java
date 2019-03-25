package com.lqh.admin.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ArticleTags implements Serializable {

    private long id; //编号
    @NotNull
    private long articleId; //文章ID
    @NotNull
    private long tagsId; //标签ID

    public ArticleTags() {
    }

    public ArticleTags(long articleId, long tagsId) {
        this.articleId = articleId;
        this.tagsId = tagsId;
    }
}

