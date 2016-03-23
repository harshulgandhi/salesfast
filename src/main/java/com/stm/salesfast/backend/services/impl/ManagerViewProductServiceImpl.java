package com.stm.salesfast.backend.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.entity.ManagerProductViewEntity;
import com.stm.salesfast.backend.services.specs.EDetailingMaterialService;
import com.stm.salesfast.backend.services.specs.ManagerViewProductService;
import com.stm.salesfast.backend.services.specs.TrainingMaterialService;
import com.stm.salesfast.constant.ConstantValues;

@Service
public class ManagerViewProductServiceImpl implements ManagerViewProductService{
	
	@Autowired
	EDetailingMaterialService detailingService;
	
	@Autowired
	TrainingMaterialService trainingMatService;
	
	@Override
	public List<ManagerProductViewEntity> getDocuments(int productId) {
		List<ManagerProductViewEntity> filesForProduct = new ArrayList<>(); 
		String detailingFileName = detailingService.getDetailingFileName(productId);
		String trainingFileName = trainingMatService.getTrainingMaterialUrlForProduct(productId);
		filesForProduct.add(new ManagerProductViewEntity(
				ConstantValues.EDETAILING_DOCS_PATH+detailingFileName,
				"e-Detailing File (for Physician)"
				));
		filesForProduct.add(new ManagerProductViewEntity(
				ConstantValues.TRAINING_MATERIAL_PATH+trainingFileName,
				"Virtual Learning File (for SalesReps)"
				));
		filesForProduct.add(new ManagerProductViewEntity(
				"SampleReport",
				"Sample Medicine Feedback Analysis"
				));
		
		return filesForProduct;
	}

}
