package com.duo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duo.constants.SystemConstants;
import com.duo.domain.ResponseResult;
import com.duo.domain.entity.Comment;
import com.duo.domain.vo.CommentVo;
import com.duo.domain.vo.PageVo;
import com.duo.enums.AppHttpCodeEnum;
import com.duo.exception.SystemException;
import com.duo.mapper.CommentMapper;
import com.duo.service.CommentService;
import com.duo.service.UserService;
import com.duo.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-07-28 20:25:41
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //查询文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //对articleId判断
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
        //根评论的id为-1
        queryWrapper.eq(Comment::getRootId,-1);
        //评论类型
        queryWrapper.eq(Comment::getType,commentType);
        //分页查询
        Page<Comment> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);

        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());

        //查询所有根评论对应的子评论集合，并且赋值给对应的属性
        for (CommentVo commentVo : commentVoList) {
            //查询对应的子评论
            List<CommentVo> children = getChildren(commentVo.getId());
            //赋值
            commentVo.setChildren(children);
        }
        return ResponseResult.okResult(new PageVo(commentVoList,page.getTotal()));
    }

    @Override
    @Transactional
    public ResponseResult addComment(Comment comment) {
        //评论内容不能为空
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    private List<CommentVo> getChildren(Long id) {

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(queryWrapper);

        List<CommentVo> commentVos = toCommentVoList(comments);
        return commentVos;
    }

    private List<CommentVo> toCommentVoList(List<Comment> list){
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);

        for (CommentVo commentVo : commentVos) {
            if (commentVo.getCreateBy() == null) {
                // 添加空值检查，如果创建者ID为空，则跳过后续操作
                continue;
            }
            // 查询用户昵称
            String nickName = "";
            try {
                // 获取用户昵称，如果出现异常则设置为默认值
                nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            } catch (Exception e) {
                // 在发生异常时设置默认昵称或进行其他适当处理
                nickName = "Unknown";
            }
            commentVo.setUsername(nickName);
            // 查询toCommentUserId对应的用户昵称
            if (commentVo.getToCommentUserId() != -1) {
                String toCommentUserName = "";
                try {
                    // 获取toCommentUserId对应的用户昵称，如果出现异常则设置为默认值
                    toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                } catch (Exception e) {
                    // 在发生异常时设置默认昵称或进行其他适当处理
                    toCommentUserName = "Unknown";
                }
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }
        return commentVos;
    }
}
