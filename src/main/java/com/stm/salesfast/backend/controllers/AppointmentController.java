package com.stm.salesfast.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.stm.salesfast.backend.dto.PhysicianStgDto;
import com.stm.salesfast.backend.entity.AppointmentEntity;
import com.stm.salesfast.backend.services.specs.AlignmentFetchService;
import com.stm.salesfast.backend.services.specs.AppointmentService;
import com.stm.salesfast.backend.services.specs.UserAccountService;

@Controller
public class AppointmentController {
	
	String CURRENTUSERNAME = "johny";
	
	
	@Autowired
	private AlignmentFetchService alignmentFetchService;
	
	@Autowired
	AppointmentService appointmentFetchService;
	
	@Autowired
	UserAccountService userAccountService;
	
	@RequestMapping(value="/showappointments", method=RequestMethod.GET)
	public String showAppointments(Model model){
		
		/*Fetching fixed appointments
		 */
		List<AppointmentEntity> appointmentsList = appointmentFetchService.getAppointmentToShow(userAccountService.getUserIdByUserName(CURRENTUSERNAME));
		
		List<PhysicianStgDto> alignedPhysicianInVicinity = alignmentFetchService.getAlignmentByUserIdInVicinityOfAppointments(
				(userAccountService.getUserAccountByUserName(CURRENTUSERNAME)).getUserId());
		
		model.addAttribute("listOfAppointments", appointmentsList);
		model.addAttribute("listOfPhysInVicinity", alignedPhysicianInVicinity);
		return "showappointment";
	}
}
