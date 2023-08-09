package com.duo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duo.constants.SystemConstants;
import com.duo.domain.ResponseResult;
import com.duo.domain.dto.RoleAddDTO;
import com.duo.domain.dto.RoleStatusDto;
import com.duo.domain.dto.RoleUpdateDto;
import com.duo.domain.entity.Role;
import com.duo.domain.vo.PageVo;
import com.duo.domain.vo.RoleSimpleVo;
import com.duo.domain.vo.RoleUpdateVo;
import com.duo.enums.AppHttpCodeEnum;
import com.duo.exception.SystemException;
import com.duo.mapper.RoleMapper;
import com.duo.mapper.RoleMenuMapper;
import com.duo.service.RoleService;
import com.duo.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-07-31 19:09:55
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    @Transactional
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员 如果是返回集合中只需要有admin
        if(id == 1L){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    @Transactional
    public ResponseResult<PageVo> pageRoleList(Integer pageNum, Integer pageSize, String roleName, String status) {
        // 构建查询条件(模糊查询)
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(roleName)) {
            wrapper.like(Role::getRoleName, roleName);
        }
        if (StringUtils.hasText(status)) {
            wrapper.like(Role::getStatus, status);
        }

        // 分页查询
        Page<Role> page = new Page<>(pageNum, pageSize);
        IPage<Role> rolePage = roleMapper.selectPage(page, wrapper);

        // 组装响应数据
        PageVo pageVo = new PageVo(rolePage.getRecords(), rolePage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    @Transactional
    public ResponseResult<?> changeStatus(RoleStatusDto roleStatus) {
        // 检查角色是否存在
        if(roleStatus.getRoleId() == 1){
            throw new SystemException(AppHttpCodeEnum.ADMIN_ERROR);
        }
        //检查是不是超级管理员
        Role role = roleMapper.selectById(roleStatus.getRoleId());
        if (role == null) {
            return ResponseResult.errorResult(500, "角色不存在");
        }
        //更新到数据库
        LambdaUpdateWrapper<Role> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Role::getId, roleStatus.getRoleId())
                .set(Role::getStatus, roleStatus.getStatus());
        int success = roleMapper.update(role, updateWrapper);
        if (success > 0) {
            // 状态更新成功
            return ResponseResult.okResult();
        } else {
            // 状态更新失败
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseResult<?> addRole(RoleAddDTO roleAddDTO) {
        // 角色名是否存在
        String roleName = roleAddDTO.getRoleName();
        Role existingRole = roleMapper.selectOne(new QueryWrapper<Role>().eq("role_name", roleName));
        if (existingRole != null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.ROLE_EXIST);
        }
        // 添加新角色
        Role newRole = BeanCopyUtils.copyBean(roleAddDTO, Role.class);
        newRole.setCreateTime(new Date());
        roleMapper.insert(newRole);
        // 给权限
        roleMenuMapper.insertRoleMenu(newRole.getId(), roleAddDTO.getMenuIds());
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult<?> adminRoleUpdateList(Long id) {
        //找出信息
        Role role = roleMapper.selectById(id);
        if (role == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.ROLE_NOT_EXIST);
        }

        RoleUpdateVo roleUpdateVo = BeanCopyUtils.copyBean(role,RoleUpdateVo.class);
        // 构建响应数据
        return ResponseResult.okResult(roleUpdateVo);
    }

    @Override
    @Transactional
    public ResponseResult<?> updateRole(RoleUpdateDto roleUpdateDto) {
        //找出这个id并匹配
        Role role = BeanCopyUtils.copyBean(roleUpdateDto, Role.class);
        updateById(role);
        List<Integer> menuIds = roleUpdateDto.getMenuIds();
        //删除roleMenu表中内容
        roleMenuMapper.deleteRoleMenuByRoleId(role.getId());
        //判断有没有更新，更新就加进roleMenuMapper表
        if (menuIds != null && menuIds.size() > 0) {
            roleMenuMapper.insertRoleMenu(role.getId(),menuIds);
        }
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult<?> deleteRoleById(Long id) {
        //判断有没有这个角色
        Role role = roleMapper.selectById(id);
        if (role == null) {
            throw new SystemException(AppHttpCodeEnum.ROLE_NOT_EXIST);
        }
        //超级管理员不能删除
        if (id == SystemConstants.ADMIN_ID) {
            return ResponseResult.errorResult(AppHttpCodeEnum.CAN_NOT_DELETE_ADMIN);
        }
        //逻辑删除
        LambdaUpdateWrapper<Role> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Role::getId,role.getId())
                .set(Role::getDelFlag,1);
        role.setUpdateTime(new Date());
        int success = roleMapper.update(role,updateWrapper);
        if (success > 0) {
            roleMenuMapper.deleteRoleMenuByRoleId(id);
            return ResponseResult.okResult();
        } else {
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseResult<List<RoleSimpleVo>> getAllRole() {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getStatus, 0);
        List<Role> roleList = list(wrapper);
        List<RoleSimpleVo> roleSimpleVoList = BeanCopyUtils.copyBeanList(roleList, RoleSimpleVo.class);
        return ResponseResult.okResult(roleSimpleVoList);
    }
}
