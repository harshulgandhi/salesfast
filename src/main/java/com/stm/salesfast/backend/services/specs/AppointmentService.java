package com.stm.salesfast.backend.services.specs;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.util.List;

import com.stm.salesfast.backend.dto.AppointmentDto;
import com.stm.salesfast.backend.entity.AppointmentEntity;

public interface AppointmentService {

	
	public List<AppointmentEntity> getTodaysAppointmentToShow(int userId) throws ParseException;
	
	public List<AppointmentEntity> getFutureAppointmentToShow(int userId) throws ParseException;
	
	public int getAppointmentId (String username, int physicianId);
	int getAppointmentId(String username, int physicianId, int productId);
	
	public AppointmentDto getById(int appointmentId);
	
	public void setHasMeetingUpdateFlag(int appointmentId, int flag);
	
	public void setHasMeetingExperienceFlag(int appointmentId, int flag);


	public void cancelAppointment(int appointmentId, String reason);

	public void addAppointment(int physId, Time time, Date date,
			String confirmationStatus, int productId, String additionalNotes)
			throws ParseException;


	public List<AppointmentDto> getFollowUpAppointments();

	public void sendMail(String subject, String body, String toEmailId);

	
}
