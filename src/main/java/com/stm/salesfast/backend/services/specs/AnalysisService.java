package com.stm.salesfast.backend.services.specs;

import java.util.List;

import com.stm.salesfast.backend.entity.MeetingExpAnalysisFilterEntity;
import com.stm.salesfast.backend.entity.MeetingExperienceDataEntity;
import com.stm.salesfast.backend.entity.MeetingExperienceDetailedDataEntity;

public interface AnalysisService {
	
	public List<MeetingExperienceDataEntity> analyseOverall();

	public List<MeetingExperienceDataEntity> analysePhysicianResponse();

	public List<MeetingExperienceDataEntity> analyseSalesRepResponse();

	public List<MeetingExperienceDataEntity> analyseLostStatusRecords();

	void applyCount(int[][] countMatrix,
			MeetingExperienceDetailedDataEntity entityToBeCounted);

	void analyseMeetingExperience(MeetingExpAnalysisFilterEntity filter);

	void calculateAnalysisPercentage(int[][] countMatrix);
	
}
