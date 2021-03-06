package com.stm.salesfast.backend.dao.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.MeetingUpdateDto;
import com.stm.salesfast.backend.entity.MeetingStatusCountEntity;

public interface MeetingUpdateDao {
	public void insert(MeetingUpdateDto meetingUpdate);
	
	public MeetingUpdateDto getByAppointmentId(int appointmentId);
	
	public List<MeetingUpdateDto> getByPrescribingPhysicians(int physicianId);

	public List<Integer> getLostPhysiciansForUser(int userId);

	public List<Integer> getPrescribingPhysiciansForUser(int userId);

	public List<String> getStatusesByAppointments(int userId, int physicianId);

	public List<MeetingUpdateDto> getForPhysiciansPortal(String status1, String status2, String status3, int physicianId);

	public void update(boolean isExpensive, boolean hasSideEffetcs, int appointmentId);

	public List<MeetingStatusCountEntity> getMeetingStatusCountForUser(int userId);

}
