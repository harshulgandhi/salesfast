package com.stm.salesfast.backend.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.TrainingMaterialDao;
import com.stm.salesfast.backend.dto.TrainingMaterialDto;
import com.stm.salesfast.backend.entity.VirtualLearningEntity;
import com.stm.salesfast.backend.services.specs.TerritoryService;
import com.stm.salesfast.backend.services.specs.TrainingMaterialService;
import com.stm.salesfast.backend.services.specs.UserDetailService;
import com.stm.salesfast.constant.ConstantValues;
import com.stm.salesfast.constant.SessionConstants;

@Service
public class TrainingMaterialServiceImpl implements TrainingMaterialService{
	private Logger log = LoggerFactory.getLogger(TrainingMaterialServiceImpl.class.getName());

	@Autowired
	TrainingMaterialDao trainingMaterialDao;
	
	@Autowired
	UserDetailService userDetail;
	
	@Autowired
	TerritoryService terrService;
	
	@Override
	public TrainingMaterialDto getById(int trainingMaterialId) {
		return trainingMaterialDao.getBy(trainingMaterialId);
	}

	@Override
	public List<TrainingMaterialDto> getByUserId(int userId) {
		List<TrainingMaterialDto> trainingMat = trainingMaterialDao.getByUserId(userId);
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
		if(getByUserId(userId).size() == 0) return null;
		else return getByUserId(userId).get(0).getMedicalFieldId();
	}
	
	@Override
	public List<VirtualLearningEntity> getAllDocumentsPath(int userId){
		List<VirtualLearningEntity> allDocumentPaths = new ArrayList<>();
		List<String> trainingMaterial = getUrlsForUser(userId);
		int i = 0;
		for(String eachFile : trainingMaterial){
			allDocumentPaths.add(new VirtualLearningEntity(ConstantValues.TRAINING_MATERIAL_PATH+eachFile, "Medicine "+(++i)+" "+eachFile.replace(".pdf", "")));
		}
		allDocumentPaths.add(new VirtualLearningEntity(ConstantValues.ORGANISATIONAL_DOCS_PATH+ConstantValues.CODE_OF_CONDUCT_FILE,
				"Code of Conduct"));
		allDocumentPaths.add(new VirtualLearningEntity(ConstantValues.ORGANISATIONAL_DOCS_PATH+ConstantValues.GENERAL_GUIDELINES_FILE,
				"General Guidelines"));
		int userIdDM = userDetail.getDistrictManagerId(SessionConstants.USER_ID);
		
		allDocumentPaths.add(new VirtualLearningEntity(ConstantValues.ORGANISATIONAL_DOCS_PATH+ConstantValues.MANAGER_TIPS_FILE.replace(".pdf", "_"+userIdDM+".pdf"), "Your Manager's Tips"));
		
		return allDocumentPaths;
	}

	@Override
	public void insert(TrainingMaterialDto trainingMat){
		trainingMaterialDao.insert(trainingMat);
	}
	
}
