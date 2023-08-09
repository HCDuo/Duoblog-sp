package com.duo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/8/4 14:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateVo {
    private UserUpdateInfoVo user;
    private List<Long> roleIds;
    private List<RoleSimpleVo> roles;
}
