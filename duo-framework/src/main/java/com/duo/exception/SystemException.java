package com.duo.exception;

import com.duo.enums.AppHttpCodeEnum;

/**
 * <pre>
 *  SystemException 异常类用于表示系统级别的异常，它继承了 RuntimeException，并可以携带自定义的错误码和错误信息。
 *  在构造方法中，可以传入一个 AppHttpCodeEnum 枚举对象，以便快速设置错误码和错误信息
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/28 16:08
 */
public class SystemException extends RuntimeException{

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

}
