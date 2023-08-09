package com.duo.utils;

import com.duo.domain.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * <pre>
 * SecurityUtils 是一个工具类，用于简化获取当前登录用户信息和进行用户权限判断的操作。
 * 通过调用该类中的方法，可以方便地获取当前登录用户的详细信息和判断用户是否为管理员。
 * 这些方法可以在需要获取当前登录用户信息或判断用户权限的地方使用。
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/29 0:34
 */
public class SecurityUtils
{

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser()
    {
        return (LoginUser) getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Boolean isAdmin(){
        Long id = getLoginUser().getUser().getId();
        return id != null && 1L == id;
    }

    public static Long getUserId() {
        return getLoginUser().getUser().getId();
    }
}
