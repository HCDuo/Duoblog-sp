package com.duo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duo.domain.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <pre>
 * 文章表(Article)表映射类
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/26 14:32
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    void updateViewCountById(@Param("id") Long id, @Param("viewCount") Long viewCount);

}
