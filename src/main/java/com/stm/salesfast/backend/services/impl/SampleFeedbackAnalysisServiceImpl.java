package com.stm.salesfast.backend.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dto.ProductDto;
import com.stm.salesfast.backend.entity.MedicalFieldEntity;
import com.stm.salesfast.backend.entity.ProductEntity;
import com.stm.salesfast.backend.entity.SampleFeedbackDataEntity;
import com.stm.salesfast.backend.services.specs.MedicalFieldService;
import com.stm.salesfast.backend.services.specs.ProductFetchService;
import com.stm.salesfast.backend.services.specs.SampleFeedbackAnalysisService;
import com.stm.salesfast.backend.services.specs.SampleFeedbackService;
import com.stm.salesfast.backend.services.specs.TrainingMaterialService;
import com.stm.salesfast.backend.services.specs.UserToRoleService;

@Service
public class SampleFeedbackAnalysisServiceImpl implements SampleFeedbackAnalysisService{
	
	private Logger log = LoggerFactory.getLogger(SampleFeedbackAnalysisServiceImpl.class.getName());

	@Autowired
	UserToRoleService userRoleService;
	
	@Autowired
	TrainingMaterialService trainingMaterialService;
	
	@Autowired
	ProductFetchService productService;
	
	@Autowired
	MedicalFieldService medicalFieldService;
	
	@Autowired
	SampleFeedbackService sampleFeedbackService;
	
	@Override
	public List<SampleFeedbackDataEntity> getCounts(int productId) {
		List<SampleFeedbackDataEntity> sampleFeedbackAnaysisEntities = new ArrayList<>();
		int countAll = sampleFeedbackService.getCountAll(productId);
		int countIsEffective = sampleFeedbackService.getCountIsEffective(productId);
		int countIsAffordable = sampleFeedbackService.getCountIsAffordable(productId);
		int countHasSideEffects= sampleFeedbackService.getCountSideEffects(productId);
		log.info(productId+" ==> countAll : "+countAll+";countIsEffective  : "+countIsEffective +"; countIsAffordable : "+countIsAffordable +"; countHasSideEffects : "+countHasSideEffects);
		sampleFeedbackAnaysisEntities.add(new SampleFeedbackDataEntity(
					countIsEffective*100.0/countAll,
					countHasSideEffects*100.0/countAll,
					countIsAffordable*100.0/countAll
				));
		return sampleFeedbackAnaysisEntities;
	}

	@Override
	public List<ProductEntity> getProductsForUser(int userId) {
		List<String> roles = userRoleService.getAllRolesForUser(userId);
		List<ProductDto> productsDto = new ArrayList<>();
		List<ProductEntity> products = new ArrayList<>();
		if(roles.contains("SalesRep")){
			String medicalField = trainingMaterialService.getMedicalFieldForUser(userId);
			productsDto = productService.getProductByMedicalField(medicalField);
			
			
		}else if(roles.contains("DM") || roles.contains("NH")){
			List<MedicalFieldEntity> medicalField = medicalFieldService.getAllMedicalFields();
			productsDto = productService.getProductByMedicalField(medicalField);
		}
		
		for(ProductDto eachProduct : productsDto){
			products.add(new ProductEntity(eachProduct.getProductId(), eachProduct.getProductName(), eachProduct.getMedicalFieldId()));
		}
		
		return products;
	}

	@Override
	public List<SampleFeedbackDataEntity> analyseFeedbackData(int productId) {
		return getCounts(productId);
	}
}
