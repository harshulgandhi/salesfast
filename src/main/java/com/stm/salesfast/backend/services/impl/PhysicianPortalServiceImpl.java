package com.stm.salesfast.backend.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dto.AppointmentDto;
import com.stm.salesfast.backend.dto.MeetingUpdateDto;
import com.stm.salesfast.backend.dto.UserDto;
import com.stm.salesfast.backend.entity.AppointmentForPhysEntity;
import com.stm.salesfast.backend.entity.EDetailingMaterialEntity;
import com.stm.salesfast.backend.services.specs.AppointmentService;
import com.stm.salesfast.backend.services.specs.MeetingUpdateService;
import com.stm.salesfast.backend.services.specs.PhysicianFetchService;
import com.stm.salesfast.backend.services.specs.PhysicianPortalService;
import com.stm.salesfast.backend.services.specs.ProductFetchService;
import com.stm.salesfast.backend.services.specs.UserDetailService;

@Service
public class PhysicianPortalServiceImpl implements PhysicianPortalService {
	private Logger log = LoggerFactory.getLogger(PhysicianPortalServiceImpl.class.getName());
	
	@Autowired
	AppointmentService appointmentService;
	
	@Autowired
	UserDetailService userDetail;
	
	@Autowired
	ProductFetchService productService;
	
	@Autowired
	PhysicianFetchService physicianService;
	
	@Autowired
	MeetingUpdateService meetingUpdates;
	
	@Override
	public List<AppointmentForPhysEntity> getMeetingsForPhysician(
			int physiciansUserId) {
		
		int physicianId = physicianService.getPhysicianIdByName(userDetail.getUserDetails(physiciansUserId).getFirstName(), 
				userDetail.getUserDetails(physiciansUserId).getLastName(),
				userDetail.getUserDetails(physiciansUserId).getEmail());
		
		List<MeetingUpdateDto> allMeetings = meetingUpdates.getForPhysiciansPortal("PRESCRIBING", "PROSPECTING", "LOST",physicianId);
		log.info("No of appointments for phys : "+allMeetings.size()+" for physician : "+physicianId);
		for(MeetingUpdateDto each : allMeetings) log.info(""+each);
		List<AppointmentForPhysEntity> physMeetings = new ArrayList<>();
		for(MeetingUpdateDto eachMeeting : allMeetings){
			String salesRep = userDetail.getUserCompleteName(
					appointmentService.getById(
							eachMeeting.getAppointmentId()).getUserId());
			UserDto user = userDetail.getUserDetails(appointmentService.getById(eachMeeting.getAppointmentId()).getUserId());
			
			physMeetings.add(new AppointmentForPhysEntity(
					eachMeeting.getAppointmentId(),
					eachMeeting.getPhysicianId(),
					salesRep,
					user.getContactNumber(),
					eachMeeting.getStatus(),
					appointmentService.getById(eachMeeting.getAppointmentId()).getStartTime(),
					appointmentService.getById(eachMeeting.getAppointmentId()).getEndTime(),
					eachMeeting.getDate(),
					productService.getProductById(eachMeeting.getProductId()).getProductName(),
					appointmentService.getById(eachMeeting.getAppointmentId()).isHasMeetingExperienceFromPH()
					));
		}
		return physMeetings;
	}
	
	public List<EDetailingMaterialEntity> getEDetailingMaterial(){
		return null;
		
	}
	

}
