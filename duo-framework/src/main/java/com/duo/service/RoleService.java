package com.duo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duo.domain.ResponseResult;
import com.duo.domain.dto.RoleAddDTO;
import com.duo.domain.dto.RoleStatusDto;
import com.duo.domain.dto.RoleUpdateDto;
import com.duo.domain.entity.Role;
import com.duo.domain.vo.PageVo;
import com.duo.domain.vo.RoleSimpleVo;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-07-31 19:09:55
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult<PageVo> pageRoleList(Integer pageNum, Integer pageSize, String roleName, String status);

    ResponseResult<?> changeStatus(RoleStatusDto roleStatus);

    ResponseResult<?> addRole(RoleAddDTO roleAddDTO);

    ResponseResult<?> adminRoleUpdateList(Long id);

    ResponseResult<?> updateRole(RoleUpdateDto roleUpdateDto);

    ResponseResult<?> deleteRoleById(Long id);

    ResponseResult<List<RoleSimpleVo>> getAllRole();
}
