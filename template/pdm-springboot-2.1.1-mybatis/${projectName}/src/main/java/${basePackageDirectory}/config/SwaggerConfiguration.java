package org.mpro.pro.fj.cs5g.swagger;

import java.sql.Date;
import java.sql.Timestamp;

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

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .directModelSubstitute(Timestamp.class, Long.class)
                .directModelSubstitute(Date.class, Long.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.mpro.pro.fj.cs5g"))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("5G邻区自动设置API文档").contact(new Contact("ludongrong", "", "15880161493@139.com"))
                .version("1.0.0").description("本文档作为接入接口的规范，同时也作为后续接口联调系统测试验证的参考依据。\r\n" + 
                        "具体技术约定如下：\r\n" + 
                        "1.  基于HTTP协议传输\r\n" + 
                        "2.  在应用层采用UTF-8编码方式\r\n" + 
                        "3.  时间字段格式yyyy-MM-dd hh24:mm:ss\r\n"+
                        "4.  应答状态码0表示成功，非0表示失败。没有返回issuccess" + 
                        "").build();
    }
}