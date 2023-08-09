package com.duo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duo.domain.entity.Comment;
import org.apache.ibatis.annotations.Mapper;


/**
 * 评论表(Comment)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-28 20:25:38
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
