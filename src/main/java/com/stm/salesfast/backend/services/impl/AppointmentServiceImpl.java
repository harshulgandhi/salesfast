package com.stm.salesfast.backend.services.impl;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.controllers.LoginController;
import com.stm.salesfast.backend.dao.specs.AlignmentsDao;
import com.stm.salesfast.backend.dao.specs.AppointmentDao;
import com.stm.salesfast.backend.dto.AppointmentDto;
import com.stm.salesfast.backend.dto.PhysicianStgDto;
import com.stm.salesfast.backend.dto.ProductDto;
import com.stm.salesfast.backend.entity.AppointmentEntity;
import com.stm.salesfast.backend.services.specs.AlignmentFetchService;
import com.stm.salesfast.backend.services.specs.AppointmentService;
import com.stm.salesfast.backend.services.specs.PhysicianFetchService;
import com.stm.salesfast.backend.services.specs.ProductFetchService;
import com.stm.salesfast.backend.services.specs.UserAccountService;
import com.stm.salesfast.backend.utils.SalesFastUtilities;
import com.stm.salesfast.constant.ConstantValues;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	private Logger log = LoggerFactory.getLogger(AppointmentServiceImpl.class.getName());

	String CURRENTUSERNAME = "";
	
	@Autowired
	AppointmentDao appointmentDao;
	
	@Autowired
	UserAccountService userAccountService;
	
	@Autowired
	AlignmentFetchService alignmentFetchService;
	
	@Autowired
	PhysicianFetchService physicianService;
	
	@Autowired
	ProductFetchService productFetchService;
	
	@Override
	public void addAppointment(int physId, Time time, Date date,  String confirmationStatus, int productId) throws ParseException {
		// TODO Auto-generated method stub
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CURRENTUSERNAME = user.getUsername(); //get logged in user name
		int userId = userAccountService.getUserIdByUserName(CURRENTUSERNAME);
		String zip = physicianService.getPhysicianZipById(physId);
		appointmentDao.insertAppointment(new AppointmentDto(time, date, physId, userId, productId,confirmationStatus, zip, false, false));
	}
	
	/**
	 * This method returns appointments of a user based on whether
	 * he/she has entered meeting update or meeting experience details 
	 * */
	@Override
	public List<AppointmentEntity> getAppointmentToShow(int userId) {
		// TODO Auto-generated method stub
		List<AppointmentDto> appointmentDtos = appointmentDao.getAppointmentByUserId(userId);
		List<AppointmentEntity> appointmentEntitiesList = new ArrayList<>();
		int i = 0;
		for(AppointmentDto eachAppointment : appointmentDtos){
			PhysicianStgDto physicianDto = physicianService.getPhysicianById(eachAppointment.getPhysicianId());
			ProductDto productDto = productFetchService.getProductById(eachAppointment.getProductId());
			appointmentEntitiesList.add(new AppointmentEntity(eachAppointment.getAppointmnetId(),
					physicianDto.getPhysicianId(),
					physicianDto.getFirstName()+" "+physicianDto.getLastName(), 
					physicianDto.getAddressLineOne()+" "+physicianDto.getAddressLineTwo()+" "+physicianDto.getCity()+"-"+physicianDto.getZip(),
					physicianDto.getContactNumber(), 
					physicianDto.getEmail(), 
					eachAppointment.getConfirmationStatus(),
					eachAppointment.getTime(), 
					productDto.getProductName(),
					eachAppointment.isHasMeetingUpdate(),
					eachAppointment.isHasMeetingExperience()));
		}
		log.info("Appointment fetched are : \n");
		for (AppointmentEntity eachAppointment : appointmentEntitiesList) log.info(""+eachAppointment);
		return appointmentEntitiesList;
	}


	@Override
	public int getAppointmentId(String username, int physicianId) {
		// TODO Auto-generated method stub
		int userId = userAccountService.getUserAccountByUserName(username).getUserId();
		return appointmentDao.getIdByPhysIdUserId(userId, physicianId);
	}


	@Override
	public AppointmentDto getById(int appointmentId) {
		// TODO Auto-generated method stub
		return appointmentDao.getAppointmentById(appointmentId);
	}


	@Override
	public void setHasMeetingUpdateFlag(int appointmentId, int flag) {
		appointmentDao.setMeetinUpdateFlag(appointmentId, flag);
	}

	@Override
	public void setHasMeetingExperienceFlag(int appointmentId, int flag) {
		appointmentDao.setMeetinExperienceFlag(appointmentId, flag);
		
	}
}
