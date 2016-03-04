package com.stm.salesfast.backend.services.specs;

import java.text.ParseException;

import com.stm.salesfast.backend.dto.AppointmentDto;

public interface ReminderService {
	
	public void followUpCallReminders() throws ParseException;
	
	public void addReminderToNotifications(AppointmentDto appointment);
	
	public void sendReminderEmails(String subject, String body, String toEmailId);
}
