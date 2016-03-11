package com.stm.salesfast.backend.services.specs;

import java.util.List;

import com.stm.salesfast.backend.entity.MeetingExperienceDataEntity;

public interface AnalysisService {
	
	public List<MeetingExperienceDataEntity> analyseOverall();

	public List<MeetingExperienceDataEntity> analysePhysicianResponse();

	public List<MeetingExperienceDataEntity> analyseSalesRepResponse();

	public List<MeetingExperienceDataEntity> analyseLostStatusRecords();
	
}
