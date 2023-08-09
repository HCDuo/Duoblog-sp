package com.duo.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <pre>
 *  查询分页文章返回类
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/26 21:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListVo {

    private Long id;
    //标题
    private String title;
    //文章摘要
    private String summary;
    //所属分类名
    private String categoryName;
    //缩略图
    private String thumbnail;
    //访问量
    private Long viewCount;
    //创建日期
    private Date createTime;

}
