package com.duo.controller;

import com.duo.annotation.SystemLog;
import com.duo.domain.ResponseResult;
import com.duo.domain.dto.RoleAddDTO;
import com.duo.domain.dto.RoleStatusDto;
import com.duo.domain.dto.RoleUpdateDto;
import com.duo.domain.vo.PageVo;
import com.duo.domain.vo.RoleSimpleVo;
import com.duo.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/8/3 14:55
 */
@RestController
@RequestMapping("/system/role")
@Api(tags = "角色",description = "角色相关接口")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    @SystemLog(BusinessName = "展示角色分页信息")
    @ApiOperation(value = "展示角色分页信息",notes = "展示角色分页信息")
    public ResponseResult<PageVo> pageRoleList(Integer pageNum, Integer pageSize, String roleName,String status){
        return roleService.pageRoleList(pageNum,pageSize,roleName,status);
    }

    @PutMapping("/changeStatus")
    @SystemLog(BusinessName = "改变角色状态")
    @ApiOperation(value = "改变角色状态",notes = "改变角色状态")
    public ResponseResult<?> changeStatus(@Validated @RequestBody RoleStatusDto roleStatus) {
        return roleService.changeStatus(roleStatus);
    }

    @PostMapping()
    @SystemLog(BusinessName = "增加角色")
    @ApiOperation(value = "增加角色",notes = "增加角色")
    public ResponseResult<?> addRole(@RequestBody RoleAddDTO roleAddDTO) {
        return roleService.addRole(roleAddDTO);
    }

    @GetMapping("{id}")
    @SystemLog(BusinessName = "根据id查找更新角色")
    @ApiOperation(value = "根据id查找更新角色",notes = "根据id查找更新角色")
    public ResponseResult<?> adminRoleUpdateList(@PathVariable("id") Long id){
        return roleService.adminRoleUpdateList(id);
    }

    @PutMapping
    @SystemLog(BusinessName = "更新角色")
    @ApiOperation(value = "更新角色",notes = "更新角色")
    public ResponseResult<?> updateRole(@RequestBody @Validated RoleUpdateDto roleUpdateDto) {
        return roleService.updateRole(roleUpdateDto);
    }

    @DeleteMapping("/{id}")
    @SystemLog(BusinessName = "删除角色")
    @ApiOperation(value = "删除角色",notes = "删除角色")
    public ResponseResult<?> deleteRole(@PathVariable Long id) {
        return roleService.deleteRoleById(id);
    }

    @GetMapping("/listAllRole")
    @SystemLog(BusinessName = "角色列表")
    @ApiOperation(value = "角色列表",notes = "角色列表")
    public ResponseResult<List<RoleSimpleVo>> listAllRole() {
        return roleService.getAllRole();
    }
}
