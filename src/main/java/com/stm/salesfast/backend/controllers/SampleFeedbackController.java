package com.stm.salesfast.backend.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stm.salesfast.backend.entity.SampleFeedbackEntity;
import com.stm.salesfast.backend.services.specs.SampleFeedbackService;

@Controller
public class SampleFeedbackController {

	private Logger log = LoggerFactory.getLogger(SampleFeedbackController.class.getName());

	@Autowired
	SampleFeedbackService sampleFeedbackService;
	
	@RequestMapping(value="/samplefeedback", method=RequestMethod.GET)
	public String samplefeedbackpage(@RequestParam(value="id") int productId,  @RequestParam(value="name") String productName, Model model){
		model.addAttribute("productId",productId);
		model.addAttribute("productName",productName);
		return "samplefeedback";
	}
	
	@RequestMapping(value="/submitsamplefeedback", method=RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public void meetingUpdate(@RequestBody SampleFeedbackEntity sampleFeedback){
		log.info("Sample feedback received  : "+sampleFeedback);
		sampleFeedbackService.insertFeedback(sampleFeedback);
	}
	
	
	
}
