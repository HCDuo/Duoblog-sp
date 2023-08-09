package com.duo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duo.domain.ResponseResult;
import com.duo.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-07-28 20:25:40
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);

}
