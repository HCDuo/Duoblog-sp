package com.duo.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <pre>
 * 添加角色DTO
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/8/4 1:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "添加角色DTO")
public class AddUserDto {

    private Long id;

    private String userName;

    private String password;

    private String nickName;

    private String phonenumber;

    private String email;

    private String sex;

    private Integer status;

    private List<Long> roleIds;
}
