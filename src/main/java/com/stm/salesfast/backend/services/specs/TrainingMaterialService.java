package com.stm.salesfast.backend.services.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.TrainingMaterialDto;

public interface TrainingMaterialService {
	
	public TrainingMaterialDto getById(int trainingMaterialId);
	
	public List<TrainingMaterialDto> getByUserId(int userId);
	
	public List<TrainingMaterialDto> getByMedicalField(String medicalFieldId);
	
	public List<String> getUrlsForUser(int userId);
	
	public String getMedicalFieldForUser(int userId);
}
