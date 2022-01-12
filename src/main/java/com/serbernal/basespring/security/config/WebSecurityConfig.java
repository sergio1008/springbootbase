package com.serbernal.basespring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private JWTAuthorizationFilter jwtAuthorizationFilter;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/auth/**").permitAll()
			.antMatchers("/test/**","/prueba/**")
			.hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
			.antMatchers("/admin")
			.hasAnyAuthority("ROLE_ADMIN")
			.anyRequest()
			.authenticated();
	}
}