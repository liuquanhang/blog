package com.lqh.admin.dto;

import com.lqh.admin.entity.Comments;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

//封装评论信息
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentsDTO implements Serializable {

    private Comments parent; //父级留言信息
    private List<Comments> childrenList; //所有子级回复、评论列表
}
