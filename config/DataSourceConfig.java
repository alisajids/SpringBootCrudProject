package config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@Data
@EnableConfigurationProperties({OracleConfig.class})
@EnableTransactionManagement  
public class DataSourceConfig{

  @Primary
  @Bean(name = "oracle")
  public DataSource oracleDataSource(OracleConfig oracleConfig) {
	  log.debug("url:{},user:{}",oracleConfig.getUrl()
			  ,oracleConfig.getUserName());
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(oracleConfig.getUrl());
    config.setUsername(oracleConfig.getUserName());
    config.setPassword(oracleConfig.getPassword());
    config.setDriverClassName(oracleConfig.getDriverClassName());
    return new HikariDataSource(config);
  }
}
