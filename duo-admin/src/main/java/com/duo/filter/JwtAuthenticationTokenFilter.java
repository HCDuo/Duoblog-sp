package com.duo.filter;

import com.alibaba.fastjson.JSON;
import com.duo.domain.ResponseResult;
import com.duo.domain.entity.LoginUser;
import com.duo.enums.AppHttpCodeEnum;
import com.duo.utils.JwtUtil;
import com.duo.utils.RedisCache;
import com.duo.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * <pre>
 * JwtAuthenticationTokenFilter 是一个自定义的过滤器，用于处理 JWT 身份认证。
 * 它从请求头中获取 JWT token，并根据 token 解析出用户ID。然后通过用户ID从 Redis 中获取用户信息，
 * 并将用户信息存入 SecurityContextHolder 中，以便后续处理请求时获取当前用户信息。
 * 如果请求头中没有 token，说明该接口不需要登录，直接放行。如果 token 解析失败或者获取不到用户信息，会返回相应的错误提示
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/28 1:42
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;
    //要实现一个doFilterInternal方法
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的token
        String token = request.getHeader("token");
        if(!StringUtils.hasText(token)){
            //说明该接口不需要登录  直接放行
            filterChain.doFilter(request, response);
            return;
        }
        //解析获取userid
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            //token超时  token非法
            //响应告诉前端需要重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        String userId = claims.getSubject();
        //从redis中获取用户信息
        LoginUser loginUser = redisCache.getCacheObject("login:" + userId);
        //如果获取不到
        if(Objects.isNull(loginUser)){
            //说明登录过期  提示重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        //存入SecurityContextHolder
        /*
        SecurityContextHolder 提供了一种简便的方式来访问当前用户的安全上下文，它是一个线程绑定的容器，意味着每个线程都有自己独立的安全上下文。
        通过 SecurityContextHolder，我们可以轻松地在应用程序的任何地方获取当前用户的信息，而不需要在每个方法或类中手动传递用户信息。
        通常，当用户进行身份认证后（比如通过用户名和密码登录），认证成功后的用户信息将被存储在 SecurityContextHolder 中。
        Spring Security 在认证成功后会自动将用户信息放入 SecurityContextHolder，以便在后续的请求处理过程中获取当前用户信息。
        */
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
