package com.stm.salesfast.backend.dao.specs;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.stm.salesfast.backend.dto.AppointmentDto;
import com.stm.salesfast.backend.entity.AppointmentCountPerDayEntity;
import com.stm.salesfast.backend.entity.MeetingStatusCountEntity;

public interface AppointmentDao {
	public AppointmentDto getAppointmentById(int appointmentId);
	
	public List<AppointmentDto> getAppointmentByUserId(int userId);
	
	public AppointmentDto getAppointmentByPhysicianId(int physicianId);
	
	public void insertAppointment(AppointmentDto appointment);
	
	public int getIdByPhysIdUserId(int userId, int physicianId);

	public void setMeetinUpdateFlag(int appointmentId, int meetingUpdateFlag);
	
	void updateStatus(int appointmentId, String status, String reason);
	
	int getIdByPhysIdUserIdProductId(int physicianId, int userId, int productId);
	
	public List<AppointmentDto> getAppointmentByStatus(String confirmationStatus, int userId);
	
	List<AppointmentDto> getAppointmentForPhysician(String confirmationStatus1,
			String confirmationStatus2, int physicianId);
	
	public void setMeetinExperienceFlagFromSR(int appointmentId, int meetingExpFromSR);

	public void setMeetinExperienceFlagFromPH(int appointmentId, int meetingExpFromPH);

	public List<Integer> getNotInterestedPhysicians(int userId);

	public void updateAppointment(Time startTime, Time endTime, Date date,
			String status, String additionalNotes, int appointmentId);

	public void updatePitchFlagTrue(int appointmentId);

	public List<AppointmentDto> getAppointmentByMedicalField(String medicalFieldId);

	public List<AppointmentDto> getAppointmentByProduct(int productId);

	public List<AppointmentDto> getAppointmentBySalesRep(int userId);

	public List<AppointmentDto> getAppointmentByPhysician(int physicianId);

	public List<AppointmentDto> getAllAppointmentHavingPitch();

	public List<AppointmentDto> getPastAppointmentByUserId(int userId);

	public String getNotInterestedStatus(int physicianId, int userId);

	public List<AppointmentDto> getAppointmentByUserIdForADate(int userId, Date date);

	public List<AppointmentCountPerDayEntity> getCountPerDayPerformanceBased(int userId, String status);

	List<MeetingStatusCountEntity> getAppointmentStatusCount(int userId);
	
}
