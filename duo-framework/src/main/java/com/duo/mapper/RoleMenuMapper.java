package com.duo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <pre>
 * 角色菜单关联表表数据库访问层
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/8/3 19:25
 */
@Mapper
public interface RoleMenuMapper {

    void insertRoleMenu(@Param("roleId")Long id, @Param("menuIds") List<Integer> menuIds);

    List<Long> selectMenuIdsByRoleId(Long id);

    void deleteRoleMenuByRoleId(Long id);
}
