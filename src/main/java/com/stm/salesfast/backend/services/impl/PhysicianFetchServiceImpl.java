package com.stm.salesfast.backend.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.PhysicianStgDao;
import com.stm.salesfast.backend.dto.AppointmentDto;
import com.stm.salesfast.backend.dto.PhysicianStgDto;
import com.stm.salesfast.backend.dto.ProductDto;
import com.stm.salesfast.backend.dto.UserDto;
import com.stm.salesfast.backend.entity.PhysicianAppointmentCancellationEntity;
import com.stm.salesfast.backend.services.specs.AppointmentService;
import com.stm.salesfast.backend.services.specs.PhysicianFetchService;
import com.stm.salesfast.backend.services.specs.ProductFetchService;
import com.stm.salesfast.backend.services.specs.UserDetailService;
import com.stm.salesfast.constant.ConstantValues;

@Service
public class PhysicianFetchServiceImpl implements PhysicianFetchService {
	private Logger log = LoggerFactory.getLogger(PhysicianFetchServiceImpl.class.getName());
	
	@Autowired
	PhysicianStgDao physicianDao;
	
	@Autowired
	UserDetailService userDetailService;
	
	@Autowired
	AppointmentService appointmentService;
	
	@Autowired
	ProductFetchService productService;
	
	@Override
	public PhysicianStgDto getPhysicianById(int physicianId) {
		// TODO Auto-generated method stub
		return physicianDao.getBy(physicianId);
	}

	@Override
	public String getPhysicianZipById(int physId) {
		// TODO Auto-generated method stub
		return physicianDao.getZipById(physId);
	}

	@Override
	public PhysicianAppointmentCancellationEntity getAppointmentDetailForPhysician(int appointmentId) {
		// TODO Auto-generated method stub
		
		AppointmentDto appointmentDto = appointmentService.getById(appointmentId);
		int userId = appointmentDto.getUserId();
		String salesRepName = userDetailService.getUserDetails(userId).getFirstName() + " " +userDetailService.getUserDetails(userId).getLastName();
		ProductDto product = productService.getProductById(appointmentDto.getProductId());
		return new PhysicianAppointmentCancellationEntity(appointmentId, salesRepName, ConstantValues.organisation, product.getProductName(), appointmentDto.getStartTime().toString(), appointmentDto.getEndTime().toString(), appointmentDto.getConfirmationStatus());
		
	}

	@Override
	public List<PhysicianStgDto> getAllPhysicians() {
		// TODO Auto-generated method stub
		return physicianDao.getAll();
	}
	
	@Override
	public void updateImportanceFactor(double importanceFactor, int physicianId) {
		// TODO Auto-generated method stub
		physicianDao.updateImportance(importanceFactor, physicianId);;
	}
	
	@Override
	public void updatePhysicianStatus(int physicianId, String status) {
		// TODO Auto-generated method stub
		physicianDao.updateStatus(physicianId, status);
	}

	@Override
	public String getPhysicianName(int physicianId) {
		// TODO Auto-generated method stub
		PhysicianStgDto physician = physicianDao.getBy(physicianId);
		return physician.getFirstName() + " " + physician.getLastName();
	}
	
	@Override
	public int getPhysicianIdByName(String firstName, String lastName, String email){
		return physicianDao.getBy(firstName, lastName, email).getPhysicianId();
	}
	
	@Override
	public PhysicianStgDto getPhysicianByHisUserId(int userId){
		UserDto user = userDetailService.getUserDetails(userId);
		log.info("Getting physician Id for FirstName : "+user.getFirstName()+", Last Name : "+user.getLastName()+", email : "+user.getEmail());
		int physicianId = getPhysicianIdByName(user.getFirstName(), user.getLastName(), user.getEmail());
		return getPhysicianById(physicianId);
	}
}