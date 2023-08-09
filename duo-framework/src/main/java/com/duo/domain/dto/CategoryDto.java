package com.duo.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 分类DTO
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/8/4 15:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "分类DTO")
public class CategoryDto {

    private Long id;

    private String name;

    private String description;

    private Integer status;
}
