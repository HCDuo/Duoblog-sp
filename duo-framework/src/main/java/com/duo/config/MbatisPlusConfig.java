package com.duo.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 *  MP支持分页配置
 *  MbatisPlusConfig 配置类中通过 @Bean 注解创建了一个 MybatisPlusInterceptor 的 Bean，
 *  并添加了一个内部拦截器 PaginationInnerInterceptor，以实现 MyBatis Plus 的分页功能。
 *  这样配置后，当你使用 MyBatis Plus 进行查询时，如果使用了分页功能，它将自动帮你拦截并添加分页的 SQL 语句，简化了分页查询的操作
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/26 21:20
 */
@Configuration
public class MbatisPlusConfig {

    /**
     * 3.4.0之后版本
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }
}
