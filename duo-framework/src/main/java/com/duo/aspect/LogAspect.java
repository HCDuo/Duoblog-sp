package com.duo.aspect;

import com.alibaba.fastjson.JSON;
import com.duo.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 *  这是一个使用 Spring AOP 的切面类 LogAspect，用于实现对带有 @SystemLog 注解的方法进行日志记录的功能。
 *  实现了在带有 @SystemLog 注解的方法执行前后记录日志信息的功能。
 *  通过 AOP 的切面技术，我们可以将日志记录的逻辑从业务逻辑中解耦出来，使代码更加清晰和可维护
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/29 19:11
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.duo.annotation.SystemLog)")
    public void pt(){

    }

    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object ret;
        try {
            handleBefore(joinPoint);
            ret = joinPoint.proceed();
            handleAfter(ret);

        } finally {
            // 结束后换行
            log.info("=======End=======" + System.lineSeparator());
        }
        return ret;
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        SystemLog systemLog = getSystemLog(joinPoint);

        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}",request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName   : {}",systemLog.BusinessName());
        // 打印 Http method
        log.info("HTTP Method    : {}",request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}",joinPoint.getSignature().getDeclaringTypeName(),((MethodSignature) joinPoint.getSignature()).getName());
        // 打印请求的 IP
        log.info("IP             : {}",request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    private void handleAfter(Object ret) {
        // 打印出参
        log.info("Response       : {}", JSON.toJSONString(ret));
    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod().getAnnotation(SystemLog.class);
    }
}
