package com.stm.salesfast.backend.controllers;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.sql.Date;
import java.sql.Time;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.stm.salesfast.backend.entity.AlignedPhysicianEntity;
import com.stm.salesfast.backend.services.specs.AlignmentFetchService;
import com.stm.salesfast.backend.services.specs.AppointmentService;
import com.stm.salesfast.backend.services.specs.PhysicianFetchService;
import com.stm.salesfast.backend.services.specs.ReminderService;
import com.stm.salesfast.backend.services.specs.UserAccountService;
import com.stm.salesfast.backend.utils.AjaxRequestListMapper;
import com.stm.salesfast.backend.utils.SalesFastUtilities;

@Controller
public class AlignmentController {
	private Logger log = LoggerFactory.getLogger(LoginController.class.getName());
	String CURRENTUSERNAME = "";
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private AlignmentFetchService alignmentFetchService;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private PhysicianFetchService physicianService; 
	
	@Autowired
	private ReminderService reminders;
	
	
	@RequestMapping(value="/showalignments", method=RequestMethod.GET)
	public String showAlignments(Model model){
		
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CURRENTUSERNAME = user.getUsername(); //get logged in user name
	    log.info("\nLogged in user is : "+CURRENTUSERNAME+" and his role is "+user.getAuthorities());
		
		/* These alignments are the ones that haven't been 
		 * converted to an appointment yet.
		 * */
		List<AlignedPhysicianEntity> alignedPhysician = alignmentFetchService.getAlignmentByUserIdToShow(
				(userAccountService.getUserAccountByUserName(CURRENTUSERNAME)).getUserId());
		model.addAttribute("listOfAlignedPhysician", alignedPhysician);
		return "showalignments";
	}
		
	
	@RequestMapping(value="/toRedirect", method=RequestMethod.GET)
	public String forRedirecting(){	
		return "showappointment";
	}
	
	@RequestMapping(value="/fixappointments", method=RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public void fixAppointment(@RequestBody AjaxRequestListMapper[] appointments) throws ParseException{
		for (AjaxRequestListMapper appointmentList : appointments){
			log.info("Appointment fixed for physician = "+appointmentList);
			Time selectedTime = SalesFastUtilities.getTimeForStringTime(appointmentList.getAppointmentTime(), "HH:mm");
			Date selectedDate = SalesFastUtilities.getDateForStringTime(appointmentList.getAppointmentDate(), "yyyy-MM-dd"); 
			appointmentService.addAppointment(appointmentList.getPhysicianId(), selectedTime, selectedDate, appointmentList.getAppointmentStatus(), appointmentList.getProductId(), appointmentList.getAdditionalNotes());
		}
		/* To set reminders in case any follow up appointment
		 * was fixed for any time in next day*/
		reminders.followUpCallReminders();
//		return "forward:/toRedirect";
	} 
}
