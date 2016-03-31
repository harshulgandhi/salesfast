package com.stm.salesfast.backend.services.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.MeetingExperienceDto;
import com.stm.salesfast.backend.entity.MeetingExperienceEntity;

public interface MeetingExperienceService {
	
	public void insert(MeetingExperienceEntity meetingExperience);
	
	public List<MeetingExperienceDto> fetchMeetingExperienceByAppointmentId(int appointmentId);
	
	public List<MeetingExperienceDto> fetchMeetingExperienceForSalesRep();
	
	public List<MeetingExperienceDto> fetchMeetingExperienceForPhysician();

	public List<MeetingExperienceDto> fetchAll();

	int countOfRecords(boolean forPhysicianEntries, boolean forSalesRepEntries);

	int getCountOfLikedProduct(int isPhy, int isSR);

	int getCountPriceAffordability(int isPhy, int isSR);

	int getCountForLessSideEffects(int isPhy, int isSR);

	int getCountLikedPresentation(int isPhy, int isSR);

	int getCountForRepsConfidence(int isPhy, int isSR);

	int getCountOrgReputation(int isPhy, int isSR);

	int getCountAll_Lost();

	int getCountOfLikedProduct_Lost();

	int getCountPriceAffordability_Lost();

	int getCountForLessSideEffects_Lost();

	int getCountLikedPresentation_Lost();

	int getCountForRepsConfidence_Lost();

	int getCountOrgReputation_Lost();

	List<String> physicianResponse(int appointmentId);

	List<String> salesRepResponse(int appointmentId);
}
