package com.stm.salesfast.backend.controllers;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import com.stm.salesfast.backend.dto.PhysicianStgDto;
import com.stm.salesfast.backend.entity.AlignedPhysicianEntity;
import com.stm.salesfast.backend.entity.AlignedPhysicianFollowUpEntity;
import com.stm.salesfast.backend.entity.AppointmentEntity;
import com.stm.salesfast.backend.entity.FollowupAppointmentUpdateEntity;
import com.stm.salesfast.backend.entity.FutureAppointmentUpdateEntity;
import com.stm.salesfast.backend.entity.MeetingExperienceEntity;
import com.stm.salesfast.backend.services.specs.AlignmentFetchService;
import com.stm.salesfast.backend.services.specs.AppointmentService;
import com.stm.salesfast.backend.services.specs.MeetingExperienceService;
import com.stm.salesfast.backend.services.specs.MeetingUpdateService;
import com.stm.salesfast.backend.services.specs.NotificationService;
import com.stm.salesfast.backend.services.specs.ReminderService;
import com.stm.salesfast.backend.services.specs.UserAccountService;
import com.stm.salesfast.backend.utils.SalesFastUtilities;
import com.stm.salesfast.backend.entity.MeetingUpdateEntity;
import com.stm.salesfast.constant.ConstantValues;
import com.stm.salesfast.constant.SessionConstants;

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
	
	@Autowired
	NotificationService notificationService;
	
	@Autowired
	ReminderService reminders;
	
	@RequestMapping(value="/showappointments", method=RequestMethod.GET)
	public String showAppointments(Model model) throws ParseException{
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CURRENTUSERNAME = user.getUsername(); //get logged in user name
		
		List<AppointmentEntity> todaysAppointmentsList = appointmentFetchService.getTodaysAppointmentToShow(SessionConstants.USER_ID);
		List<AppointmentEntity> futureAppointmentsList = appointmentFetchService.getFutureAppointmentToShow(SessionConstants.USER_ID);
		List<AlignedPhysicianEntity> alignedPhysicianInVicinity = alignmentFetchService.getAlignmentByUserIdInVicinityOfAppointments(
				(userAccountService.getUserAccountByUserName(CURRENTUSERNAME)).getUserId());
		List<AlignedPhysicianFollowUpEntity> followUpAppointments = appointmentFetchService.followUpAppointmentsToShow();
		
		
		model.addAttribute("listOfTodaysAppointments", todaysAppointmentsList);
		model.addAttribute("listOfFutureAppointments", futureAppointmentsList);
		model.addAttribute("listOfPhysInVicinity", alignedPhysicianInVicinity);
		model.addAttribute("followUpAppointments", followUpAppointments);
		return "showappointment";
	}
	
	
	@RequestMapping(value="/addmeetingupdate", method=RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public void meetingUpdate(@RequestBody MeetingUpdateEntity meetingUpdate) throws ParseException{
		log.info("Meeting update received  : "+meetingUpdate);
		meetingUpdateService.insertMeetingUpdate(meetingUpdate);
	}
	
	@RequestMapping(value="/addmeetingexperience", method=RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public void meetingExperience(@RequestBody MeetingExperienceEntity meetingExperience) {
		log.info("Meeting experience details received  : "+meetingExperience);
		meetingExperienceService.insert(meetingExperience);
	}
	
	@RequestMapping(value="/cancelappointment", method=RequestMethod.POST)
	@ResponseBody
	public void cancelAppointment( @RequestParam(value="appointmentId") int appointmentId, @RequestParam(value="cancellationReason") String cancellationReason){
		log.info("Cancellation request received for appointment "+appointmentId+" due to "+cancellationReason);
		appointmentFetchService.cancelAppointment(appointmentId, cancellationReason);
		
	}
	
	@RequestMapping(value="/getallappointments",  headers="Accept=*/*", method=RequestMethod.GET, produces="appliation/json")
	@ResponseBody
	public AppointmentEntity[] getAllQuestionsWithAnswers() throws ParseException{
		log.info("Fetching all appointments!");
		List<AppointmentEntity> futureAppointmentsList = appointmentFetchService.getAllAppointmentToShow(SessionConstants.USER_ID);
		return futureAppointmentsList.toArray(new AppointmentEntity[futureAppointmentsList.size()]);
	}
	
	@RequestMapping(value="/cancelappointmentbysr", method=RequestMethod.POST)
	@ResponseBody
	public void cancelAppointmentBySR( @RequestParam(value="appointmentId") int appointmentId, @RequestParam(value="cancellationReason") String cancellationReason){
		log.info("Cancellation request received from SALESREP for appointment "+appointmentId+" due to "+cancellationReason);
		appointmentFetchService.cancelAppointmentBySR(appointmentId, cancellationReason);
		
	}
	
	@RequestMapping(value="/updatefutureappointment", method=RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public void updateFutureAppointments(@RequestBody FutureAppointmentUpdateEntity[] appointments) throws ParseException{
		for (FutureAppointmentUpdateEntity appointmentUpdate : appointments){
			log.info("Appointment Update by SalesRep = "+appointmentUpdate);
			Time selectedTime = SalesFastUtilities.getTimeForStringTime(appointmentUpdate.getAppointmentTime(), "HH:mm");
			Date selectedDate = SalesFastUtilities.getDateForStringTime(appointmentUpdate.getAppointmentDate(), "yyyy-MM-dd");
			appointmentFetchService.updateFutureAppointmentStatus(selectedTime, selectedDate, 
					appointmentUpdate.getAppointmentStatus(), 
					appointmentUpdate.getAdditionalNotes(), 
					appointmentUpdate.getAppointmentId());
		}
		
		/* To set reminders in case any follow up appointment
		 * was fixed for any time in next day*/
		reminders.followUpCallReminders();
	}
}





