package com.stm.salesfast.backend.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stm.salesfast.backend.dto.PhysicianStgDto;
import com.stm.salesfast.backend.services.specs.AlignmentFetchService;
import com.stm.salesfast.backend.services.specs.UserAccountService;
import com.stm.salesfast.backend.utils.AjaxRequestListMapper;

@Controller
public class AlignmentController {
	private Logger log = LoggerFactory.getLogger(LoginController.class.getName());
	String CURRENTUSERNAME = "johny";
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private AlignmentFetchService alignmentFetch;
	
	@RequestMapping(value="/showalignments", method=RequestMethod.GET)
	public String showAlignments(Model model){
		
		/* THIS IS A TEST PRINT */
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String name = user.getUsername(); //get logged in user name
	    log.info("\nLogged in user is : "+name);
		
		List<PhysicianStgDto> alignedPhysician = alignmentFetch.getAlignmentByUserIdToShow((userAccountService.getUserAccountByUserName(CURRENTUSERNAME)).getUserId());
		model.addAttribute("listOfAlignedPhysician", alignedPhysician);
		return "showalignments";
	}
	
	
	@RequestMapping(value="/fixappointments", method=RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public void fixAppointment(@RequestBody AjaxRequestListMapper[] appointments){
		for (AjaxRequestListMapper appointmentList : appointments){
			log.info("** Appointment fixed for physician = "+appointmentList.getPhysicianId()+" @ "+appointmentList.getAppointmentTime());
		}
		
	} 
}
