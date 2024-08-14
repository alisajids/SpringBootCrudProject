package config;

//import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
//import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
//import org.springframework.hateoas.client.LinkDiscoverer;
//import org.springframework.hateoas.client.LinkDiscoverers;
//import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
//import org.springframework.plugin.core.SimplePluginRegistry;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile({"!prod"}
public class SwaggerConfig {

  @Bean
  public Docket createApi(){
    return new Docket(DocumentationType.SWAGGER_2)
      .apiInfo(apiInfo())
      .select()
      .apis(RequestHandleSelector.basePackage("com.controller")
      .paths(PathSelector.any())
      .build()
      .securitySchemes(Arrays.asList(apiKey());      
  }

private ApiInfo apiInfo(){
  return new ApiInfoBuilder()
    .title("Project Name")
    .description("")
    .termsOfServiceUrl("")
    .license("")
    .licenseUrl("")
    .build();
}
private static ApiKey apiKey(){
  return new ApiKey("Bearer", "Authorization", "header");
}

@Bean
public Docket apiYaml(){
   return new Docket(DocumentationType.SWAGGER_2)
      .select()
      .apis(RequestHandleSelector.basePackage("com.controller")
      .build()
      .produces(Collections.singleton("application/json"))
      .protocols(new HashSet<>(Arrays.asList<>("http", "https")))
      .groupName("yaml")
      .produces(Collections.singleton("application/json"));
      //.securitySchemes(Arrays.asList(apiKey());  
}
}
