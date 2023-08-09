package com.duo.controller;

import com.duo.annotation.SystemLog;
import com.duo.domain.ResponseResult;
import com.duo.domain.entity.LoginUser;
import com.duo.domain.entity.Menu;
import com.duo.domain.entity.User;
import com.duo.domain.vo.AdminUserInfoVo;
import com.duo.domain.vo.RoutersVo;
import com.duo.domain.vo.UserInfoVo;
import com.duo.enums.AppHttpCodeEnum;
import com.duo.exception.SystemException;
import com.duo.service.LoginService;
import com.duo.service.MenuService;
import com.duo.service.RoleService;
import com.duo.utils.BeanCopyUtils;
import com.duo.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/31 17:41
 */
@RestController
@Api(tags = "登录操作",description = "登录操作相关接口")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    @SystemLog(BusinessName = "登录")
    @ApiOperation(value = "登录",notes = "登录")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    @GetMapping("getInfo")
    @SystemLog(BusinessName = "获得角色")
    @ApiOperation(value = "获得角色",notes = "获得角色")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        //获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //封装数据返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKeyList,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("getRouters")
    @SystemLog(BusinessName = "得到权限")
    @ApiOperation(value = "得到权限",notes = "得到权限")
    public ResponseResult<RoutersVo> getRouters(){
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }

    @PostMapping("/user/logout")
    @SystemLog(BusinessName = "登出")
    @ApiOperation(value = "登出",notes = "登出")
    public ResponseResult logout(){
        return loginService.logout();
    }

}
