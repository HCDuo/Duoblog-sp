package com.duo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duo.domain.entity.Category;
import org.apache.ibatis.annotations.Mapper;


/**
 * 分类表(Category)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-26 18:32:24
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}
