package com.stm.salesfast.backend;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.stm.salesfast.backend.services.specs.UserAccountService;
import com.stm.salesfast.backend.interceptor.AccessControlInterceptor;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private UserAccountService service;

	@Autowired
	AccessControlInterceptor accessControlInterceptor;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations(
				"/resources/");
	}
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(accessControlInterceptor);
	}

}
