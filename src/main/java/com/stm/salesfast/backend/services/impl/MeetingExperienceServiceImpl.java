package com.stm.salesfast.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
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
	public MeetingExperienceDto fetchMeetingExperienceByAppointmentId(
			int appointmentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MeetingExperienceDto fetchMeetingExperienceForSalesRep() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MeetingExperienceDto fetchMeetingExperienceForPhysician() {
		// TODO Auto-generated method stub
		return null;
	}

}
