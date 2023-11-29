package com.duo.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * Bean拷贝工具类封装
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/26 18:03
 */
public class BeanCopyUtils {

    private BeanCopyUtils() {
    }

    /**
     * 复制属性值从源对象到目标对象，并返回目标对象。
     *
     * @param source 源对象
     * @param clazz  目标对象的类
     * @param <V>    目标对象类型
     * @return 复制后的目标对象
     */
    public static <V> V copyBean(Object source,Class<V> clazz) {
        //创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();
            //实现属性copy
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果
        return result;
    }
    /**
     * 复制属性值从源对象列表到目标对象列表，并返回目标对象列表。
     *
     * @param list  源对象列表
     * @param clazz 目标对象的类
     * @param <O>   源对象类型
     * @param <V>   目标对象类型
     * @return 复制后的目标对象列表
     */
    public static <O,V> List<V> copyBeanList(List<O> list, Class<V> clazz){
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}
