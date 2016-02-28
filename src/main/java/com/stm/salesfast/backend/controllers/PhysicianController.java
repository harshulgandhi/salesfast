package com.stm.salesfast.backend.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.stm.salesfast.backend.entity.PhysicianAppointmentEntity;
import com.stm.salesfast.backend.services.specs.PhysicianFetchService;
import com.stm.salesfast.constant.ConstantValues;

@Controller
public class PhysicianController {
	String CURRENTUSERNAME = "";
	private Logger log = LoggerFactory.getLogger(PhysicianController.class.getName());
	
	@Autowired
	PhysicianFetchService physicianService;
	
	@RequestMapping(value="/yourappointment", method=RequestMethod.GET)
	public String showAppointment(Model model, @RequestParam int id){
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CURRENTUSERNAME = user.getUsername(); //get logged in user name
		log.info("Appointment id received is : "+id);
		/*Fetch respective appointment and return the entity as an object after adding it to the model*/
		PhysicianAppointmentEntity appointmentDetail = physicianService.getAppointmentDetailForPhysician(CURRENTUSERNAME, id);
		log.info("Appointment for this physicians is : \n"+appointmentDetail);
		model.addAttribute("appointmentDetail", appointmentDetail);
		
		return "appointmentcancellation";
	}
}
