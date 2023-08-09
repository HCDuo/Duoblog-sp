package com.duo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 *  类别返回类
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/26 20:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVo {
    private Long id;
    private String name;
    //描述
    private String description;
}
