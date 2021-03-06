package com.stm.salesfast.backend.services.specs;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.util.List;

import com.stm.salesfast.backend.dto.AppointmentDto;
import com.stm.salesfast.backend.entity.AlignedPhysicianFollowUpEntity;
import com.stm.salesfast.backend.entity.AppointmentCountPerDayEntity;
import com.stm.salesfast.backend.entity.AppointmentEntity;
import com.stm.salesfast.backend.entity.MeetingStatusCountEntity;
import com.stm.salesfast.backend.entity.PastAppointmentEntity;

public interface AppointmentService {

	
	public List<AppointmentEntity> getTodaysAppointmentToShow(int userId) throws ParseException;
	
	public List<AppointmentEntity> getFutureAppointmentToShow(int userId) throws ParseException;
	
	public int getAppointmentId (String username, int physicianId);
	int getAppointmentId(String username, int physicianId, int productId);
	
	public AppointmentDto getById(int appointmentId);
	
	public void cancelAppointment(int appointmentId, String reason);

	public List<AppointmentDto> getFollowUpAppointments(int userId);

	public void sendMail(String subject, String body, String toEmailId);

	List<AlignedPhysicianFollowUpEntity> followUpAppointmentsToShow() throws ParseException;

	List<AppointmentDto> getAppointmentsForPhysician(
			String confirmationStatus1, String confirmationStatus2, int physicianId);

	public void setHasMeetingUpdateFlag(int appointmentId, int flag);

	public void setHasMeetingExperienceFlagFromSR(int appointmentId,
			int isSalesRepEntry);

	public void setHasMeetingExperienceFlagFromPH(int appointmentId, int isPhyEntry);

	public List<Integer> getPhysiciansNotInterestedBeforeDetailing(int userId);

	public List<AppointmentEntity> getAllAppointmentToShow(int userId);

	public void cancelAppointmentBySR(int appointmentId, String reason);

	public void updateFollowUpAppointmentStatus(Time startTime, Time endTime,
			Date date, String status, String additionalNotes, int appointmentId);

	public void updateFutureAppointmentStatus(Time startTime, Time endTime, Date date,
			String status, String additionalNotes, int appointmentId);

	public void addAppointment(int physId, Time startTime, Time endTime, Date date,
			String confirmationStatus, int productId, String additionalNotes)
			throws ParseException;

	public void setHasPitchFlag(int appointmentId);

	public List<AppointmentDto> getAppointmentsByMedicalField(String medicalFieldId);

	List<AppointmentDto> getAppointmentsByProduct(int productId);

	List<AppointmentDto> getAppointmentsBySalesRep(int userId);

	List<AppointmentDto> getAppointmentsByPhysician(int physicianId);

	List<AppointmentDto> getAllAppointmentsHavingPitch();

	List<PastAppointmentEntity> getPastAppointmentToShow(int userId)
			throws ParseException;

	public String getNotInterestedAppointmentStatus(int physicianId, int userId);

	public List<AppointmentEntity> getAllAppointmentForADate(int userId, Date date);

	public AppointmentDto getAppointmentPhysIdUserIdProductId(int physicianId, int userId, int productId);

	List<AppointmentCountPerDayEntity> getPerDayAppointmentCountByPerformance(int userId, String status);

	List<MeetingStatusCountEntity> getAppointmentStatusCount(int userId);
	
}
