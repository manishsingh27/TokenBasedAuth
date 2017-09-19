package com.adms.authz.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
@Order(1)
public class ResourceServiceConfiguration extends ResourceServerConfigurerAdapter {
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.
		antMatcher("/v1/api/**")
		.csrf().disable()
		.authorizeRequests()
			.antMatchers("/v1/api/**").hasAnyAuthority ("READ_PRIVILEGE","WRITE_PRIVILEGE");
		//.anyRequest().authenticated();
	}
	

}