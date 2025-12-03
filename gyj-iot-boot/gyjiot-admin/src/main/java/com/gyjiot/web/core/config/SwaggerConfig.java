package com.gyjiot.web.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2配置（适配Spring Boot2.5 + Springfox 2.9.2）
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    /**
     * 创建API应用
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2) // 替换OAS_30为SWAGGER_2（2.9.2唯一支持的类型）
                .apiInfo(apiInfo())
                .select()
                // 扫描控制器包（根据你的项目实际包名调整，保持和原来一致）
                .apis(RequestHandlerSelectors.basePackage("com.gyjiot.web"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes());
    }

    /**
     * 构建API文档信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("GYJIOT物联网系统API文档")
                .description("GYJIOT物联网系统接口文档")
                .contact(new Contact("GYJIOT", "", "xxx@xxx.com")) // 可自定义
                .version("2.1.0")
                .build();
    }

    /**
     * 配置token认证（替换原来的operationSelector，适配2.9.2）
     */
    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> apiKeyList = new ArrayList<>();
        // token参数名根据你的项目调整（比如token、Authorization）
        apiKeyList.add(new ApiKey("token", "token", "header"));
        return apiKeyList;
    }

    /**
     * 解决SwaggerUI访问404问题
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
