package com.duo.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <pre>
 * 添加评论DTO
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/30 15:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "添加评论DTO")
public class AddCommentDto {
    private Long id;
    //评论类型（0代表文章评论，1代表友链评论）
    private String type;
    //文章id
    private Long articleId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的userid
    private Long toCommentUserId;
    //回复目标评论id
    private Long toCommentId;
    //创建人的用户id
    private Long createBy;
    //创建时间
    private Date createTime;
    //更新人
    private Long updateBy;
    //更新时间
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;
}
