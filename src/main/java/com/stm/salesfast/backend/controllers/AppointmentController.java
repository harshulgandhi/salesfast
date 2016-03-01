package com.stm.salesfast.backend.controllers;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import com.stm.salesfast.backend.dto.PhysicianStgDto;
import com.stm.salesfast.backend.entity.AlignedPhysicianEntity;
import com.stm.salesfast.backend.entity.AppointmentEntity;
import com.stm.salesfast.backend.entity.MeetingExperienceEntity;
import com.stm.salesfast.backend.services.specs.AlignmentFetchService;
import com.stm.salesfast.backend.services.specs.AppointmentService;
import com.stm.salesfast.backend.services.specs.MeetingExperienceService;
import com.stm.salesfast.backend.services.specs.MeetingUpdateService;
import com.stm.salesfast.backend.services.specs.UserAccountService;
import com.stm.salesfast.backend.entity.MeetingUpdateEntity;

@Controller
public class AppointmentController {
	private Logger log = LoggerFactory.getLogger(AppointmentController.class.getName());
	String CURRENTUSERNAME="";
	
	@Autowired
	private AlignmentFetchService alignmentFetchService;
	
	@Autowired
	AppointmentService appointmentFetchService;
	
	@Autowired
	UserAccountService userAccountService;
	
	@Autowired
	MeetingUpdateService meetingUpdateService;
	
	@Autowired
	MeetingExperienceService meetingExperienceService;
	
	@RequestMapping(value="/showappointments", method=RequestMethod.GET)
	public String showAppointments(Model model){
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CURRENTUSERNAME = user.getUsername(); //get logged in user name
		
		/*Fetching fixed appointments
		 */
		List<AppointmentEntity> appointmentsList = appointmentFetchService.getAppointmentToShow(userAccountService.getUserIdByUserName(CURRENTUSERNAME));
		
		List<AlignedPhysicianEntity> alignedPhysicianInVicinity = alignmentFetchService.getAlignmentByUserIdInVicinityOfAppointments(
				(userAccountService.getUserAccountByUserName(CURRENTUSERNAME)).getUserId());
		
		model.addAttribute("listOfAppointments", appointmentsList);
		model.addAttribute("listOfPhysInVicinity", alignedPhysicianInVicinity);
		return "showappointment";
	}
	
	
	@RequestMapping(value="/addmeetingupdate", method=RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public void meetingUpdate(@RequestBody MeetingUpdateEntity meetingUpdate) throws ParseException{
		log.info("Meeting update received  : "+meetingUpdate);
		meetingUpdateService.insertMeetinUpdate(meetingUpdate);
	}
	
	@RequestMapping(value="/addmeetingexperience", method=RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public void meetingExperience(@RequestBody MeetingExperienceEntity meetingExperience) {
		log.info("Meeting experience details received  : "+meetingExperience);
		meetingExperienceService.insert(meetingExperience);
	}
}





