package com.stm.salesfast.backend.services.specs;

import com.stm.salesfast.backend.dto.MeetingExperienceDto;
import com.stm.salesfast.backend.entity.MeetingExperienceEntity;

public interface MeetingExperienceService {
	
	public void insert(MeetingExperienceEntity meetingExperience);
	
	public MeetingExperienceDto fetchMeetingExperienceByAppointmentId(int appointmentId);
	
	public MeetingExperienceDto fetchMeetingExperienceForSalesRep();
	public MeetingExperienceDto fetchMeetingExperienceForPhysician();
}
