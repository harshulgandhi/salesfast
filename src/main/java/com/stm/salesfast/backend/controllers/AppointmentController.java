package com.stm.salesfast.backend.controllers;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.stm.salesfast.backend.dto.PhysicianStgDto;
import com.stm.salesfast.backend.entity.AlignedPhysicianEntity;
import com.stm.salesfast.backend.entity.AlignedPhysicianFollowUpEntity;
import com.stm.salesfast.backend.entity.AppointmentEntity;
import com.stm.salesfast.backend.entity.FollowupAppointmentUpdateEntity;
import com.stm.salesfast.backend.entity.FutureAppointmentUpdateEntity;
import com.stm.salesfast.backend.entity.MeetingExperienceEntity;
import com.stm.salesfast.backend.entity.PastAppointmentEntity;
import com.stm.salesfast.backend.entity.PitchViewEntity;
import com.stm.salesfast.backend.services.specs.AlignmentFetchService;
import com.stm.salesfast.backend.services.specs.AppointmentService;
import com.stm.salesfast.backend.services.specs.MeetingExperienceService;
import com.stm.salesfast.backend.services.specs.MeetingUpdateService;
import com.stm.salesfast.backend.services.specs.NotificationService;
import com.stm.salesfast.backend.services.specs.PitchesService;
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
	
	@Autowired
	PitchesService pitchService;
	
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
	
	@RequestMapping(value="/showfutureappointments", method=RequestMethod.GET)
	public String showFutureAppointments(Model model) throws ParseException{
		List<AppointmentEntity> futureAppointmentsList = appointmentFetchService.getFutureAppointmentToShow(SessionConstants.USER_ID);
		
		model.addAttribute("listOfFutureAppointments", futureAppointmentsList);
		return "showfutureappointment";
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
	public void cancelAppointment( @RequestParam(value="appointmentId") int appointmentId, @RequestParam(value="cancellationReason") String cancellationReason){
		log.info("Cancellation request received for appointment "+appointmentId+" due to "+cancellationReason);
		appointmentFetchService.cancelAppointment(appointmentId, cancellationReason);
	}
	
	@RequestMapping(value="/getallappointments",  headers="Accept=*/*", method=RequestMethod.GET, produces="appliation/json")
	@ResponseBody
	public AppointmentEntity[] getAllAppointments() throws ParseException{
		log.info("Fetching all appointments!");
		List<AppointmentEntity> futureAppointmentsList = appointmentFetchService.getAllAppointmentToShow(SessionConstants.USER_ID);
		return futureAppointmentsList.toArray(new AppointmentEntity[futureAppointmentsList.size()]);
	}
	
	@RequestMapping(value="/getallappointmentsfordate",  headers="Accept=*/*", method=RequestMethod.GET, produces="appliation/json")
	@ResponseBody
	public AppointmentEntity[] getAllAppointmentsForDate(@RequestParam(value="date") String date) throws ParseException{
		Date selectedDate = SalesFastUtilities.getDateForStringTime(date, "yyyy-MM-dd");
		List<AppointmentEntity> futureAppointmentsList = appointmentFetchService.getAllAppointmentForADate(SessionConstants.USER_ID, selectedDate);
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
			Time selectedStartTime = SalesFastUtilities.getTimeForStringTime(appointmentUpdate.getAppointmentStartTime(), "HH:mm");
			Time selectedEndTime = SalesFastUtilities.getTimeForStringTime(appointmentUpdate.getAppointmentEndTime(), "HH:mm");
			Date selectedDate = SalesFastUtilities.getDateForStringTime(appointmentUpdate.getAppointmentDate(), "yyyy-MM-dd");
			appointmentFetchService.updateFutureAppointmentStatus(selectedStartTime, selectedEndTime, selectedDate, 
					appointmentUpdate.getAppointmentStatus(), 
					appointmentUpdate.getAdditionalNotes(), 
					appointmentUpdate.getAppointmentId());
		}
		
		/* To set reminders in case any follow up appointment
		 * was fixed for any time in next day*/
		reminders.followUpCallReminders();
	}
	
	@RequestMapping(value = "/uploadmeetingpitch", method = RequestMethod.POST) 
    public String uploadMeetingPitch(@RequestParam("pitchdocument") MultipartFile pitchdocument, 
    		@RequestParam("uploadModalAppointmentId") int appointmentId) throws IllegalStateException, IOException, ParseException { 
		log.info("Meeting pitch received! ");
		log.info("File  : "+pitchdocument.getOriginalFilename()+"["+pitchdocument.getContentType()+"]"+"\nFor Meeting : "+appointmentId);
		pitchService.insertNewPitch(appointmentId, pitchdocument);
		return "redirect:/showappointments";
    }
	
	@RequestMapping(value = "/updatemeetingpitch", method = RequestMethod.POST) 
    public String updateExistingMeetingPitch(@RequestParam("updatedpitchdocument") MultipartFile pitchdocument, 
    		@RequestParam("viewModalAppointmentId") int appointmentId) throws IllegalStateException, IOException, ParseException { 
		log.info("Meeting pitch UPDATE received! ");
		log.info("File  : "+pitchdocument.getOriginalFilename()+"["+pitchdocument.getContentType()+"]"+"\nFor Meeting : "+appointmentId);
		pitchService.updatePitchFile(appointmentId, pitchdocument);
		return "redirect:/showappointments";
    }
	
	
	@RequestMapping(value="/getpitchforappointment", method=RequestMethod.GET,produces = "application/json")
	@ResponseBody
	public PitchViewEntity getMeetingPitch(@RequestParam(value="appointmentId") int appointmentId){
		PitchViewEntity pitchEntity = pitchService.getPitchForAppointment(appointmentId);
		log.info("Pitch entity fetched : "+pitchEntity);
		return pitchEntity;
	}
	
	@RequestMapping(value="/pastappointments", method=RequestMethod.GET)
	public String showPastAppointments(){
		return "pastappointment";
	}
	
	@RequestMapping(value="/getpastappointments", method=RequestMethod.GET)
	@ResponseBody
	public PastAppointmentEntity[] getPastAppointments() throws ParseException{
		List<PastAppointmentEntity> pastAppointments = appointmentFetchService.getPastAppointmentToShow(SessionConstants.USER_ID);
		for(PastAppointmentEntity eachPastApp : pastAppointments) log.info("Past Appointments : "+eachPastApp);
		return pastAppointments.toArray(new PastAppointmentEntity[pastAppointments.size()]);
	}
}





