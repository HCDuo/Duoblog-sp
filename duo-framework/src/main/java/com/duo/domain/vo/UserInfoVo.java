package com.duo.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/28 0:49
 */
@Data
@Accessors(chain = true)
public class UserInfoVo {
    /**
     * 主键
     */
    private Long id;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String avatar;

    private String sex;

    private String email;


}
