package com.stm.salesfast.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.stm.salesfast.backend.services.specs.AlignmentFetchService;
import com.stm.salesfast.backend.services.specs.UserAccountService;
import com.stm.salesfast.frontend.LoginUI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class LoginController {
	
	private Logger log = LoggerFactory.getLogger(LoginController.class.getName());
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private AlignmentFetchService alignmentFetch;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String loginPage(Model model){
		model.addAttribute("loginUiObj", new LoginUI());
		return "login";
	}
	
	@RequestMapping(value="/verifycredentials", method=RequestMethod.POST)
	public String verifyCredentials(@ModelAttribute LoginUI loginUiObj, Model model){
		model.addAttribute("loginUiObj", loginUiObj);
		log.info("\nUsername :"+loginUiObj.getUsername()+"\nPassword :"+loginUiObj.getPassword());
		
		return userAccountService.verifyUserCredentials(loginUiObj.getUsername(), loginUiObj.getPassword()) ?  "home" :  "login"; 
	}
}
