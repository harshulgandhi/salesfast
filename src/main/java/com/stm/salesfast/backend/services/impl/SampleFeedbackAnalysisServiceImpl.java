package com.stm.salesfast.backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.stm.salesfast.backend.dto.ProductDto;
import com.stm.salesfast.backend.entity.SampleFeedbackDataEntity;
import com.stm.salesfast.backend.services.specs.ProductFetchService;
import com.stm.salesfast.backend.services.specs.SampleFeedbackAnalysisService;
import com.stm.salesfast.backend.services.specs.TrainingMaterialService;
import com.stm.salesfast.backend.services.specs.UserToRoleService;
import com.stm.salesfast.constant.SessionConstants;


public class SampleFeedbackAnalysisServiceImpl implements SampleFeedbackAnalysisService{

	@Autowired
	UserToRoleService userRoleService;
	
	@Autowired
	TrainingMaterialService trainingMaterialService;
	
	@Autowired
	ProductFetchService productService;
	
	@Override
	public List<SampleFeedbackDataEntity> getFeedbackAnaysis(List<ProductDto> products) {
		return null;
	}

	@Override
	public List<ProductDto> getProductsForUser(int userId) {
		List<String> roles = userRoleService.getAllRolesForUser(userId);
		if(roles.contains("SalesRep")){
			String medicalField = trainingMaterialService.getMedicalFieldForUser(userId);
			List<ProductDto> products = productService.getProductByMedicalField(medicalField);
		}
		return null;
	}

	@Override
	public List<SampleFeedbackDataEntity> analyseFeedbackData() {
		int userId = SessionConstants.USER_ID;
		List<ProductDto> products = getProductsForUser(userId);
		return null;
	}
}
