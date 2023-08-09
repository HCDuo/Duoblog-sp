package com.duo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duo.domain.entity.ArticleTag;
import org.apache.ibatis.annotations.Mapper;


/**
 * 文章标签关联表(ArticleTag)表数据库访问层
 *
 * @author makejava
 * @since 2023-08-02 17:48:38
 */
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

}
