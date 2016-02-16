package com.stm.salesfast.backend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController {
	
	@RequestMapping(value="/testpage", method=RequestMethod.GET)
	public String testPage(){
		return "testpage";
	}
}
