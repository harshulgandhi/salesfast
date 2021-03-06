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
import com.stm.salesfast.backend.entity.FollowupAppointmentUpdateEntity;
import com.stm.salesfast.backend.entity.ManagerSuggestiveAlignmentsEntity;
import com.stm.salesfast.backend.entity.ManagerViewAlignmentsEntity;
import com.stm.salesfast.backend.services.specs.AlignmentFetchService;
import com.stm.salesfast.backend.services.specs.AppointmentService;
import com.stm.salesfast.backend.services.specs.PhysicianFetchService;
import com.stm.salesfast.backend.services.specs.ReminderService;
import com.stm.salesfast.backend.services.specs.UserAccountService;
import com.stm.salesfast.backend.utils.AjaxRequestListMapper;
import com.stm.salesfast.backend.utils.SalesFastUtilities;
import com.stm.salesfast.constant.SessionConstants;

@Controller
public class AlignmentController {
	private Logger log = LoggerFactory.getLogger(AlignmentController.class.getName());
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
	    
		List<AlignedPhysicianEntity> alignedPhysician = alignmentFetchService.getAlignmentByUserIdToShow(
				(userAccountService.getUserAccountByUserName(CURRENTUSERNAME)).getUserId());
		log.info("Alignments Fetched : ");
		for(AlignedPhysicianEntity eachEntity : alignedPhysician) log.info(""+eachEntity);
		
		model.addAttribute("listOfAlignedPhysician", alignedPhysician);
		return "showalignments";
	}
	
	
	@RequestMapping(value="/showalignmentsfordm", method=RequestMethod.GET)
	public String showAlignmentsToManager(){
		return "alignmentsfordm";
	}
	
	
	@RequestMapping(value="/getalignmentsfordm", method=RequestMethod.GET)
	@ResponseBody
	public ManagerViewAlignmentsEntity[] getAlignmentsToManager(@RequestParam(value="userId") int userId){
	    
		List<ManagerViewAlignmentsEntity> alignedPhysician = alignmentFetchService.getAlignmentForManagersView(userId);
		log.info("Alignments Fetched : ");
		for(ManagerViewAlignmentsEntity eachEntity : alignedPhysician) log.info(""+eachEntity);
		
		return alignedPhysician.toArray(new ManagerViewAlignmentsEntity[alignedPhysician.size()]);
	}
		
	@RequestMapping(value="/getsuggestivealignmentsfordm", method=RequestMethod.GET)
	@ResponseBody
	public ManagerSuggestiveAlignmentsEntity[] getSuggestiveAlignmentsForManager(@RequestParam(value="userId") int userId){
	    
		List<ManagerSuggestiveAlignmentsEntity> alignedPhysician = alignmentFetchService.getAlignmentForSuggestionsManualAlignment(userId);
		log.info("Alignments Fetched : ");
		for(ManagerSuggestiveAlignmentsEntity eachEntity : alignedPhysician) log.info(""+eachEntity);
		
		return alignedPhysician.toArray(new ManagerSuggestiveAlignmentsEntity[alignedPhysician.size()]);
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
			Time selectedStartTime = (appointmentList.getAppointmentStartTime() == "" ) ? null :SalesFastUtilities.getTimeForStringTime(appointmentList.getAppointmentStartTime(), "HH:mm");
			Time selectedEndTime = (appointmentList.getAppointmentEndTime() == "" ) ? null : SalesFastUtilities.getTimeForStringTime(appointmentList.getAppointmentEndTime(), "HH:mm");
			Date selectedDate = (appointmentList.getAppointmentDate() == "" ) ? null : SalesFastUtilities.getDateForStringTime(appointmentList.getAppointmentDate(), "yyyy-MM-dd"); 
			appointmentService.addAppointment(appointmentList.getPhysicianId(), selectedStartTime, selectedEndTime, selectedDate, appointmentList.getAppointmentStatus(), appointmentList.getProductId(), appointmentList.getAdditionalNotes());
		}
		/* To set reminders in case any follow up appointment
		 * was fixed for any time in next day*/
		reminders.followUpCallReminders();
	}
	
	@RequestMapping(value="/updatefollowupappointment", method=RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public void updateFollowUpAppointments(@RequestBody FollowupAppointmentUpdateEntity[] appointments) throws ParseException{
		for (FollowupAppointmentUpdateEntity appointmentUpdate : appointments){
			log.info("Appointment fixed for physician = "+appointmentUpdate);
			Time selectedStartTime = SalesFastUtilities.getTimeForStringTime(appointmentUpdate.getAppointmentStartTime(), "HH:mm");
			Time selectedEndTime = SalesFastUtilities.getTimeForStringTime(appointmentUpdate.getAppointmentEndTime(), "HH:mm");
			Date selectedDate = SalesFastUtilities.getDateForStringTime(appointmentUpdate.getAppointmentDate(), "yyyy-MM-dd");
			appointmentService.updateFollowUpAppointmentStatus(selectedStartTime, selectedEndTime, selectedDate, 
					appointmentUpdate.getAppointmentStatus(), 
					appointmentUpdate.getAdditionalNotes(), 
					appointmentUpdate.getAppointmentId());
		}
		/* To set reminders in case any follow up appointment
		 * was fixed for any time in next day*/
		reminders.followUpCallReminders();
	}
	
	@RequestMapping(value="/deletealignment", method=RequestMethod.POST)
	@ResponseBody
	public void cancelAppointment( @RequestParam(value="alignmentId") int alignmentId){
		log.info("Delete alignment with id "+alignmentId);
//		alignmentFetchService.deleteAlignmentByDM(alignmentId);
	}
	
}
