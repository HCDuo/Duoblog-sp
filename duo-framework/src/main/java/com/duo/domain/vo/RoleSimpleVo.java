package com.duo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/8/4 1:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleSimpleVo {
    //角色ID@TableId
    private Long id;
    //角色名称
    private String roleName;
    //角色权限字符串
    private String roleKey;
    //显示顺序
    private Integer roleSort;
    //角色状态（0正常 1停用）
    private String status;
    //删除标志（0代表存在 1代表删除）
    private String delFlag;
    //创建者
    private Long createBy;
    //创建时间
    private Date createTime;
    //更新者
    private Long updateBy;
    //备注
    private String remark;

}
