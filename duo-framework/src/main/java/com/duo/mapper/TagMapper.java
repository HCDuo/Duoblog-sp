package com.duo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duo.domain.entity.Tag;
import org.apache.ibatis.annotations.Mapper;


/**
 * 标签(Tag)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-31 16:37:13
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}
