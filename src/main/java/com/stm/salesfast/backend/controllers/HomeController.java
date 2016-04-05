package com.stm.salesfast.backend.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stm.salesfast.backend.services.specs.UserDetailService;
import com.stm.salesfast.constant.SessionConstants;

@Controller
public class HomeController {
	
	private static final String APP_NAME = "SalesFast Application";
	
	@Autowired
	UserDetailService userDetails;

	@RequestMapping(value={"/", "/home"})
	public String index(Model model, HttpSession session) {
		model.addAttribute("appName", APP_NAME);
		return "home";
//		return "redirect:/showalignments";
		
	}
	
	@RequestMapping(value="/getcurrentuser", method=RequestMethod.GET)
	@ResponseBody
	public String getCurrentUserName() {
		return userDetails.getUserCompleteName(SessionConstants.USER_ID);
	}
	
}
