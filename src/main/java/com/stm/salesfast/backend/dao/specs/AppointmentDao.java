package com.stm.salesfast.backend.dao.specs;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.stm.salesfast.backend.dto.AppointmentDto;

public interface AppointmentDao {
	public AppointmentDto getAppointmentById(int appointmentId);
	public List<AppointmentDto> getAppointmentByUserId(int userId);
	public AppointmentDto getAppointmentByPhysicianId(int physicianId);
	
	public void insertAppointment(AppointmentDto appointment);
	
	public int getIdByPhysIdUserId(int userId, int physicianId);

	public void setMeetinUpdateFlag(int appointmentId, int meetingUpdateFlag);
	void setMeetinExperienceFlag(int appointmentId, int meetingUpdateFlag);
	void updateStatus(int appointmentId, String status, String reason);
	int getIdByPhysIdUserIdProductId(int physicianId, int userId, int productId);
	public List<AppointmentDto> getAppointmentByStatus(String confirmationStatus, int userId);
	public void updateFollowUps(Time time, Date date, String status,
			String additionalNotes, int appointmentId);
	
}
