package com.duo.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 添加标签DTO
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/8/2 0:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "添加标签DTO")
public class TagDto {
    private String name;
    private String remark;
}
