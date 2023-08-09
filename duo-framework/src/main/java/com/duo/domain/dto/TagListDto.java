package com.duo.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 查询标签DTO
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/8/1 17:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "查询标签DTO")
public class TagListDto {
    private String name;
    private String remark;
}
