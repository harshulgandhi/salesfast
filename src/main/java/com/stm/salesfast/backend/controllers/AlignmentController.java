package com.stm.salesfast.backend.controllers;

import java.text.ParseException;
import java.util.List;
import java.sql.Time;

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
import com.stm.salesfast.backend.services.specs.AppointmentService;
import com.stm.salesfast.backend.services.specs.PhysicianFetchService;
import com.stm.salesfast.backend.services.specs.UserAccountService;
import com.stm.salesfast.backend.utils.AjaxRequestListMapper;
import com.stm.salesfast.backend.utils.SalesFastUtilities;

@Controller
public class AlignmentController {
	private Logger log = LoggerFactory.getLogger(LoginController.class.getName());
	String CURRENTUSERNAME = "johny";
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private AlignmentFetchService alignmentFetchService;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private PhysicianFetchService physicianService; 
	
	@RequestMapping(value="/showalignments", method=RequestMethod.GET)
	public String showAlignments(Model model){
		
		/* THIS IS A TEST PRINT */
		/*User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String name = user.getUsername(); //get logged in user name
	    log.info("\nLogged in user is : "+name);*/
		
		/* These alignments are the ones that haven't been converted to an appointment
		 * yet.
		 * */
		List<PhysicianStgDto> alignedPhysician = alignmentFetchService.getAlignmentByUserIdToShow(
				(userAccountService.getUserAccountByUserName(CURRENTUSERNAME)).getUserId());
		
		model.addAttribute("listOfAlignedPhysician", alignedPhysician);
		return "showalignments";
	}
		
	@RequestMapping(value="/toRedirect", method=RequestMethod.GET)
	public String forRedirecting(){	
		log.info("Redirecting!!");
		return "showappointment";
	}
	
	@RequestMapping(value="/fixappointments", method=RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public String fixAppointment(@RequestBody AjaxRequestListMapper[] appointments) throws ParseException{
		for (AjaxRequestListMapper appointmentList : appointments){
			log.info("Appointment fixed for physician = "+appointmentList.getPhysicianId()+" @ "+appointmentList.getAppointmentTime());
			Time selectedTime = SalesFastUtilities.getTimeForStringTime(appointmentList.getAppointmentTime(), "HH:mm");
			appointmentService.addAppointment(appointmentList.getPhysicianId(), selectedTime, new String("CONFIRMED"));
			
			
			/*Send email with appointmentId as get parameter*/
			int appointmentId = appointmentService.getAppointmentId(CURRENTUSERNAME, appointmentList.getPhysicianId());
			String physicianEmail = physicianService.getPhysicianById(appointmentList.getPhysicianId()).getEmail();
			/*  Send email to physicianEmail id with URL as 
			 *  '127.0.0.1/yourappointment?id=appointmentId' 
			 **/
			
			
		}
		return "redirect:/toRedirect";
	} 
}
