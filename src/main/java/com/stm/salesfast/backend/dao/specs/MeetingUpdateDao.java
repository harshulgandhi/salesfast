package com.stm.salesfast.backend.dao.specs;

import com.stm.salesfast.backend.dto.MeetingUpdateDto;

public interface MeetingUpdateDao {
	public void insert(MeetingUpdateDto meetingUpdate);
	
	public MeetingUpdateDto getByAppointmentId(int appointmentId);
}
