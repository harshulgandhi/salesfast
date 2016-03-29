package com.stm.salesfast.backend.services.impl;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.stm.salesfast.backend.dao.specs.PitchesDao;
import com.stm.salesfast.backend.dto.MeetingUpdateDto;
import com.stm.salesfast.backend.dto.PitchesDto;
import com.stm.salesfast.backend.entity.PitchViewEntity;
import com.stm.salesfast.backend.services.specs.AppointmentService;
import com.stm.salesfast.backend.services.specs.MeetingUpdateService;
import com.stm.salesfast.backend.services.specs.PitchesService;
import com.stm.salesfast.constant.ConstantValues;

@Service
public class PitchesServiceImpl implements PitchesService{
	
	@Autowired
    private HttpServletRequest request;
	
	@Autowired
	PitchesDao pitchDao;
	
	@Autowired
	MeetingUpdateService meetingUpdateServ;
	
	@Autowired
	AppointmentService appointmentServ; 
	
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
		return new PitchViewEntity(pitchDto.getAppointmentId(), pitchDto.getMeetingStatus(), fileLocation, pitchDto.getPitchScore());
	}
	
	
}
