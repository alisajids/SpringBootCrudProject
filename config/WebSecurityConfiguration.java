package com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(final WebSecurity web) throws Exception {
    	 web.ignoring().antMatchers(
    	            "/docs/**",
    	            "/v2/api-docs",
    	            "/configuration/**",
    	            "/swagger*/**",
    	            "/webjars/**");
    	  }
}
