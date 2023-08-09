package com.duo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *  用于记录日志
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/29 19:09
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SystemLog {
    String BusinessName();
}
