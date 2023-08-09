package com.duo.controller;

import com.duo.annotation.SystemLog;
import com.duo.domain.ResponseResult;
import com.duo.domain.dto.AddUserDto;
import com.duo.domain.dto.UserDto;
import com.duo.domain.vo.UserUpdateVo;
import com.duo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/8/4 0:05
 */
@RestController
@RequestMapping("/system/user")
@Api(tags = "用户",description = "用户相关接口")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    @SystemLog(BusinessName = "展示用户分页信息")
    @ApiOperation(value = "展示用户分页信息",notes = "展示用户分页信息")
    public ResponseResult adminArticleList(Integer pageNum, Integer pageSize, String userName, String phonenumber,String status){
        return userService.adminArticleList(pageNum,pageSize,userName,phonenumber,status);
    }

    @PostMapping()
    @SystemLog(BusinessName = "增加用户")
    @ApiOperation(value = "增加用户",notes = "增加用户")
    public ResponseResult<?> addUser(@RequestBody AddUserDto addUserDto) {
        return userService.addUser(addUserDto);
    }
    @DeleteMapping("/{id}")
    @SystemLog(BusinessName = "删除用户")
    @ApiOperation(value = "删除用户",notes = "删除用户")
    public ResponseResult<Object> deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    @SystemLog(BusinessName = "根据id查找更新用户")
    @ApiOperation(value = "根据id查找更新用户",notes = "根据id查找更新用户")
    public ResponseResult<UserUpdateVo> getUserInfo(@PathVariable Long id){
        return userService.getUserInfo(id);
    }
    @PutMapping
    @SystemLog(BusinessName = "更新用户")
    @ApiOperation(value = "更新用户",notes = "更新用户")
    public ResponseResult<?> updateUserInfo(@RequestBody @Validated UserDto userDto){
        return userService.updateUser(userDto);
    }
}
