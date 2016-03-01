package com.stm.salesfast.backend.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.TrainingMaterialDao;
import com.stm.salesfast.backend.dto.TrainingMaterialDto;
import com.stm.salesfast.backend.services.specs.TrainingMaterialService;

@Service
public class TrainingMaterialServiceImpl implements TrainingMaterialService{
	private Logger log = LoggerFactory.getLogger(TrainingMaterialServiceImpl.class.getName());

	@Autowired
	TrainingMaterialDao trainingMaterialDao;
	
	@Override
	public TrainingMaterialDto getById(int trainingMaterialId) {
		return trainingMaterialDao.getBy(trainingMaterialId);
	}

	@Override
	public List<TrainingMaterialDto> getByUserId(int userId) {
//		log.info("Fetching training material for user : "+userId);
		List<TrainingMaterialDto> trainingMat = trainingMaterialDao.getByUserId(userId);
//		for(TrainingMaterialDto eachRecord : trainingMat) log.info("Each training record  : "+eachRecord);
		return trainingMat;
	}

	@Override
	public List<TrainingMaterialDto> getByMedicalField(String medicalFieldId) {
		return trainingMaterialDao.getByMedicalField(medicalFieldId);
	}

	@Override
	public List<String> getUrlsForUser(int userId) {
		List<String> trainingUrls = new ArrayList<>();
		List<TrainingMaterialDto> trainingMaterials = getByUserId(userId);
		for(TrainingMaterialDto eachRecord : trainingMaterials){
			trainingUrls.add(eachRecord.getTrainingMaterialUrl());
		}
		return trainingUrls;
	}

	@Override
	public String getMedicalFieldForUser(int userId) {
		// TODO Auto-generated method stub
//		log.info("Getting medical field for user : "+userId);
		if(getByUserId(userId).size() == 0) return null;
		else return getByUserId(userId).get(0).getMedicalFieldId();
	}

}
