package com.stm.salesfast.backend.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.EDetailingMaterialDao;
import com.stm.salesfast.backend.dto.EDetailingMaterialDto;
import com.stm.salesfast.backend.dto.UserDto;
import com.stm.salesfast.backend.entity.EDetailingMaterialEntity;
import com.stm.salesfast.backend.services.specs.AlignmentFetchService;
import com.stm.salesfast.backend.services.specs.EDetailingMaterialService;
import com.stm.salesfast.backend.services.specs.PhysicianFetchService;
import com.stm.salesfast.backend.services.specs.ProductFetchService;
import com.stm.salesfast.backend.services.specs.UserDetailService;
import com.stm.salesfast.constant.ConstantValues;

@Service
public class EDetailingMaterialServiceImpl implements EDetailingMaterialService {

	@Autowired
	EDetailingMaterialDao eDetailingDao;
	
	@Autowired
	ProductFetchService products;
	
	@Autowired
	AlignmentFetchService alignment;
	
	@Autowired
	PhysicianFetchService physicianService;
	
	@Autowired
	UserDetailService userService;
	
	@Override
	public EDetailingMaterialDto getEDetailingById(int eDetailingId) {
		// TODO Auto-generated method stub
		return eDetailingDao.getById(eDetailingId);
	}

	@Override
	public List<EDetailingMaterialDto> getEDetailingByPhysicianId(
			int physicianId) {
		// TODO Auto-generated method stub
		return eDetailingDao.getByPhysicianId(physicianId);
	}

	@Override
	public void insert(EDetailingMaterialDto eDetailing) {
		eDetailingDao.insert(eDetailing);
	}
	
	@Override
	public String getDetailingFileName(int productId){
		return eDetailingDao.getDetailingFileName(productId);
	}
	
	@Override
	public List<EDetailingMaterialEntity> getEDetailingMaterialForUI(
			int userId) {
		int physicianId = physicianService.getPhysicianByHisUserId(userId).getPhysicianId();
		List<EDetailingMaterialDto> eDetailingDto = getEDetailingByPhysicianId(physicianId);
		List<EDetailingMaterialEntity> eDetailingMat = new ArrayList<>();
		int count = 0;
		for(EDetailingMaterialDto eachDto : eDetailingDto){
			String productName = products.getProductById(eachDto.getProductId()).getProductName();
			String nameToShow = "Product "+(++count)+" "+productName;
			UserDto salesRep = alignment.getUserForAlignment(physicianId, eachDto.getProductId());
			eDetailingMat.add(new EDetailingMaterialEntity(
					ConstantValues.EDETAILING_DOCS_PATH,
					productName,
					nameToShow,
					salesRep.getFirstName()+" "+salesRep.getLastName(),
					salesRep.getContactNumber()
					));
		}
		return eDetailingMat;
	}
	
	@Override
	public List<Integer> getPhysiciansInEdetailingForMedicalField(String medicalFieldId){
		return eDetailingDao.getPhysicians(medicalFieldId);
	}

}