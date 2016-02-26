package com.stm.salesfast.backend.dao.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.AppointmentDto;

public interface AppointmentDao {
	public AppointmentDto getAppointmentById(int appointmentId);
	public List<AppointmentDto> getAppointmentByUserId(int userId);
	public AppointmentDto getAppointmentByPhysicianId(int physicianId);
	
	public void insertAppointment(AppointmentDto appointment);
	
	public int getIdByPhysIdUserId(int userId, int physicianId);

	public void setMeetinUpdateFlag(int appointmentId, int meetingUpdateFlag);
	
}
