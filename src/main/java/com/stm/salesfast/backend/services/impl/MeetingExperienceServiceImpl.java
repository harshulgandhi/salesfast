package com.stm.salesfast.backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.MeetingExperienceDao;
import com.stm.salesfast.backend.dto.MeetingExperienceDto;
import com.stm.salesfast.backend.entity.MeetingExperienceEntity;
import com.stm.salesfast.backend.services.specs.AppointmentService;
import com.stm.salesfast.backend.services.specs.MeetingExperienceService;
import com.stm.salesfast.backend.services.specs.MeetingUpdateService;
import com.stm.salesfast.constant.ConstantValues;

@Service
public class MeetingExperienceServiceImpl implements MeetingExperienceService {

	@Autowired
	MeetingExperienceDao meetingExperienceDao;
	
	@Autowired
	MeetingUpdateService meetingUpdateService;
	
	@Autowired
	AppointmentService appointmentService;
	
	@Override
	public void insert(MeetingExperienceEntity meetingExperience) {
		// TODO Auto-generated method stub
		boolean isSalesRepEntry = ConstantValues.currentrole_temp == "SalesRep" ? true : false;
		boolean isPhysicianEntry = ConstantValues.currentrole_temp == "PH" ? true : false;
		String status = meetingUpdateService.getMeetingUpdateByAppointmentId(meetingExperience.getAppointmentId()).getStatus();
		
		meetingExperienceDao.insert(new MeetingExperienceDto(isPhysicianEntry,
				isSalesRepEntry,
				status,
				meetingExperience.isLikedTheProduct(),
				meetingExperience.isLikedPriceAffordability(),
				meetingExperience.isImpressiveLessSideEffects(),
				meetingExperience.isLikedPresentation(),
				meetingExperience.isSalesRepConfidence(),
				meetingExperience.isImpressiveCompanyReputation(),
				meetingExperience.getAppointmentId()
				));
		appointmentService.setHasMeetingExperienceFlag(meetingExperience.getAppointmentId(), 1);
	}

	@Override
	public List<MeetingExperienceDto> fetchMeetingExperienceByAppointmentId(
			int appointmentId) {
		// TODO Auto-generated method stub
		return meetingExperienceDao.getByAppointmentId(appointmentId);
	}

	@Override
	public List<MeetingExperienceDto> fetchMeetingExperienceForSalesRep() {
		// TODO Auto-generated method stub
		return meetingExperienceDao.getSalesRepEntries();
	}

	@Override
	public List<MeetingExperienceDto> fetchMeetingExperienceForPhysician() {
		// TODO Auto-generated method stub
		return meetingExperienceDao.getPhysicianEntries();
	}
	
	@Override
	public List<MeetingExperienceDto> fetchAll() {
		// TODO Auto-generated method stub
		return meetingExperienceDao.getAll();
	}
	
	@Override
	public int countOfRecords(boolean forPhysicianEntries, boolean forSalesRepEntries){
		if(forPhysicianEntries && forSalesRepEntries) return meetingExperienceDao.countAll();
		else if(forPhysicianEntries) return meetingExperienceDao.countAllPhy();
		else if(forSalesRepEntries) return meetingExperienceDao.countAllSR();
		else return 0;
	}
	
	@Override
	public int getCountOfLikedProduct(int isPhy, int isSR){
		return meetingExperienceDao.countLikedProduct(isPhy, isSR);
	}
	
	@Override
	public int getCountPriceAffordability(int isPhy, int isSR){
		return meetingExperienceDao.countPriceAffordability(isPhy, isSR);
	}
	
	@Override
	public int getCountForLessSideEffects(int isPhy, int isSR){
		return meetingExperienceDao.countLessSideEffects(isPhy, isSR);
	}
	
	@Override
	public int getCountLikedPresentation(int isPhy, int isSR){
		return meetingExperienceDao.countLikedPresentation(isPhy, isSR);
	}
	
	@Override
	public int getCountForRepsConfidence(int isPhy, int isSR){
		return meetingExperienceDao.countLikedProduct(isPhy, isSR);
	}
	
	@Override
	public int getCountOrgReputation(int isPhy, int isSR){
		return meetingExperienceDao.countOrgReputation(isPhy, isSR);
	}

}
