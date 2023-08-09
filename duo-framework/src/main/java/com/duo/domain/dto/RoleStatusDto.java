package com.duo.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 角色状态DTO
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/8/3 17:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "角色状态DTO")
public class RoleStatusDto {

    private Long roleId;

    private String status;
}
