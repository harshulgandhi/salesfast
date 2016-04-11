package com.stm.salesfast.backend.services.specs;

import java.io.IOException;
import java.util.List;

import com.stm.salesfast.backend.dto.ProductDto;
import com.stm.salesfast.backend.entity.ProductEntity;
import com.stm.salesfast.backend.entity.SampleFeedbackDataEntity;
import com.stm.salesfast.backend.entity.SampleSideEffectCommentsEntity;


public interface SampleFeedbackAnalysisService {
	
	
	public List<ProductEntity> getProductsForUser(int userId);
	
	public List<SampleFeedbackDataEntity> getCounts(int productId);

	public List<SampleFeedbackDataEntity> analyseFeedbackData(int productId);

	public List<SampleSideEffectCommentsEntity> getSideEffectTextAnalysis(int productId)
			throws IOException;
}
