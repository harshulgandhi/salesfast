package com.stm.salesfast.backend.services.impl;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dto.AppointmentDto;
import com.stm.salesfast.backend.dto.UserDto;
import com.stm.salesfast.backend.services.specs.AppointmentService;
import com.stm.salesfast.backend.services.specs.NotificationService;
import com.stm.salesfast.backend.services.specs.PhysicianFetchService;
import com.stm.salesfast.backend.services.specs.ProductFetchService;
import com.stm.salesfast.backend.services.specs.ReminderService;
import com.stm.salesfast.backend.services.specs.UserDetailService;
import com.stm.salesfast.backend.utils.SalesFastEmail;
import com.stm.salesfast.backend.utils.SalesFastEmailSendGridImpl;
import com.stm.salesfast.backend.utils.SalesFastUtilities;

@Service
public class ReminderServiceImpl implements ReminderService{
	private Logger log = LoggerFactory.getLogger(ReminderServiceImpl.class.getName());
	private String CURRENTUSERNAME="";
	
	@Autowired
	AppointmentService appointmentService;
	
	@Autowired
	ProductFetchService productService;
	
	@Autowired
	PhysicianFetchService physService;
	
	@Autowired
	NotificationService notification;
	
	@Autowired
	UserDetailService userDetails;
	
	@Override
	public void followUpCallReminders() throws ParseException {
		// TODO Auto-generated method stub
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CURRENTUSERNAME = user.getUsername(); //get logged in user name
		List<AppointmentDto> appointments = appointmentService.getFollowUpAppointments(userDetails.getUserDetails(CURRENTUSERNAME).getUserId());
		log.info("Number of appointments to follow up : "+appointments.size());
		for(AppointmentDto eachAppointment : appointments){
			log.info("number of days : "+SalesFastUtilities.numberOfDays(eachAppointment.getDate()));
			if(SalesFastUtilities.numberOfDaysToDate(eachAppointment.getDate()) > 0.0 && SalesFastUtilities.numberOfDaysToDate(eachAppointment.getDate()) <= 1.0){
				addReminderToNotifications(eachAppointment);
			}
		}
	}

	@Override
	public void addReminderToNotifications(AppointmentDto appointment) {
		// TODO Auto-generated method stub
		String productName = productService.getProductById(appointment.getProductId()).getProductName();
		String physicianName = physService.getPhysicianName(appointment.getPhysicianId());
		int userId = appointment.getUserId();
		UserDto user = userDetails.getUserDetails(userId);
		notification.insertNotificationFollowupReminder(userId, physicianName, productName, "FOLLOW UP");
		String emailBody = "Reminder to follow up with Dr. "+physicianName+" regardind medicine "+productName+"."
				+ " Visit http://127.0.0.1/showappointments ";
		String subject = "Reminder for a follow up";
		String toEmail = user.getEmail();
		sendReminderEmails(subject, emailBody, toEmail);
		
	}

	@Override
	public void sendReminderEmails(String subject, String body, String toEmailId) {
		SalesFastEmail email = new SalesFastEmailSendGridImpl();
		email.setFromEmail("no-reply@biopharma.com");
		email.setFromName("BioPharma SalesForce");
		email.addSubject(subject);
		
		email.addTextBody(body);
		log.info("Sending reminder email with content as :\n"+body);
		email.sendMail();
	}

}
