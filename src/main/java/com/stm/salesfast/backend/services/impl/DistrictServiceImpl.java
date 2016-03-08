package com.stm.salesfast.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.DistrictDao;
import com.stm.salesfast.backend.dto.DistrictsDto;
import com.stm.salesfast.backend.services.specs.DistrictService;

@Service
public class DistrictServiceImpl implements DistrictService{

	@Autowired
	DistrictDao districtDao;
	
	@Override
	public DistrictsDto getDistrictById(int districtId) {
		// TODO Auto-generated method stub
		return districtDao.getBy(districtId);
	}

	@Override
	public DistrictsDto getDistrictByManagerId(int userId) {
		// TODO Auto-generated method stub
		return districtDao.getByUser(userId);
	}

}
