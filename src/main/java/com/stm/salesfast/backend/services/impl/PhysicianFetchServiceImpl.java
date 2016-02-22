package com.stm.salesfast.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.PhysicianStgDao;
import com.stm.salesfast.backend.dto.PhysicianStgDto;
import com.stm.salesfast.backend.services.specs.PhysicianFetchService;

@Service
public class PhysicianFetchServiceImpl implements PhysicianFetchService {

	@Autowired
	PhysicianStgDao physicianDao;
	
	@Override
	public PhysicianStgDto getPhysicianById(int physicianId) {
		// TODO Auto-generated method stub
		return physicianDao.getBy(physicianId);
	}

}
