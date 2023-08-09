package com.duo.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 友链DTO
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/8/4 16:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "友链DTO")
public class LinkDto {
    private Long id;

    private String name;

    private String logo;

    private String description;

    private String address;

    private String status;
}
