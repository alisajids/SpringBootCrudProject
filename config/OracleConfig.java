package config;

import lombok.Getter;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "spring.datasource")
@ConstructorBinding
@Getter
@AllArgsConstructor  
public final class OracleConfig{
  private String url;
  private String userName;
  private String password;
  private String driverClassName;

  //add param in application.yaml or application properties like
  //spring.datasource.url=
   //spring.datasource.username=
   //spring.datasource.password=
   //spring.datasource.driverClassName=
}
