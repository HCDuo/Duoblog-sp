package com.duo.controller;

import com.duo.annotation.SystemLog;
import com.duo.domain.ResponseResult;
import com.duo.domain.entity.User;
import com.duo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/29 14:15
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户",description = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    @SystemLog(BusinessName = "查询用户信息")
    @ApiOperation(value = "查询用户信息",notes = "查询用户信息")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }
    @PutMapping("/userInfo")
    @SystemLog(BusinessName = "更新用户信息")
    @ApiOperation(value = "更新用户信息",notes = "更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    @SystemLog(BusinessName = "注册用户")
    @ApiOperation(value = "注册用户",notes = "注册用户")
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }
}
