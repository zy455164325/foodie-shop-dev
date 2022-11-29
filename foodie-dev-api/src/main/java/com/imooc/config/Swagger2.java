package com.imooc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName Swagger2
 * @Description Swagger2配置类
 * @Author braveZeng
 * @Date 10:35 2022/11/29
 * Version 1.0
 **/
@Configuration
@EnableSwagger2
public class Swagger2 {

    // http://localhost:8088/swagger-ui.html
    // http://localhost:8088/doc.html


    //配置Swagger2
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2) //指定api类型为Swagger2
                .apiInfo(apiInfo())  //用于定义api文档汇总信息
                .select().apis(RequestHandlerSelectors
                        .basePackage("com.imooc.controller")) //指定controller包
                .paths(PathSelectors.any()) //针对所有controller
                .build();

    }


    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("天天吃货 电商平台API") //文档页标题
                .contact(new Contact("imooc",
                        "https://www.imooc.com",
                        "455164325@qq.com")) //联系人信息
                .description("专为天天吃货提供的API文档") //详细信息
                .version("1.0.1") //文档版本号
                .termsOfServiceUrl("https://www.imooc.com") //网站地址
                .build();
    }

}
