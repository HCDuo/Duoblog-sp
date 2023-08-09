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
 * @date:2023/8/3 21:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleUpdateVo {
    private Long id;

    private String roleName;

    private String roleKey;

    private int roleSort;

    private String status;

    private String remark;
}
