package com.stm.salesfast.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableScheduling
public class SalesFastApplicationBoot {
	public static void main(String[] args) {
		SpringApplication.run(SalesFastApplicationBoot.class);
	}
}
