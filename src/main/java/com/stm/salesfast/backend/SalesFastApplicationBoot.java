package com.stm.salesfast.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;


@SpringBootApplication
public class SalesFastApplicationBoot extends SpringBootServletInitializer{
	
	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(SalesFastApplicationBoot.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SalesFastApplicationBoot.class);
	}
}
