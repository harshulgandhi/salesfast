package com.stm.salesfast.backend.services.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.stm.salesfast.backend.controllers.LiveMeetingController;
import com.stm.salesfast.backend.dao.specs.PitchesDao;
import com.stm.salesfast.backend.dto.AppointmentDto;
import com.stm.salesfast.backend.dto.MeetingUpdateDto;
import com.stm.salesfast.backend.dto.PitchesDto;
import com.stm.salesfast.backend.entity.PitchViewEntity;
import com.stm.salesfast.backend.entity.ViewAllPitchEntity;
import com.stm.salesfast.backend.entity.ViewPitchFilterParamEntity;
import com.stm.salesfast.backend.services.specs.AppointmentService;
import com.stm.salesfast.backend.services.specs.MedicalFieldService;
import com.stm.salesfast.backend.services.specs.MeetingUpdateService;
import com.stm.salesfast.backend.services.specs.PhysicianFetchService;
import com.stm.salesfast.backend.services.specs.PitchesService;
import com.stm.salesfast.backend.services.specs.ProductFetchService;
import com.stm.salesfast.backend.services.specs.UserDetailService;
import com.stm.salesfast.constant.ConstantValues;

@Service
public class PitchesServiceImpl implements PitchesService{
	
	private Logger log = LoggerFactory.getLogger(PitchesServiceImpl.class.getName());

	@Autowired
    private HttpServletRequest request;
	
	@Autowired
	PitchesDao pitchDao;
	
	@Autowired
	MeetingUpdateService meetingUpdateServ;
	
	@Autowired
	AppointmentService appointmentServ; 
	
	@Autowired
	UserDetailService userService;
	
	@Autowired
	PhysicianFetchService physService;
	
	@Autowired
	ProductFetchService prodService;
	
	@Autowired
	MedicalFieldService medService;
	
	@Override
	public void insertPitch(PitchesDto pitch) {
		pitchDao.insert(pitch);
	}

	@Override
	public void insertNewPitch(int appointmentId, MultipartFile pitch) throws IllegalStateException, IOException{
		MeetingUpdateDto meetingUpdate = meetingUpdateServ.getMeetingUpdateByAppointmentId(appointmentId);
		String status = (meetingUpdate == null) ? "TO BE DETAILED" : meetingUpdate.getStatus();
		saveTheFile(pitch);
		
		insertPitch(new PitchesDto(appointmentId, status, pitch.getOriginalFilename(), 1
				));
		appointmentServ.setHasPitchFlag(appointmentId);
		
	}
	
	@Override
	public void saveTheFile(MultipartFile pitchFile) throws IllegalStateException, IOException{
		String realPathtoUploads =   request.getServletContext().getRealPath("/resources/");
		String orgName = "docs\\meetingpitch\\"+pitchFile.getOriginalFilename();
        String filePath = realPathtoUploads + orgName;
        File dest = new File(filePath);
        pitchFile.transferTo(dest);
	}
	
	@Override
	public void updateStatus(String status, int appointmentId){
		pitchDao.updateStatus(appointmentId, status);
	}
	
	@Override
	public void updatePitchFileName(String fileName, int appointmentId){
		pitchDao.updatePitchFile(appointmentId, fileName);
	}
	
	@Override
	public void updatePitchFile(int appointmentId, MultipartFile pitchdocument) throws IllegalStateException, IOException{
		String realPathtoUploads =   request.getServletContext().getRealPath("/resources/");
		String orgName = "docs\\meetingpitch\\"+pitchdocument.getOriginalFilename();
        String filePath = realPathtoUploads + orgName;
        File dest = new File(filePath);
        pitchdocument.transferTo(dest);
		updatePitchFileName(pitchdocument.getOriginalFilename(), appointmentId);
	}
	
	@Override
	public PitchViewEntity getPitchForAppointment(int appointmentId){
		PitchesDto pitchDto = pitchDao.getPitchByAppointmentId(appointmentId);
		String fileLocation = ConstantValues.PITCH_DOCS_PATH+pitchDto.getFileName();
		return new PitchViewEntity(pitchDto.getPitchesId(), pitchDto.getAppointmentId(), pitchDto.getMeetingStatus(), fileLocation, pitchDto.getPitchScore());
	}
	
	@Override
	public List<ViewAllPitchEntity> getAllPitchesForFilter(ViewPitchFilterParamEntity filterForPitch){
		List<AppointmentDto> allAppointments = new ArrayList<>();
		
		//Get initial list of appointments based on top most available filter.
		//After this, the appointment list is further filtered.
		if(filterForPitch.getMedicalFieldId() != null){
			allAppointments = appointmentServ.getAppointmentsByMedicalField(filterForPitch.getMedicalFieldId());
		}else if(filterForPitch.getMedicalFieldId() == null && filterForPitch.getProductId() != 0){
			allAppointments = appointmentServ.getAppointmentsByProduct(filterForPitch.getProductId());
		}else if(filterForPitch.getProductId() == 0 && filterForPitch.getUserId() != 0){
			allAppointments = appointmentServ.getAppointmentsBySalesRep(filterForPitch.getUserId());
		}else if(filterForPitch.getUserId() == 0 && filterForPitch.getPhysicianId() != 0){
			allAppointments = appointmentServ.getAppointmentsByPhysician(filterForPitch.getPhysicianId());
		}else if(filterForPitch.getPhysicianId() == 0){
			//get all appointments having pitch
			allAppointments = appointmentServ.getAllAppointmentsHavingPitch();
		}
		List<AppointmentDto> filteredAppointments = applyFurtherFilter(allAppointments, filterForPitch);
		for(AppointmentDto eachAppointment : filteredAppointments) log.info("FILTERED "+eachAppointment);
		return getPitchViewEntityFields(filteredAppointments);
	}
	
	@Override
	public List<ViewAllPitchEntity> getPitchViewEntityFields(List<AppointmentDto> filteredAppointments){
		List<ViewAllPitchEntity> allPitchView = new ArrayList<>();
		
		for(AppointmentDto eachApp : filteredAppointments){
			PitchViewEntity pitch  = getPitchForAppointment(eachApp.getAppointmnetId());
			int salesRepId = appointmentServ.getById(pitch.getAppointmentId()).getUserId();
			String salesRepName = userService.getUserCompleteName(salesRepId);
			int physicianId = appointmentServ.getById(pitch.getAppointmentId()).getPhysicianId();
			String physicianName = physService.getPhysicianName(physicianId);
			int productId = appointmentServ.getById(pitch.getAppointmentId()).getProductId();
			String product = prodService.getProductById(productId).getProductName();
			String medicalFieldId = prodService.getMedicalFieldForProduct(productId);
			String medicalFieldName = medService.getByMedicalField(medicalFieldId).getMedicalFieldName();
			
			allPitchView.add(new ViewAllPitchEntity(
					pitch.getPitchId(),
					pitch.getAppointmentId(),
					pitch.getMeetingStatus(),
					salesRepId,
					salesRepName,
					physicianId,
					physicianName,
					eachApp.getDate(),
					productId,
					product,
					medicalFieldId,
					medicalFieldName,
					pitch.getFileLocation(),
					pitch.getPitchScore()
					));
			
		}
		
		return allPitchView;
	}
	
	@Override
	public List<AppointmentDto> applyFurtherFilter(List<AppointmentDto> appointments, ViewPitchFilterParamEntity filterEntity){
		if(filterEntity.getMedicalFieldId() != null){
			List<AppointmentDto> toRemove = new ArrayList<>();
			for(AppointmentDto eachAppointment : appointments){
				if(filterEntity.getProductId() != 0 && eachAppointment.getProductId() != filterEntity.getProductId()){
					toRemove.add(eachAppointment);
				}
			}
			appointments.removeAll(toRemove);
			for(AppointmentDto eachAppointment : appointments){
				if(filterEntity.getUserId() !=0 && eachAppointment.getUserId() != filterEntity.getUserId()){
					toRemove.add(eachAppointment);
				}
			}
			appointments.removeAll(toRemove);
			for(AppointmentDto eachAppointment : appointments){
				if(filterEntity.getPhysicianId() !=0 && eachAppointment.getPhysicianId() != filterEntity.getPhysicianId()){
					toRemove.add(eachAppointment);
				}
			}
			appointments.removeAll(toRemove);
		}else if(filterEntity.getProductId() != 0){
			List<AppointmentDto> toRemove = new ArrayList<>();
			for(AppointmentDto eachAppointment : appointments){
				if(filterEntity.getUserId() !=0 && eachAppointment.getUserId() != filterEntity.getUserId()){
					toRemove.add(eachAppointment);
				}
			}
			appointments.removeAll(toRemove);
			for(AppointmentDto eachAppointment : appointments){
				if(filterEntity.getPhysicianId() !=0 && eachAppointment.getPhysicianId() != filterEntity.getPhysicianId()){
					toRemove.add(eachAppointment);
				}
			}
			appointments.removeAll(toRemove);
		}else if(filterEntity.getPhysicianId() != 0){
			List<AppointmentDto> toRemove = new ArrayList<>();
			for(AppointmentDto eachAppointment : appointments){
				if(filterEntity.getPhysicianId() !=0 && eachAppointment.getPhysicianId() != filterEntity.getPhysicianId()){
					toRemove.add(eachAppointment);
				}
			}
			appointments.removeAll(toRemove);
		}
		
		return appointments;
	}
	
}
