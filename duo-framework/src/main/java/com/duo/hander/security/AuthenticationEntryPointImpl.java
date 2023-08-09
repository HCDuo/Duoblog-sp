package com.duo.hander.security;

import com.alibaba.fastjson.JSON;
import com.duo.domain.ResponseResult;
import com.duo.enums.AppHttpCodeEnum;
import com.duo.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 *  AuthenticationEntryPoint 是Spring Security提供的接口，用于处理认证入口点。
 *  当用户尝试访问受保护的资源而没有提供任何凭据（未认证），或者凭据无效时（认证失败），将引发 AuthenticationException。
 *  AuthenticationEntryPoint 的任务是为这种情况提供一个处理入口点，并向客户端发送适当的响应，通常是要求用户进行登录。
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/28 15:53
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        /*
        commence 方法是 AuthenticationEntryPoint 接口的实现，用于处理认证入口点。
        当发生未认证或认证失败的情况时，Spring Security将调用这个方法，
        并将相关信息传递给它，包括 HttpServletRequest 请求对象、 HttpServletResponse 响应对象和 AuthenticationException 异常对象。
        在这个实现中，首先通过 authException.printStackTrace(); 将认证异常信息打印到控制台。这样做通常是为了方便调试和日志记录。
        */

        authException.printStackTrace();
        // 如果是InsufficientAuthenticationException异常，表示未提供足够的凭据进行认证，将返回需要登录的错误信息；
        // 如果是 BadCredentialsException 异常，表示凭证（用户名或密码）错误，将返回登录错误信息；
        ResponseResult result = null;
        if(authException instanceof BadCredentialsException){
            result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(),authException.getMessage());
        }else if(authException instanceof InsufficientAuthenticationException){
            result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }else{
            result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"认证或授权失败");
        }
        //响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
