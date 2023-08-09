package com.duo.controller;

import com.duo.annotation.SystemLog;
import com.duo.domain.ResponseResult;
import com.duo.domain.entity.User;
import com.duo.enums.AppHttpCodeEnum;
import com.duo.exception.SystemException;
import com.duo.service.BlogLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/28 0:15
 */
@RestController
@Api(tags = "登录操作",description = "登录以及退出相关接口")
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;
    @PostMapping("/login")
    @SystemLog(BusinessName = "用户登录")
    @ApiOperation(value = "用户登录",notes = "进行用户登录")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }
    @PostMapping("/logout")
    @SystemLog(BusinessName = "退出登录")
    @ApiOperation(value = "退出登录",notes = "用户退出登录")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}
