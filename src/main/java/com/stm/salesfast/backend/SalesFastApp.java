package com.stm.salesfast.backend;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SalesFastApp {
	
	@Scheduled(fixedRate=86400000)
	public void printWelcomeMessage(){
		System.out.println(" **** SALES FAST APPLICATION UP AND RUNNING **** ");
	}
}
