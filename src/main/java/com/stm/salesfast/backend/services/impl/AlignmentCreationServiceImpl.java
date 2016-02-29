package com.stm.salesfast.backend.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dto.PhysicianStgDto;
import com.stm.salesfast.backend.dto.UserDto;
import com.stm.salesfast.backend.services.specs.AlignmentCreationService;
import com.stm.salesfast.backend.services.specs.PhysicianFetchService;
import com.stm.salesfast.backend.services.specs.UserDetailService;

@Service
public class AlignmentCreationServiceImpl implements AlignmentCreationService {
	private Logger log = LoggerFactory.getLogger(AlignmentCreationServiceImpl.class.getName());
	
	@Autowired
	PhysicianFetchService physicianFetchService;
	
	@Autowired
	UserDetailService userDetailService;
	
	private List<UserDto> salesReps = new ArrayList<>();
	private List<PhysicianStgDto> physicians = new ArrayList<>();
	
	public AlignmentCreationServiceImpl() {
		// TODO Auto-generated constructor stub
		log.info("Instantiating Automatic Alignment Creator!!");
	}
	
	@Override
	public void createAlignments() {
		// TODO Auto-generated method stub
		salesReps = userDetailService.getAllSalesReps();
		physicians = physicianFetchService.getAllPhysicians();
		
		for(PhysicianStgDto eachPhysician : physicians){
			
		}
	}	

}
