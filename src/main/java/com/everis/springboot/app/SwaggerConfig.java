package com.everis.springboot.app;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

@Configuration
@EnableSwagger2WebFlux
public class SwaggerConfig {
  /**

   * Esto sirve para borrar los registros de la base de datos y volver
   * a crear 5 datos de prueba.

   */

  @Bean
  public Docket newsApi() {
    return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api-infos")
                .apiInfo(apiInfo())
                .directModelSubstitute(LocalDateTime.class, Date.class)
                .select()
                .paths(PathSelectors.regex("/api.*"))
                .build();
  }
 
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
                .title("Infos REST api")
                .description("PoC of a REST api, Infos")
                .termsOfServiceUrl("http://en.wikipedia.org/wiki/Terms_of_service")
                .license("Apache License Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("2.0")
                .build();
  }
}
