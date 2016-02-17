package com.stm.salesfast.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.stm.salesfast.backend.services.impl.AlignmentFetchServiceImpl;
import com.stm.salesfast.backend.services.impl.UserAccountServiceImpl;
import com.stm.salesfast.frontend.LoginUI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class LoginController {
	
	private Logger log = LoggerFactory.getLogger(LoginController.class.getName());
	
	@Autowired
	private UserAccountServiceImpl userAccountService;
	
	@Autowired
	private AlignmentFetchServiceImpl alignmentFetch;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String loginPage(Model model){
		model.addAttribute("loginUiObj", new LoginUI());
		return "login";
	}
	
	@RequestMapping(value="/verifycredentials", method=RequestMethod.POST)
	public String verifyCredentials(@ModelAttribute LoginUI loginUiObj, Model model){
		model.addAttribute("loginUiObj", loginUiObj);
		log.info("Username :"+loginUiObj.getUsername()+"\nPassword :"+loginUiObj.getPassword());
		
		/* THIS IS A TEST PRINT */
		alignmentFetch.getAlignmentByUserId((userAccountService.getUserAccountByUserName(loginUiObj.getUsername())).getUserId());
		alignmentFetch.getAlignmentByUserIdToShow((userAccountService.getUserAccountByUserName(loginUiObj.getUsername())).getUserId());
		
		return userAccountService.verifyUserCredentials(loginUiObj.getUsername(), loginUiObj.getPassword()) ?  "testpage" :  "login"; 
	}
}
