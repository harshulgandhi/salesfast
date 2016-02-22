package com.stm.salesfast.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.stm.salesfast.backend.dto.PhysicianStgDto;
import com.stm.salesfast.backend.entity.AppointmentEntity;
import com.stm.salesfast.backend.services.specs.AppointmentService;
import com.stm.salesfast.backend.services.specs.UserAccountService;

@Controller
public class AppointmentController {
	
	String CURRENTUSERNAME = "johny";
	
	@Autowired
	AppointmentService appointmentFetchService;
	
	@Autowired
	UserAccountService userAccountService;
	
	@RequestMapping(value="/showappointments", method=RequestMethod.GET)
	public String showAppointments(Model model){
		List<AppointmentEntity> appointmentsList = appointmentFetchService.getAppointmentToShow(userAccountService.getUserIdByUserName(CURRENTUSERNAME));
		model.addAttribute("listOfAppointments", appointmentsList);
		return "showappointments";
	}
	
	/*@RequestMapping(value="/showalignments", method=RequestMethod.GET)
	public String showAlignments(Model model){
		
		 THIS IS A TEST PRINT 
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String name = user.getUsername(); //get logged in user name
	    log.info("\nLogged in user is : "+name);
		
		List<PhysicianStgDto> alignedPhysician = alignmentFetch.getAlignmentByUserIdToShow((userAccountService.getUserAccountByUserName(CURRENTUSERNAME)).getUserId());
		model.addAttribute("listOfAlignedPhysician", alignedPhysician);
		return "showalignments";
	}*/
}
