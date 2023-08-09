package com.duo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 *  热门文章返回类
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/26 17:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotArticleVo {
    private Long id;
    //标题
    private String title;
    //访问量
    private Long viewCount;
}
