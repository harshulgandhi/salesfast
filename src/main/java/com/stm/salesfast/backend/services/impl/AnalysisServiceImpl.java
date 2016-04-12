package com.stm.salesfast.backend.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dto.MeetingExperienceDto;
import com.stm.salesfast.backend.entity.MeetingExpAnalysisFilterEntity;
import com.stm.salesfast.backend.entity.MeetingExperienceAnalyzedDataEntity;
import com.stm.salesfast.backend.entity.MeetingExperienceDataEntity;
import com.stm.salesfast.backend.entity.MeetingExperienceDetailedDataEntity;
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
		List<MeetingExperienceDataEntity> physicianResponseData = new ArrayList<>();
		int countAllEntries = meetingExp.getCountAll_Lost();
		double percentLikedProduct = (meetingExp.getCountOfLikedProduct_Lost())*100.0/countAllEntries; 
		physicianResponseData.add(new MeetingExperienceDataEntity("Did not like the product", 100.0-percentLikedProduct));
		
		double percentPriceAffordability = (meetingExp.getCountPriceAffordability_Lost())*100.0/countAllEntries; 
		physicianResponseData.add(new MeetingExperienceDataEntity("Found the product expensive", 100.0-percentPriceAffordability));
		
		double percentLessSideEffects = (meetingExp.getCountForLessSideEffects_Lost())*100.0/countAllEntries; 
		physicianResponseData.add(new MeetingExperienceDataEntity("Felt that product has many side effects", 100.0 - percentLessSideEffects));
		
		double percentLikedPresentation = (meetingExp.getCountLikedPresentation_Lost())*100.0/countAllEntries; 
		physicianResponseData.add(new MeetingExperienceDataEntity("Was not influenced by presentation", 100.0 - percentLikedPresentation));
		
		double percentLikedRepsConfidence = (meetingExp.getCountForRepsConfidence_Lost())*100.0/countAllEntries; 
		physicianResponseData.add(new MeetingExperienceDataEntity("Was not impressed by SalesRep's confidence", 100.0 - percentLikedRepsConfidence));
		
		double percentOrganisationReputation = (meetingExp.getCountOrgReputation_Lost())*100.0/countAllEntries; 
		physicianResponseData.add(new MeetingExperienceDataEntity("Was not impressed  by organisation's reputations", 100.0 - percentOrganisationReputation));
	
		return physicianResponseData;
	}

	@Override
	public List<MeetingExperienceAnalyzedDataEntity> calculateAnalysisPercentageForLost(int[][] countMatrix,int countAllSREntries, int countAllPhysEntries, int countAll){
		List<MeetingExperienceAnalyzedDataEntity> analyzedDataFiltered = new ArrayList<>();
		
		List<MeetingExperienceDataEntity> onlyPhysAnalyzedData = new ArrayList<>();
		onlyPhysAnalyzedData.add(new MeetingExperienceDataEntity("Did not like the product", 100.0-(countMatrix[0][0]*100.0/countAllPhysEntries)));
		onlyPhysAnalyzedData.add(new MeetingExperienceDataEntity("Too expensive", 100.0-(countMatrix[0][1]*100.0/countAllPhysEntries)));
		onlyPhysAnalyzedData.add(new MeetingExperienceDataEntity("Has too many side effects", 100.0-(countMatrix[0][2]*100.0/countAllPhysEntries)));
		onlyPhysAnalyzedData.add(new MeetingExperienceDataEntity("Presentation could be better", 100.0-(countMatrix[0][3]*100.0/countAllPhysEntries)));
		onlyPhysAnalyzedData.add(new MeetingExperienceDataEntity("SalesRep needs to be more confident", 100.0-(countMatrix[0][4]*100.0/countAllPhysEntries)));
		onlyPhysAnalyzedData.add(new MeetingExperienceDataEntity("Not impressed with organisation's reputations", 100.0-(countMatrix[0][5]*100.0/countAllPhysEntries)));
		analyzedDataFiltered.add(new MeetingExperienceAnalyzedDataEntity("According to Physicians", onlyPhysAnalyzedData));
		
		List<MeetingExperienceDataEntity> onlySRAnalyzedData = new ArrayList<>();
		onlySRAnalyzedData.add(new MeetingExperienceDataEntity("Did not like the product", 100.0-(countMatrix[1][0]*100.0/countAllSREntries)));
		onlySRAnalyzedData.add(new MeetingExperienceDataEntity("Too expensive", 100.0-(countMatrix[1][1]*100.0/countAllSREntries)));
		onlySRAnalyzedData.add(new MeetingExperienceDataEntity("Has too many side effects", 100.0-(countMatrix[1][2]*100.0/countAllSREntries)));
		onlySRAnalyzedData.add(new MeetingExperienceDataEntity("Presentation could be better", 100.0-(countMatrix[1][3]*100.0/countAllSREntries)));
		onlySRAnalyzedData.add(new MeetingExperienceDataEntity("SalesRep needs to be more confident", 100.0-(countMatrix[1][4]*100.0/countAllSREntries)));
		onlySRAnalyzedData.add(new MeetingExperienceDataEntity("Not impressed with organisation's reputations", 100.0-(countMatrix[1][5]*100.0/countAllSREntries)));
		analyzedDataFiltered.add(new MeetingExperienceAnalyzedDataEntity("According to SalesReps", onlySRAnalyzedData));
		
		List<MeetingExperienceDataEntity> allAnalyzedData = new ArrayList<>();
		allAnalyzedData.add(new MeetingExperienceDataEntity("Did not like the product", 100.0-(countMatrix[2][0]*100.0/countAll)));
		allAnalyzedData.add(new MeetingExperienceDataEntity("Too expensive", 100.0-(countMatrix[2][1]*100.0/countAll)));
		allAnalyzedData.add(new MeetingExperienceDataEntity("Has too many side effects", 100.0-(countMatrix[2][2]*100.0/countAll)));
		allAnalyzedData.add(new MeetingExperienceDataEntity("Presentation could be better", 100.0-(countMatrix[2][3]*100.0/countAll)));
		allAnalyzedData.add(new MeetingExperienceDataEntity("SalesRep needs to be more confident", 100.0-(countMatrix[2][4]*100.0/countAll)));
		allAnalyzedData.add(new MeetingExperienceDataEntity("Not impressed with organisation's reputations", 100.0-(countMatrix[2][5]*100.0/countAll)));
		analyzedDataFiltered.add(new MeetingExperienceAnalyzedDataEntity("Aggregated", allAnalyzedData));
		
		return analyzedDataFiltered;
	}
	
	@Override
	public List<MeetingExperienceAnalyzedDataEntity> calculateAnalysisPercentage(int[][] countMatrix,int countAllSREntries, int countAllPhysEntries, int countAll){
		List<MeetingExperienceAnalyzedDataEntity> analyzedDataFiltered = new ArrayList<>();
		
		List<MeetingExperienceDataEntity> onlyPhysAnalyzedData = new ArrayList<>();
		onlyPhysAnalyzedData.add(new MeetingExperienceDataEntity("Genuinely liked the product", countMatrix[0][0]*100.0/countAllPhysEntries));
		onlyPhysAnalyzedData.add(new MeetingExperienceDataEntity("Price affordability", countMatrix[0][1]*100.0/countAllPhysEntries));
		onlyPhysAnalyzedData.add(new MeetingExperienceDataEntity("Liked that product has less side effects", countMatrix[0][2]*100.0/countAllPhysEntries));
		onlyPhysAnalyzedData.add(new MeetingExperienceDataEntity("Influenced by presentation", countMatrix[0][3]*100.0/countAllPhysEntries));
		onlyPhysAnalyzedData.add(new MeetingExperienceDataEntity("Impressed by SalesRep's confidence", countMatrix[0][4]*100.0/countAllPhysEntries));
		onlyPhysAnalyzedData.add(new MeetingExperienceDataEntity("Impressed with organisation's reputations", countMatrix[0][5]*100.0/countAllPhysEntries));
		analyzedDataFiltered.add(new MeetingExperienceAnalyzedDataEntity("According to Physicians", onlyPhysAnalyzedData));
		
		List<MeetingExperienceDataEntity> onlySRAnalyzedData = new ArrayList<>();
		onlySRAnalyzedData.add(new MeetingExperienceDataEntity("Genuinely liked the product", countMatrix[1][0]*100.0/countAllSREntries));
		onlySRAnalyzedData.add(new MeetingExperienceDataEntity("Price affordability", countMatrix[1][1]*100.0/countAllSREntries));
		onlySRAnalyzedData.add(new MeetingExperienceDataEntity("Liked that product has less side effects", countMatrix[1][2]*100.0/countAllSREntries));
		onlySRAnalyzedData.add(new MeetingExperienceDataEntity("Influenced by presentation", countMatrix[1][3]*100.0/countAllSREntries));
		onlySRAnalyzedData.add(new MeetingExperienceDataEntity("Impressed by SalesRep's confidence", countMatrix[1][4]*100.0/countAllSREntries));
		onlySRAnalyzedData.add(new MeetingExperienceDataEntity("Impressed with organisation's reputations", countMatrix[1][5]*100.0/countAllSREntries));
		analyzedDataFiltered.add(new MeetingExperienceAnalyzedDataEntity("According to SalesReps", onlySRAnalyzedData));
		
		List<MeetingExperienceDataEntity> allAnalyzedData = new ArrayList<>();
		allAnalyzedData.add(new MeetingExperienceDataEntity("Genuinely liked the product", countMatrix[2][0]*100.0/countAll));
		allAnalyzedData.add(new MeetingExperienceDataEntity("Price affordability", countMatrix[2][1]*100.0/countAll));
		allAnalyzedData.add(new MeetingExperienceDataEntity("Liked that product has less side effects", countMatrix[2][2]*100.0/countAll));
		allAnalyzedData.add(new MeetingExperienceDataEntity("Influenced by presentation", countMatrix[2][3]*100.0/countAll));
		allAnalyzedData.add(new MeetingExperienceDataEntity("Impressed by SalesRep's confidence", countMatrix[2][4]*100.0/countAll));
		allAnalyzedData.add(new MeetingExperienceDataEntity("Impressed with organisation's reputations", countMatrix[2][5]*100.0/countAll));
		analyzedDataFiltered.add(new MeetingExperienceAnalyzedDataEntity("Aggregated", allAnalyzedData));
		
		return analyzedDataFiltered;
	}
	
	@Override
	public List<MeetingExperienceAnalyzedDataEntity> analyseMeetingExperience(MeetingExpAnalysisFilterEntity filter){
		List<MeetingExperienceDetailedDataEntity> allEntries = meetingExp.fetchAllDetailedRecords();
		int countAllSREntries = 0;
		int countAllPhysEntries = 0;
		int countAll = 0;
		int countMatrix[][] = new int[3][7];
		
		for(MeetingExperienceDetailedDataEntity eachEntity : allEntries){
			if(filter.getMedicalFieldId() != null && filter.getMedicalFieldId().equals(eachEntity.getMedicalFieldId())){
//				log.info("FirstIf : "+filter.getMedicalFieldId()+" "+filter.getProductId()+" "+filter.getUserId()+" "+filter.getStatus());
//				log.info(filter.getMedicalFieldId() +" : "+eachEntity.getMedicalFieldId());
				
				if(filter.getProductId() != 0 && filter.getProductId() == eachEntity.getProductId()){
//					log.info(filter.getProductId() +" : "+eachEntity.getProductId());
					
					if(filter.getUserId() !=0 && filter.getUserId() == eachEntity.getUserId()){
						if(filter.getStatus() != null && filter.getStatus().equals(eachEntity.getStatus())){
							applyCount(countMatrix, eachEntity,countAllSREntries, countAllPhysEntries, countAll);
						}
						else if(filter.getStatus() == null){
							applyCount(countMatrix, eachEntity,countAllSREntries, countAllPhysEntries, countAll);
						}
					}
					else if(filter.getUserId() == 0 && filter.getStatus() != null && filter.getStatus().equals(eachEntity.getStatus())) {
						applyCount(countMatrix, eachEntity,countAllSREntries, countAllPhysEntries, countAll);
					}
					else if(filter.getUserId() == 0 && filter.getStatus() == null ){
						applyCount(countMatrix, eachEntity,countAllSREntries, countAllPhysEntries, countAll);
					}
				}
				else if(filter.getProductId() == 0 && filter.getUserId() !=0 && filter.getUserId() == eachEntity.getUserId()){
					if(filter.getStatus() != null && filter.getStatus().equals(eachEntity.getStatus())){
						applyCount(countMatrix, eachEntity,countAllSREntries, countAllPhysEntries, countAll);
					}else if(filter.getStatus() == null){
						applyCount(countMatrix, eachEntity,countAllSREntries, countAllPhysEntries, countAll);
					}
				}
				else if(filter.getUserId() ==0 && filter.getStatus() != null && filter.getStatus().equals(eachEntity.getStatus())){
					applyCount(countMatrix, eachEntity,countAllSREntries, countAllPhysEntries, countAll);
				}
				else if (filter.getProductId() == 0 && filter.getUserId() == 0 && filter.getStatus() == null){
					applyCount(countMatrix, eachEntity,countAllSREntries, countAllPhysEntries, countAll);
				}
				
			}
			
			else if(filter.getMedicalFieldId() == null && filter.getProductId() != 0 && filter.getProductId() == eachEntity.getProductId()){
//				log.info("SecondIf : "+filter.getMedicalFieldId()+" "+filter.getProductId()+" "+filter.getUserId()+" "+filter.getStatus());
				if(filter.getUserId() !=0 && filter.getUserId() == eachEntity.getUserId()){
					if(filter.getStatus() != null && filter.getStatus().equals(eachEntity.getStatus())){
						applyCount(countMatrix, eachEntity,countAllSREntries, countAllPhysEntries, countAll);
					}
					else if(filter.getStatus() == null){
						applyCount(countMatrix, eachEntity,countAllSREntries, countAllPhysEntries, countAll);
					}
				}
				else if(filter.getUserId() ==0 && filter.getStatus() != null && filter.getStatus().equals(eachEntity.getStatus())){
					applyCount(countMatrix, eachEntity,countAllSREntries, countAllPhysEntries, countAll);
				}
				else if(filter.getUserId() == 0 && filter.getStatus() == null){
					applyCount(countMatrix, eachEntity,countAllSREntries, countAllPhysEntries, countAll);
				}
			}
			
			else if(filter.getMedicalFieldId() == null && filter.getProductId() == 0 && filter.getUserId() !=0 && filter.getUserId() == eachEntity.getUserId()){
//				log.info("ThirdId: "+filter.getMedicalFieldId()+" "+filter.getProductId()+" "+filter.getUserId()+" "+filter.getStatus());
				if(filter.getStatus() != null && filter.getStatus().equals(eachEntity.getStatus())){
					applyCount(countMatrix, eachEntity,countAllSREntries, countAllPhysEntries, countAll);
				}
				else if(filter.getStatus() == null){
					applyCount(countMatrix, eachEntity,countAllSREntries, countAllPhysEntries, countAll);
				}
			}
			
			else if(filter.getMedicalFieldId() == null && filter.getProductId() == 0 &&  filter.getUserId() == 0 && filter.getStatus() != null && filter.getStatus().equals(eachEntity.getStatus())){
//				log.info("ForthIf : "+filter.getMedicalFieldId()+" "+filter.getProductId()+" "+filter.getUserId()+" "+filter.getStatus());
				applyCount(countMatrix, eachEntity,countAllSREntries, countAllPhysEntries, countAll);
			}
			
			else if(filter.getMedicalFieldId() == null && filter.getProductId() == 0 && filter.getUserId() == 0 && filter.getStatus() == null){
//				log.info("Null filter : "+filter.getMedicalFieldId()+" "+filter.getProductId()+" "+filter.getUserId()+" "+filter.getStatus());
				applyCount(countMatrix, eachEntity,countAllSREntries, countAllPhysEntries, countAll);
			}
		}
//		log.info("Total figure : "+countMatrix[1][6]+" "+countMatrix[0][6]+" "+countMatrix[2][6]);
		for(int[] eachrow : countMatrix){
			System.out.println();
			for(int eachval : eachrow) System.out.print(eachval+" ");
		}
		countAllSREntries = countMatrix[1][6];
		countAllPhysEntries = countMatrix[0][6];
		countAll = countMatrix[2][6];
		
		return (filter.getStatus() != null && filter.getStatus().equals("LOST")) ?
				calculateAnalysisPercentageForLost(countMatrix,countAllSREntries, countAllPhysEntries, countAll)
				: calculateAnalysisPercentage(countMatrix,countAllSREntries, countAllPhysEntries, countAll);
		
	}
	
	@Override
	public void applyCount(int[][] countMatrix, MeetingExperienceDetailedDataEntity entityToBeCounted,int countAllSREntries, int countAllPhysEntries, int countAll){
		countAll++;
		countMatrix[2][6]++;
		if(entityToBeCounted.isSalesRepEntry()) countMatrix[1][6]++;
		if(entityToBeCounted.isPhysicianEntry()) countMatrix[0][6]++;
//		log.info("Applying count to : "+entityToBeCounted);
		if(entityToBeCounted.isLikedTheProduct()){
			countMatrix[2][0]++;
			if(entityToBeCounted.isPhysicianEntry()){
				countMatrix[0][0]++;
			}
			if(entityToBeCounted.isSalesRepEntry()){
				countMatrix[1][0]++;
			}
		}
		
		if(entityToBeCounted.isLikedPriceAffordability()){
			countMatrix[2][1]++;
			if(entityToBeCounted.isPhysicianEntry()){
				countMatrix[0][1]++;
			}
			if(entityToBeCounted.isSalesRepEntry()){
				countMatrix[1][1]++;
			}
		}
		
		if(entityToBeCounted.isImpressiveLessSideEffects()){
			countMatrix[2][2]++;
			if(entityToBeCounted.isPhysicianEntry()){
				countMatrix[0][2]++;
			}
			if(entityToBeCounted.isSalesRepEntry()){
				countMatrix[1][2]++;
			}
		}
		
		if(entityToBeCounted.isLikedPresentation()){
			countMatrix[2][3]++;
			if(entityToBeCounted.isPhysicianEntry()){
				countMatrix[0][3]++;
			}
			if(entityToBeCounted.isSalesRepEntry()){
				countMatrix[1][3]++;
			}
		}
		
		if(entityToBeCounted.isSalesRepConfidence()){
			countMatrix[2][4]++;
			if(entityToBeCounted.isPhysicianEntry()){
				countMatrix[0][4]++;
			}
			if(entityToBeCounted.isSalesRepEntry()){
				countMatrix[1][4]++;
			}
		}
		
		if(entityToBeCounted.isImpressiveCompanyReputation()){
			countMatrix[2][5]++;
			if(entityToBeCounted.isPhysicianEntry()){
				countMatrix[0][5]++;
			}
			if(entityToBeCounted.isSalesRepEntry()){
				countMatrix[1][5]++;
			}
		}
	}


}
