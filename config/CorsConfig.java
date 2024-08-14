package com.config;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
  @Override
  public void addCorsConfigMappings(CorsRegistry registry){
    registry.addMapping("*")
      .allowedOrigins("https://url.com");
      .allowedMethods("GET", "POST")
      .allowedHeaders("*");
  }
}
