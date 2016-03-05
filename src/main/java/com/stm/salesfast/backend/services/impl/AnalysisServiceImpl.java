package com.stm.salesfast.backend.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dto.MeetingExperienceDto;
import com.stm.salesfast.backend.entity.MeetingExperienceDataEntity;
import com.stm.salesfast.backend.services.specs.AnalysisService;
import com.stm.salesfast.backend.services.specs.MeetingExperienceService;

@Service
public class AnalysisServiceImpl implements AnalysisService{
	
	private Logger log = LoggerFactory.getLogger(AnalysisServiceImpl.class.getName());
	
	@Autowired
	MeetingExperienceService meetingExp;
	
	@Override
	public List<MeetingExperienceDataEntity> analyseOverall() {
		List<MeetingExperienceDataEntity> overallData = new ArrayList<>();
		int countAllEntries = meetingExp.countOfRecords(true, true);
		double percentLikedProduct = (meetingExp.getCountOfLikedProduct(1, 0) + 
				meetingExp.getCountOfLikedProduct(0, 1) + 
				meetingExp.getCountOfLikedProduct(1, 1))*1.0/countAllEntries; 
		overallData.add(new MeetingExperienceDataEntity("Genuinely liked the product", percentLikedProduct));
		
		double percentPriceAffordability = (meetingExp.getCountPriceAffordability(1, 0) + 
				meetingExp.getCountPriceAffordability(0, 1) + 
				meetingExp.getCountPriceAffordability(1, 1))*1.0/countAllEntries; 
		overallData.add(new MeetingExperienceDataEntity("Price affordability", percentPriceAffordability));
		
		double percentLessSideEffects = (meetingExp.getCountForLessSideEffects(1, 0) + 
				meetingExp.getCountForLessSideEffects(0, 1) + 
				meetingExp.getCountForLessSideEffects(1, 1))*1.0/countAllEntries; 
		overallData.add(new MeetingExperienceDataEntity("Liked that product is affordable", percentLessSideEffects));
		
		double percentLikedPresentation = (meetingExp.getCountLikedPresentation(1, 0) + 
				meetingExp.getCountLikedPresentation(0, 1) + 
				meetingExp.getCountLikedPresentation(1, 1))*1.0/countAllEntries; 
		overallData.add(new MeetingExperienceDataEntity("Influenced by presentation", percentLikedPresentation));
		
		double percentLikedRepsConfidence = (meetingExp.getCountForRepsConfidence(1, 0) + 
				meetingExp.getCountForRepsConfidence(0, 1) + 
				meetingExp.getCountForRepsConfidence(1, 1))*1.0/countAllEntries; 
		overallData.add(new MeetingExperienceDataEntity("Impressed by SalesRep's confidence", percentLikedRepsConfidence));
		
		double percentOrganisationReputation = (meetingExp.getCountOrgReputation(1, 0) + 
				meetingExp.getCountOrgReputation(0, 1) + 
				meetingExp.getCountOrgReputation(1, 1))*1.0/countAllEntries; 
		overallData.add(new MeetingExperienceDataEntity("Impressed with organisation's reputations", percentOrganisationReputation));
		
		return overallData;
	}

}
