package com.stm.salesfast.backend.services.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.TrainingMaterialDto;
import com.stm.salesfast.backend.entity.VirtualLearningEntity;

public interface TrainingMaterialService {
	
	public TrainingMaterialDto getById(int trainingMaterialId);
	
	public List<TrainingMaterialDto> getByUserId(int userId);
	
	public List<TrainingMaterialDto> getByMedicalField(String medicalFieldId);
	
	public List<String> getUrlsForUser(int userId);
	
	public String getMedicalFieldForUser(int userId);

	public List<VirtualLearningEntity> getAllDocumentsPath(int userId);
}
