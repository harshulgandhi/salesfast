package com.stm.salesfast.backend.dao.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.TrainingMaterialDto;

public interface TrainingMaterialDao {
	public TrainingMaterialDto getBy(int trainingMaterialId);
	
	public List<TrainingMaterialDto> getByUserId(int userId);
	
	public List<TrainingMaterialDto> getByMedicalField(String medicalFieldId);

	public void insert(TrainingMaterialDto trainingMat);

	public String getTrainingMaterialForProduct(int productId);
}
