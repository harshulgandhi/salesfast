package com.stm.salesfast.backend.services.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.ProductDto;
import com.stm.salesfast.backend.entity.SampleFeedbackDataEntity;


public interface SampleFeedbackAnalysisService {
	
	
	public List<ProductDto> getProductsForUser(int userId);
	
	public List<SampleFeedbackDataEntity> analyseFeedbackData();

	public List<SampleFeedbackDataEntity> getFeedbackAnaysis(List<ProductDto> products);
}
