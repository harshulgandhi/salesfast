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
	public void addAppointment(int physId, Time time, Date date,  String confirmationStatus, int productId, String additionalNotes) throws ParseException {
		// TODO Auto-generated method stub
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CURRENTUSERNAME = user.getUsername(); //get logged in user name
		int userId = userAccountService.getUserIdByUserName(CURRENTUSERNAME);
		String zip = physicianService.getPhysicianZipById(physId);
		appointmentDao.insertAppointment(new AppointmentDto(time, date, physId, userId, productId,confirmationStatus, zip, new String(""), additionalNotes, false, false));
	}
	
	/**
	 * This method returns CURRENT DAY's appointments of a user based on whether
	 * he/she has entered meeting update or meeting experience details. Also confirms
	 * if the appointment status is confirmed 
	 * @throws ParseException 
	 * */
	@Override
	public List<AppointmentEntity> getTodaysAppointmentToShow(int userId) throws ParseException {
		// TODO Auto-generated method stub
		List<AppointmentDto> appointmentDtos = appointmentDao.getAppointmentByUserId(userId);
		List<AppointmentEntity> todaysAppointmentEntitiesList = new ArrayList<>();
		for(AppointmentDto eachAppointment : appointmentDtos){
			PhysicianStgDto physicianDto = physicianService.getPhysicianById(eachAppointment.getPhysicianId());
			ProductDto productDto = productFetchService.getProductById(eachAppointment.getProductId());
			if(eachAppointment.getDate().getTime() <= SalesFastUtilities.getCurrentDate().getTime()){
				todaysAppointmentEntitiesList.add(new AppointmentEntity(eachAppointment.getAppointmnetId(),
						physicianDto.getPhysicianId(),
						physicianDto.getFirstName()+" "+physicianDto.getLastName(), 
						physicianDto.getAddressLineOne()+" "+physicianDto.getAddressLineTwo()+" "+physicianDto.getCity()+"-"+physicianDto.getZip(),
						physicianDto.getContactNumber(), 
						physicianDto.getEmail(), 
						eachAppointment.getConfirmationStatus(),
						eachAppointment.getTime(),
						eachAppointment.getDate(),
						productDto.getProductName(),
						eachAppointment.isHasMeetingUpdate(),
						eachAppointment.isHasMeetingExperience(),
						eachAppointment.getCancellationReason(),
						eachAppointment.getAdditionalNotes()));
			}
		}
		log.info("Appointment fetched are : \n");
		return todaysAppointmentEntitiesList;
	}

	/**
	 * This method returns FUTURE DATE's appointments of a user based on whether
	 * he/she has entered meeting update or meeting experience details 
	 * @throws ParseException 
	 * */
	@Override
	public List<AppointmentEntity> getFutureAppointmentToShow(int userId) throws ParseException {
		// TODO Auto-generated method stub
		List<AppointmentDto> appointmentDtos = appointmentDao.getAppointmentByUserId(userId);
		List<AppointmentEntity> futureAppointmentEntitiesList = new ArrayList<>();
		for(AppointmentDto eachAppointment : appointmentDtos){
			PhysicianStgDto physicianDto = physicianService.getPhysicianById(eachAppointment.getPhysicianId());
			ProductDto productDto = productFetchService.getProductById(eachAppointment.getProductId());
			if(eachAppointment.getDate().getTime() > SalesFastUtilities.getCurrentDate().getTime()){
				futureAppointmentEntitiesList.add(new AppointmentEntity(eachAppointment.getAppointmnetId(),
						physicianDto.getPhysicianId(),
						physicianDto.getFirstName()+" "+physicianDto.getLastName(), 
						physicianDto.getAddressLineOne()+" "+physicianDto.getAddressLineTwo()+" "+physicianDto.getCity()+"-"+physicianDto.getZip(),
						physicianDto.getContactNumber(), 
						physicianDto.getEmail(), 
						eachAppointment.getConfirmationStatus(),
						eachAppointment.getTime(),
						eachAppointment.getDate(),
						productDto.getProductName(),
						eachAppointment.isHasMeetingUpdate(),
						eachAppointment.isHasMeetingExperience(),
						eachAppointment.getCancellationReason(),
						eachAppointment.getAdditionalNotes()));
			}
		}
		return futureAppointmentEntitiesList;
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
	
	@Override
	public void cancelAppointment(int appointmentId, String reason){
		appointmentDao.updateStatus(appointmentId,"CANCELLED", reason);
	}
	
}
