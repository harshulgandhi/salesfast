package com.stm.salesfast.backend.services.specs;

import java.sql.Time;
import java.text.ParseException;
import java.util.List;

import com.stm.salesfast.backend.dto.AppointmentDto;
import com.stm.salesfast.backend.entity.AppointmentEntity;

public interface AppointmentService {

	public void addAppointment(int physId, Time time, String confirmationStatus) throws ParseException;
	
	public List<AppointmentEntity> getAppointmentToShow(int userId);
	
	public int getAppointmentId (String username, int physicianId);
	
	public AppointmentDto getById(int appointmentId);
	
	public void setHasMeetingUpdateFlag(int appointmentId, int flag);
	
	public void setHasMeetingExperienceFlag(int appointmentId, int flag);
}