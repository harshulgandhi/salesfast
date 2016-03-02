package com.stm.salesfast.backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.PhysicianStgDao;
import com.stm.salesfast.backend.dto.AppointmentDto;
import com.stm.salesfast.backend.dto.PhysicianStgDto;
import com.stm.salesfast.backend.dto.ProductDto;
import com.stm.salesfast.backend.entity.PhysicianAppointmentEntity;
import com.stm.salesfast.backend.services.specs.AppointmentService;
import com.stm.salesfast.backend.services.specs.PhysicianFetchService;
import com.stm.salesfast.backend.services.specs.ProductFetchService;
import com.stm.salesfast.backend.services.specs.UserDetailService;
import com.stm.salesfast.constant.ConstantValues;

@Service
public class PhysicianFetchServiceImpl implements PhysicianFetchService {

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
	public PhysicianAppointmentEntity getAppointmentDetailForPhysician(
			String username, int appointmentId) {
		// TODO Auto-generated method stub
		String salesRepName = userDetailService.getUserDetails(username).getFirstName() + " " +userDetailService.getUserDetails(username).getLastName();
		AppointmentDto appointmentDto = appointmentService.getById(appointmentId);
		ProductDto product = productService.getProductById(appointmentDto.getProductId());
		return new PhysicianAppointmentEntity(salesRepName, ConstantValues.organisation, product.getProductName(), appointmentDto.getTime().toString(), appointmentDto.getConfirmationStatus());
		
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
}
