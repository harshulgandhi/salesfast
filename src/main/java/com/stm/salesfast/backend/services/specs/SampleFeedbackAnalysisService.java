package com.stm.salesfast.backend.services.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.ProductDto;
import com.stm.salesfast.backend.entity.ProductEntity;
import com.stm.salesfast.backend.entity.SampleFeedbackDataEntity;


public interface SampleFeedbackAnalysisService {
	
	
	public List<ProductEntity> getProductsForUser(int userId);
	
	public List<SampleFeedbackDataEntity> getCounts(int productId);

	public List<SampleFeedbackDataEntity> analyseFeedbackData(int productId);
}
