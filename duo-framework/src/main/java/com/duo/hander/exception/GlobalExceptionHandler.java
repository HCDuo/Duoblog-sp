package com.duo.hander.exception;

import com.duo.domain.ResponseResult;
import com.duo.enums.AppHttpCodeEnum;
import com.duo.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <pre>
 *  GlobalExceptionHandler 是一个全局异常处理类，通过 @ExceptionHandler 注解标记多个异常处理方法，分别处理不同类型的异常。
 *  当系统中抛出对应类型的异常时，Spring Boot 会自动调用相应的异常处理方法，将异常信息封装成统一的 ResponseResult 对象返回给客户端。
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/28 16:15
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
        //打印异常信息
        log.error("出现了异常！ {}",e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }


    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){
        //打印异常信息
        log.error("出现了异常！ {}",e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }
}

