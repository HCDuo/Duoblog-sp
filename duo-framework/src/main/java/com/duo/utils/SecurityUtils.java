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
     * 获取当前登录用户的详细信息。
     *
     * @return 当前登录用户的详细信息
     */
    public static LoginUser getLoginUser()
    {
        return (LoginUser) getAuthentication().getPrincipal();
    }

    /**
     * 获取当前认证上下文的Authentication对象。
        *
        * @return 当前认证上下文的Authentication对象
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    /**
     * 检查当前登录用户是否为管理员。
     *
     * @return 如果当前登录用户是管理员，则返回true，否则返回false
     */
    public static Boolean isAdmin(){
        Long id = getLoginUser().getUser().getId();
        return id != null && 1L == id;
    }
    /**
     * 获取当前登录用户的ID。
     *
     * @return 当前登录用户的ID
     */
    public static Long getUserId() {
        return getLoginUser().getUser().getId();
    }
}
