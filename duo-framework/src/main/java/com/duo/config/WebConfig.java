package com.duo.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * <pre>
 * 解决跨域问题
 * WebConfig 配置类中通过实现 WebMvcConfigurer 接口来自定义 Spring MVC 的配置。
 * 它解决了跨域问题，并配置了 FastJson 作为消息转换器，用于处理 JSON 格式的数据，同时设置了 JSON 输出的格式和日期格式化的样
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/26 16:46
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    //    跨域资源共享是一种安全策略，用于控制一个源（domain/origin）的网页是否可以请求另一个源的资源。
    //    浏览器实施了这个策略，以防止潜在的安全威胁。当前端代码运行在一个域名下，而请求的后端服务运行在不同的域名下时，就需要进行跨域请求。
    //    CORS配置允许服务器声明哪些来源是被允许的，以及允许哪些HTTP方法、请求头等。
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 是否允许cookie
                .allowCredentials(true)
                // 设置允许的请求方式
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                // 设置允许的header属性
                .allowedHeaders("*")
                // 跨域允许时间
                .maxAge(3600);
    }
    //    消息转换器负责将Java对象转换为HTTP响应体（serialization）或将HTTP请求体转换为Java对象（deserialization）。
    //    在这里，通过配置FastJSON消息转换器，定义了如何处理JSON格式的数据
    @Bean//使用@Bean注入fastJsonHttpMessageConvert
    public HttpMessageConverter fastJsonHttpMessageConverters() {
        //1.需要定义一个Convert转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");

        SerializeConfig.globalInstance.put(Long.class, ToStringSerializer.instance);

        fastJsonConfig.setSerializeConfig(SerializeConfig.globalInstance);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastConverter;
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverters());
    }
}
