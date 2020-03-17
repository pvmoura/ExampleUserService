package com.example.user;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.user.controller.ApiController;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage(ApiController.class.getPackage().getName()))
                //.paths(PathSelectors.ant("/*"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, Arrays.asList(new ResponseMessageBuilder().code(500)
                                .message("500 Internal Server Error")
                                .responseModel(new ModelRef("string"))
                                .build(),
                        new ResponseMessageBuilder().code(403)
                                .message("Forbidden!!!!!")
                                .build()));
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("Viomephi DB Participant Svc REST API",
                "APIs for Viomephi DB Participant table CRUD and other operations.",
                "Beta",
                "Terms of service",
                new Contact("NY Engineering Team", "www.viome.com", "nyengineering@viome.com"),
                "Viome Inc.",
                "www.viome.com", Collections.emptyList());
        return apiInfo;
    }
}