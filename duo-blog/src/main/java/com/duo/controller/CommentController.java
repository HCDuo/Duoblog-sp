package com.duo.controller;

import com.duo.annotation.SystemLog;
import com.duo.constants.SystemConstants;
import com.duo.domain.ResponseResult;
import com.duo.domain.entity.Comment;
import com.duo.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/28 23:05
 */
@RestController
@RequestMapping("/comment")
@Api(tags = "评论",description = "评论相关接口")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    @SystemLog(BusinessName = "文章评论列表")
    @ApiOperation(value = "文章评论列表",notes = "获取一页文章评论")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize){
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }

    @PostMapping
    @SystemLog(BusinessName = "增加评论")
    @ApiOperation(value = "增加评论",notes = "增加一个评论")
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }

    @GetMapping("/linkCommentList")
    @SystemLog(BusinessName = "友链评论列表")
    @ApiOperation(value = "友链评论列表",notes = "获取一页友链评论")
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }
}
