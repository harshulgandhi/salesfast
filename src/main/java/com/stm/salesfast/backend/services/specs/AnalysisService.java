package com.stm.salesfast.backend.services.specs;

import java.util.List;

import com.stm.salesfast.backend.entity.MeetingExpAnalysisFilterEntity;
import com.stm.salesfast.backend.entity.MeetingExperienceAnalyzedDataEntity;
import com.stm.salesfast.backend.entity.MeetingExperienceDataEntity;
import com.stm.salesfast.backend.entity.MeetingExperienceDetailedDataEntity;

public interface AnalysisService {
	
	public List<MeetingExperienceDataEntity> analyseOverall();

	public List<MeetingExperienceDataEntity> analysePhysicianResponse();

	public List<MeetingExperienceDataEntity> analyseSalesRepResponse();

	public List<MeetingExperienceDataEntity> analyseLostStatusRecords();

	List<MeetingExperienceAnalyzedDataEntity> analyseMeetingExperience(MeetingExpAnalysisFilterEntity filter);

	void applyCount(int[][] countMatrix, MeetingExperienceDetailedDataEntity entityToBeCounted, int countAllSREntries,
			int callAllPhysEntries, int countAll);

	List<MeetingExperienceAnalyzedDataEntity> calculateAnalysisPercentage(int[][] countMatrix, int countAllSREntries, int countAllPhysEntries, int countAll);
	
}
