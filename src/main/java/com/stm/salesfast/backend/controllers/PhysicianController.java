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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stm.salesfast.backend.entity.AppointmentForPhysEntity;
import com.stm.salesfast.backend.entity.EDetailingMaterialEntity;
import com.stm.salesfast.backend.entity.MeetingExperienceEntity;
import com.stm.salesfast.backend.entity.MeetingUpdateEntity;
import com.stm.salesfast.backend.entity.PhysicianAppointmentCancellationEntity;
import com.stm.salesfast.backend.entity.VirtualLearningEntity;
import com.stm.salesfast.backend.services.specs.EDetailingMaterialService;
import com.stm.salesfast.backend.services.specs.MeetingExperienceService;
import com.stm.salesfast.backend.services.specs.PhysicianFetchService;
import com.stm.salesfast.backend.services.specs.PhysicianPortalService;
import com.stm.salesfast.constant.SessionConstants;

@Controller
public class PhysicianController {
	private Logger log = LoggerFactory.getLogger(PhysicianController.class.getName());
	
	@Autowired
	PhysicianFetchService physicianService;
	
	@Autowired
	PhysicianPortalService physicianPortal;
	
	@Autowired
	MeetingExperienceService meetingExperienceService;
	
	@Autowired
	EDetailingMaterialService eDetailingMatService;
	/**
	 * This request mapping is exempted from someone logging into
	 * the system.
	 * */
	@RequestMapping(value="/yourappointment", method=RequestMethod.GET)
	public String showAppointmentToCancel(Model model, @RequestParam int id){
		/*Fetch respective appointment and return the entity as an object after adding it to the model*/
		PhysicianAppointmentCancellationEntity appointmentDetail = physicianService.getAppointmentDetailForPhysician(id);
		log.info("Appointment for this physicians is : \n"+appointmentDetail);
		model.addAttribute("appointmentDetail", appointmentDetail);
		return appointmentDetail.getStatus().equals("CANCELLED") ? "appointmentcancelled":"appointmentcancellation";
	}
	
	@RequestMapping(value="/showmeetingsphys", method=RequestMethod.GET)
	public String showAppointmentForPhys(Model model){
		log.info("Meeting information for physicians");
		
		/* Id sent as argument in call below is userId for the physician logged in. 
		 * However, appointments are saved corresponding to physician ids,
		 * therefore physician id will be fetched corresponding to this user id inside PhysicianPortalService
		 * */
		List<AppointmentForPhysEntity> appointmentsForPhysician = physicianPortal.getMeetingsForPhysician(SessionConstants.USER_ID);
		for(AppointmentForPhysEntity each : appointmentsForPhysician) log.info(""+each);
		model.addAttribute("appointmentsForPhysician",appointmentsForPhysician);
		return "showmeetings_phy";
	}
	
	@RequestMapping(value="/addmeetingexpfromphy", method=RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public void meetingUpdate(@RequestBody MeetingExperienceEntity meetingExp) throws ParseException{
		log.info("Meeting update received  : "+meetingExp);
		meetingExperienceService.insert(meetingExp);
	}
	
	@RequestMapping(value="/edetailing", method=RequestMethod.GET)
	public String showEDetailingPage(Model model){
		return "edetailing";
	}
	
	@RequestMapping(value = "/getedetailingdata",method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public EDetailingMaterialEntity[] getEDetailingMaterial(Model model) {
		log.info("Get entities for user "+SessionConstants.USER_ID);
		List<EDetailingMaterialEntity> eDetailingMat = eDetailingMatService.getEDetailingMaterialForUI(SessionConstants.USER_ID);
		log.info("No of edetailing files "+eDetailingMat.size());
		return eDetailingMat.toArray(new EDetailingMaterialEntity[eDetailingMat.size()]);
		
	}
	
}