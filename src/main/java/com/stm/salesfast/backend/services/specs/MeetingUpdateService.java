package com.stm.salesfast.backend.services.specs;

import java.text.ParseException;
import java.util.List;

import com.stm.salesfast.backend.dto.MeetingUpdateDto;
import com.stm.salesfast.backend.dto.ProductDto;
import com.stm.salesfast.backend.entity.MeetingStatusCountEntity;
import com.stm.salesfast.backend.entity.MeetingUpdateEntity;

public interface MeetingUpdateService {

	public void insertMeetingUpdate(MeetingUpdateEntity meetingUpdateEntity) throws ParseException;
	
	public MeetingUpdateDto getMeetingUpdateByAppointmentId(int appointmentId);
	
	public List<Integer> getPrescribingProduct(int physicianId);

	public void setupEDetailing(MeetingUpdateEntity meetingUpdateEntity);

	public void sendMail(String subject, String body, String toEmailId);

	public List<Integer> getLostPhysiciansForAUser(int userId);

	public List<Integer> getPrescribingPhysiciansForAUser(int userId);

	public List<String> getStatusForAllAppointments(int userId, int physicianId);

	public List<MeetingUpdateDto> getForPhysiciansPortal(String status1, String status2, String status3, int physicianId);

	public void updateIsExpensiveAndHasSideEffects(boolean isExpensive, boolean hasSideEffects, int appointmentId);

	public List<MeetingStatusCountEntity> getMeetingUpdateStatusCount(int userId);
}
