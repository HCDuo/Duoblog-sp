package com.duo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duo.domain.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-31 19:09:54
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long userid);

}
