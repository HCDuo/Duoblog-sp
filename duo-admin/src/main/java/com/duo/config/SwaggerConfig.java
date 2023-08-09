package com.duo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * <pre>
 * SwaggerConfig 是一个 Spring 配置类，用于配置 Swagger 在 Spring Boot 项目中的相关信息，
 * 包括 API 文档的基本信息和要生成文档的接口选择器。通过配置 Swagger，可以快速生成 API 文档，并提供交互式界面方便开发者测试和调试 API
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/30 15:59
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.duo.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("HCDuo", "http://www.HCDuo.com", "my@my.com");
        return new ApiInfoBuilder()
                .title("文档标题")
                .description("文档描述")
                .contact(contact)   // 联系方式
                .version("1.1.0")  // 版本
                .build();
    }
}
