package com.stm.salesfast.backend.dao.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.MeetingExperienceDto;

public interface MeetingExperienceDao {
	
	public void insert(MeetingExperienceDto meetingExperience);
	
	public List<MeetingExperienceDto> getByAppointmentId(int appointmentId);
	
	public List<MeetingExperienceDto> getSalesRepEntries();
	
	public List<MeetingExperienceDto> getPhysicianEntries();
	
}
