package com.duo.hander.security;

import com.alibaba.fastjson.JSON;
import com.duo.domain.ResponseResult;
import com.duo.enums.AppHttpCodeEnum;
import com.duo.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 * AccessDeniedHandler 是Spring Security提供的接口，用于处理访问被拒绝的情况。
 * 当用户在访问受保护的资源时，如果其权限不足，就会引发 AccessDeniedException，表示访问被拒绝。
 * 此接口定义了一个 handle 方法，用于处理访问被拒绝的情况，并向客户端返回相应的错误信息
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/28 15:54
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        /*
        handle 方法是AccessDeniedHandler 接口的实现，用于处理访问被拒绝的情况。
        当发生访问被拒绝的情况时，Spring Security将调用这个方法，并将相关信息传递给它，
        包括 HttpServletRequest 请求对象、 HttpServletResponse 响应对象和 AccessDeniedException 异常对象。
        在这个实现中，首先通过 accessDeniedException.printStackTrace();
        将访问被拒绝的异常信息打印到控制台。这样做通常是为了方便调试和日志记录。
        */
        accessDeniedException.printStackTrace();
        ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        //响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
