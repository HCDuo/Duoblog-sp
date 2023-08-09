package com.duo.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <pre>
 * 增加角色DTO
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/8/3 19:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "增加角色DTO")
public class RoleAddDTO {

    private String roleName;

    private String roleKey;

    private int roleSort;

    private String status;

    private String remark;

    private List<Integer> menuIds;
}
