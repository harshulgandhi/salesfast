package com.stm.salesfast.backend.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.EDetailingMaterialDao;
import com.stm.salesfast.backend.dto.EDetailingMaterialDto;
import com.stm.salesfast.backend.entity.EDetailingMaterialEntity;
import com.stm.salesfast.backend.services.specs.EDetailingMaterialService;
import com.stm.salesfast.backend.services.specs.ProductFetchService;
import com.stm.salesfast.constant.ConstantValues;

@Service
public class EDetailingMaterialServiceImpl implements EDetailingMaterialService {

	@Autowired
	EDetailingMaterialDao eDetailingDao;
	
	@Autowired
	ProductFetchService products;
	
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
	public List<EDetailingMaterialEntity> getEDetailingMaterialForUI(
			int physicianId) {
		// TODO Auto-generated method stub
		List<EDetailingMaterialDto> eDetailingDto = getEDetailingByPhysicianId(physicianId);
		List<EDetailingMaterialEntity> eDetailingMat = new ArrayList<>();
		int count = 0;
		for(EDetailingMaterialDto eachDto : eDetailingDto){
			String productName = products.getProductById(eachDto.getProductId()).getProductName();
			String nameToShow = "Product "+(++count)+" "+productName;
			eDetailingMat.add(new EDetailingMaterialEntity(
					ConstantValues.EDETAILING_DOCS_PATH,
					productName,
					nameToShow,
					
					
					));
		}
		
		return null;
	}

}
