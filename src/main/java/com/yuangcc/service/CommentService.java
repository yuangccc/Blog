package com.yuangcc.service;

import com.yuangcc.po.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long bligId);

    Comment saveComment(Comment comment);
}
