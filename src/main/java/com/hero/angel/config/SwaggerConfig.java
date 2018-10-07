package com.hero.angel.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义文档
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    // 默认令牌
    private static final String authorization = "Bearer eyJhbGciOiJIUzUxMiJ9" +
            ".eyJleHAiOjE1NDEzMDU4MzMsInN1YiI6InJvb3QiLCJjcmVhdGVkIjoxNTM4NzEzODMzNjkxfQ" +
            ".vlyUzoRUneqP-mgF3cxBT6fiAkYLqw-_o8JFBoF3R0apIGNBcmVTBNOO91r6RY3gkYvZPe-XsMHZar3cl18kNw";

    @Bean
    public Docket customDocket(){
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name("Authorization").description("令牌").defaultValue(authorization)
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); //header中的ticket参数非必填，传空也可以
        pars.add(ticketPar.build());    //根据每个方法名也知道当前方法在设置什么参数

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .build()
                .globalOperationParameters(pars)
                .apiInfo(apiInfo());
    }

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("api swagger document")
                .version("1.0.1")
                .build();
    }

}
