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
public class MeetingExpAnalysisServiceImpl implements AnalysisService{
	
	private Logger log = LoggerFactory.getLogger(MeetingExpAnalysisServiceImpl.class.getName());
	
	@Autowired
	MeetingExperienceService meetingExp;
	
	@Override
	public List<MeetingExperienceDataEntity> analyseOverall() {
		List<MeetingExperienceDataEntity> overallData = new ArrayList<>();
		int countAllEntries = meetingExp.countOfRecords(true, true);
		double percentLikedProduct = (meetingExp.getCountOfLikedProduct(1, 0) + 
				meetingExp.getCountOfLikedProduct(0, 1) + 
				meetingExp.getCountOfLikedProduct(1, 1))*100.0/countAllEntries; 
		overallData.add(new MeetingExperienceDataEntity("Genuinely liked the product", percentLikedProduct));
		
		double percentPriceAffordability = (meetingExp.getCountPriceAffordability(1, 0) + 
				meetingExp.getCountPriceAffordability(0, 1) + 
				meetingExp.getCountPriceAffordability(1, 1))*100.0/countAllEntries; 
		overallData.add(new MeetingExperienceDataEntity("Price affordability", percentPriceAffordability));
		
		double percentLessSideEffects = (meetingExp.getCountForLessSideEffects(1, 0) + 
				meetingExp.getCountForLessSideEffects(0, 1) + 
				meetingExp.getCountForLessSideEffects(1, 1))*100.0/countAllEntries; 
		overallData.add(new MeetingExperienceDataEntity("Liked that product has less side effects", percentLessSideEffects));
		
		double percentLikedPresentation = (meetingExp.getCountLikedPresentation(1, 0) + 
				meetingExp.getCountLikedPresentation(0, 1) + 
				meetingExp.getCountLikedPresentation(1, 1))*100.0/countAllEntries; 
		overallData.add(new MeetingExperienceDataEntity("Influenced by presentation", percentLikedPresentation));
		
		double percentLikedRepsConfidence = (meetingExp.getCountForRepsConfidence(1, 0) + 
				meetingExp.getCountForRepsConfidence(0, 1) + 
				meetingExp.getCountForRepsConfidence(1, 1))*100.0/countAllEntries; 
		overallData.add(new MeetingExperienceDataEntity("Impressed by SalesRep's confidence", percentLikedRepsConfidence));
		
		double percentOrganisationReputation = (meetingExp.getCountOrgReputation(1, 0) + 
				meetingExp.getCountOrgReputation(0, 1) + 
				meetingExp.getCountOrgReputation(1, 1))*100.0/countAllEntries; 
		overallData.add(new MeetingExperienceDataEntity("Impressed with organisation's reputations", percentOrganisationReputation));
		
		return overallData;
	}
	
	@Override
	public List<MeetingExperienceDataEntity> analysePhysicianResponse() {
		List<MeetingExperienceDataEntity> physicianReponseData = new ArrayList<>();
		int countAllEntries = meetingExp.countOfRecords(true, false);
		double percentLikedProduct = (meetingExp.getCountOfLikedProduct(1, 0))*100.0/countAllEntries; 
		physicianReponseData.add(new MeetingExperienceDataEntity("Genuinely liked the product", percentLikedProduct));
		
		double percentPriceAffordability = (meetingExp.getCountPriceAffordability(1, 0))*100.0/countAllEntries; 
		physicianReponseData.add(new MeetingExperienceDataEntity("Price affordability", percentPriceAffordability));
		
		double percentLessSideEffects = (meetingExp.getCountForLessSideEffects(1, 0))*100.0/countAllEntries; 
		physicianReponseData.add(new MeetingExperienceDataEntity("Liked that product has less side effects", percentLessSideEffects));
		
		double percentLikedPresentation = (meetingExp.getCountLikedPresentation(1, 0))*100.0/countAllEntries; 
		physicianReponseData.add(new MeetingExperienceDataEntity("Influenced by presentation", percentLikedPresentation));
		
		double percentLikedRepsConfidence = (meetingExp.getCountForRepsConfidence(1, 0))*100.0/countAllEntries; 
		physicianReponseData.add(new MeetingExperienceDataEntity("Impressed by SalesRep's confidence", percentLikedRepsConfidence));
		
		double percentOrganisationReputation = (meetingExp.getCountOrgReputation(1, 0))*100.0/countAllEntries; 
		physicianReponseData.add(new MeetingExperienceDataEntity("Impressed with organisation's reputations", percentOrganisationReputation));
		
		return physicianReponseData;
	}
	
	@Override
	public List<MeetingExperienceDataEntity> analyseSalesRepResponse() {
		List<MeetingExperienceDataEntity> physicianReponseData = new ArrayList<>();
		int countAllEntries = meetingExp.countOfRecords(false, true);
		double percentLikedProduct = (meetingExp.getCountOfLikedProduct(0, 1))*100.0/countAllEntries; 
		physicianReponseData.add(new MeetingExperienceDataEntity("Genuinely liked the product", percentLikedProduct));
		
		double percentPriceAffordability = (meetingExp.getCountPriceAffordability(0, 1))*100.0/countAllEntries; 
		physicianReponseData.add(new MeetingExperienceDataEntity("Price affordability", percentPriceAffordability));
		
		double percentLessSideEffects = (meetingExp.getCountForLessSideEffects(0, 1))*100.0/countAllEntries; 
		physicianReponseData.add(new MeetingExperienceDataEntity("Liked that product has less side effects", percentLessSideEffects));
		
		double percentLikedPresentation = (meetingExp.getCountLikedPresentation(0, 1))*100.0/countAllEntries; 
		physicianReponseData.add(new MeetingExperienceDataEntity("Influenced by presentation", percentLikedPresentation));
		
		double percentLikedRepsConfidence = (meetingExp.getCountForRepsConfidence(0, 1))*100.0/countAllEntries; 
		physicianReponseData.add(new MeetingExperienceDataEntity("Impressed by SalesRep's confidence", percentLikedRepsConfidence));
		
		double percentOrganisationReputation = (meetingExp.getCountOrgReputation(0, 1))*100.0/countAllEntries; 
		physicianReponseData.add(new MeetingExperienceDataEntity("Impressed with organisation's reputations", percentOrganisationReputation));
		
		return physicianReponseData;
	}
	
	@Override
	public List<MeetingExperienceDataEntity> analyseLostStatusRecords() {
		List<MeetingExperienceDataEntity> physicianReponseData = new ArrayList<>();
		int countAllEntries = meetingExp.getCountAll_Lost();
		double percentLikedProduct = (meetingExp.getCountOfLikedProduct_Lost())*100.0/countAllEntries; 
		physicianReponseData.add(new MeetingExperienceDataEntity("Did not like the product", 100.0-percentLikedProduct));
		
		double percentPriceAffordability = (meetingExp.getCountPriceAffordability_Lost())*100.0/countAllEntries; 
		physicianReponseData.add(new MeetingExperienceDataEntity("Found the product expensive", 100.0-percentPriceAffordability));
		
		double percentLessSideEffects = (meetingExp.getCountForLessSideEffects_Lost())*100.0/countAllEntries; 
		physicianReponseData.add(new MeetingExperienceDataEntity("Felt that product has many side effects", 100.0 - percentLessSideEffects));
		
		double percentLikedPresentation = (meetingExp.getCountLikedPresentation_Lost())*100.0/countAllEntries; 
		physicianReponseData.add(new MeetingExperienceDataEntity("Was not influenced by presentation", 100.0 - percentLikedPresentation));
		
		double percentLikedRepsConfidence = (meetingExp.getCountForRepsConfidence_Lost())*100.0/countAllEntries; 
		physicianReponseData.add(new MeetingExperienceDataEntity("Was not impressed by SalesRep's confidence", 100.0 - percentLikedRepsConfidence));
		
		double percentOrganisationReputation = (meetingExp.getCountOrgReputation_Lost())*100.0/countAllEntries; 
		physicianReponseData.add(new MeetingExperienceDataEntity("Was not impressed  by organisation's reputations", 100.0 - percentOrganisationReputation));
	
		return physicianReponseData;
	}

}
