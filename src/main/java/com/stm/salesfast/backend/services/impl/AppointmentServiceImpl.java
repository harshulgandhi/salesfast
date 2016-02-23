package com.stm.salesfast.backend.services.impl;

import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class AppointmentServiceImpl implements AppointmentService {

	private Logger log = LoggerFactory.getLogger(AppointmentServiceImpl.class.getName());

	String CURRENTUSERNAME = "johny";
	
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
	public void addAppointment(int physId, Time time, String confirmationStatus) throws ParseException {
		// TODO Auto-generated method stub
		int userId = userAccountService.getUserIdByUserName(CURRENTUSERNAME);
		int productId = alignmentFetchService.getAlignedProduct(userId, physId);
		String zip = physicianService.getPhysicianZipById(physId);
		appointmentDao.insertAppointment(new AppointmentDto(time, SalesFastUtilities.getCurrentDate(), physId, userId, productId,confirmationStatus, zip));
	}


	
	
	@Override
	public List<AppointmentEntity> getAppointmentToShow(int userId) {
		// TODO Auto-generated method stub
		List<AppointmentDto> appointmentDtos = appointmentDao.getAppointmentByUserId(userId);
		List<AppointmentEntity> appointmentEntitiesList = new ArrayList<>();
		int i = 0;
		for(AppointmentDto eachAppointment : appointmentDtos){
			log.info("Fetching ==> "+(++i));
			PhysicianStgDto physicianDto = physicianService.getPhysicianById(eachAppointment.getPhysicianId());
			ProductDto productDto = productFetchService.getProductById(eachAppointment.getProductId());
			appointmentEntitiesList.add(new AppointmentEntity(physicianDto.getFirstName()+" "+physicianDto.getLastName(), 
					physicianDto.getAddressLineOne()+" "+physicianDto.getAddressLineTwo()+" "+physicianDto.getCity()+"-"+physicianDto.getZip(),
					physicianDto.getContactNumber(), 
					physicianDto.getEmail(), 
					eachAppointment.getConfirmationStatus(),
					eachAppointment.getTime(), 
					productDto.getProductName()));
		}
		log.info("Appointment fetched are : \n");
		for (AppointmentEntity eachAppointment : appointmentEntitiesList) log.info(""+eachAppointment);
		return appointmentEntitiesList;
	}

	
	
}
