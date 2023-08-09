package com.duo.hander.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.duo.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <pre>
 * MyMetaObjectHandler 是一个自定义的 MyBatis Plus 元对象处理器，通过实现 MetaObjectHandler 接口，
 * 实现 insertFill 和 updateFill 方法来自动填充实体类中的字段值。
 * 在插入和更新操作时，该处理器会根据当前的用户信息来自动设置创建时间、更新时间、创建人和更新人等字段的值，从而简化开发过程并提高代码的可维护性
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/29 0:38
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = null;
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            e.printStackTrace();
            userId = -1L;//表示是自己创建
        }
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("createBy",userId , metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", userId, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName(" updateBy", SecurityUtils.getUserId(), metaObject);
    }
}
