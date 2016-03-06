package com.stm.salesfast.backend.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class VirtualLearningController {
	private Logger log = LoggerFactory.getLogger(VirtualLearningController.class.getName());

	@RequestMapping(value = "/virtuallearning")
	public String virtualLearningLandingPage() {
		return "virtuallearning";
	}
}
