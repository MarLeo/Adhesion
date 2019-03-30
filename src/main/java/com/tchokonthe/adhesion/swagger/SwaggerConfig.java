package com.tchokonthe.adhesion.swagger;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private Contact contact = new Contact("Martin Tchokonthe", "https://github.com/MarLeo", "marin.aurele12@gmail.com");

    private ApiInfo apiInfo(String version) {
        return new ApiInfoBuilder()
                .title("Manage Contact and Contribution API")
                .description("Manage Contact and Contribution  for testing")
                .version(version)
                .termsOfServiceUrl("Terms of Service")
                .contact(contact)
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .build();
    }

    @Bean
    public Docket usersV1() {
        return new Docket(SWAGGER_2)
                .groupName("v1.0/users/api/docs")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tchokonthe.adhesion.controller"))
                .paths(regex("/api/users/v1.0.*"))
                .build()
                .apiInfo(apiInfo("1.0"));
    }

    @Bean
    public Docket authoritiesV1() {
        return new Docket(SWAGGER_2)
                .groupName("v1.0/authorities/api/docs")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tchokonthe.adhesion.controller"))
                .paths(regex("/api/authorities/v1.0.*"))
                .build()
                .apiInfo(apiInfo("1.0"));
    }

    @Bean
    public Docket authV1() {
        return new Docket(SWAGGER_2)
                .groupName("v1.0/auth/api/docs")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tchokonthe.adhesion.controller"))
                .paths(regex("/api/auth/v1.0.*"))
                .build()
                .apiInfo(apiInfo("1.0"));
    }

}
