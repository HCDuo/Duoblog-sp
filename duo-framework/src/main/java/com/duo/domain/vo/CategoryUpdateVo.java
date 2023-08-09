package com.duo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/8/4 16:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdateVo {
    private Long id;

    private String name;

    private String description;

    private Integer status;
}
