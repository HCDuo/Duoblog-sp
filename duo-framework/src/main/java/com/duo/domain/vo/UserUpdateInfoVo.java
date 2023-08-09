package com.duo.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/8/4 14:40
 */
@Data
@NoArgsConstructor
public class UserUpdateInfoVo {
    private Long id;
    private String userName;
    private String email;
    private String nickName;
    private String sex;
    private Integer status;

}
