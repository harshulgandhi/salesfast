package com.stm.salesfast.backend.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	private static final String APP_NAME = "SalesFast Application";

	@RequestMapping(value={"/", "/home"})
	public String index(Model model, HttpSession session) {
		model.addAttribute("appName", APP_NAME);
		return "home";
	}
}
