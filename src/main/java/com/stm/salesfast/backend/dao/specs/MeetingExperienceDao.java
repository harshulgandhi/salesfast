package com.stm.salesfast.backend.dao.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.MeetingExperienceDto;
import com.stm.salesfast.backend.entity.MeetingExperienceDetailedDataEntity;

public interface MeetingExperienceDao {
	
	public void insert(MeetingExperienceDto meetingExperience);
	
	public List<MeetingExperienceDto> getByAppointmentId(int appointmentId);
	
	public List<MeetingExperienceDto> getSalesRepEntries();
	
	public List<MeetingExperienceDto> getPhysicianEntries();

	public List<MeetingExperienceDto> getAll();

	int countOrgReputation(int isPhy, int isSR);

	int countRepsConfidence(int isPhy, int isSR);

	int countLikedPresentation(int isPhy, int isSR);

	int countPriceAffordability(int isPhy, int isSR);

	int countLikedProduct(int isPhy, int isSR);

	int countAllSR();

	int countAllPhy();

	int countAll();

	int countLessSideEffects(int isPhy, int isSR);

	int countAll_Lost();

	int countLikedProduct_Lost();

	int countPriceAffordabilityLost();

	int countLessSideEffectsLost();

	int countLikedPresentationLost();

	int countRepsConfidenceLost();

	int countOrgReputationLost();

	public List<MeetingExperienceDetailedDataEntity> getDetailedData();
	
}
