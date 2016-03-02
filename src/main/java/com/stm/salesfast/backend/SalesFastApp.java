package com.stm.salesfast.backend;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.stm.salesfast.backend.services.specs.AlignmentCreationService;

@Component
public class SalesFastApp {

	@Autowired
	private AlignmentCreationService createAlignments;
	
	@Scheduled(fixedRate=86400000)
	public void printWelcomeMessage() throws ParseException{
		System.out.println(" **** SALES FAST APPLICATION UP AND RUNNING **** ");
//		createAlignments.createAlignments();
		createAlignments.getDataForProcessing();
		createAlignments.calculatePhysicianImportance();
	}
}
