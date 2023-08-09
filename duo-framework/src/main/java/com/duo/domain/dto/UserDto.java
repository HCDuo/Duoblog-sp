package com.duo.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <pre>
 * 修改用户DTO
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/8/4 15:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "修改用户DTO")
public class UserDto {

    private Long id;

    private String userName;

    private String nickName;

    private String phonenumber;

    private String email;

    private String sex;

    private Integer status;

    private List<Long> roleIds;

}
