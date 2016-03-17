package com.stm.salesfast.backend.services.specs;

import java.util.List;

import com.stm.salesfast.backend.entity.SampleFeedbackEntity;

public interface SampleFeedbackService {

	public void insertFeedback(SampleFeedbackEntity sampleFeedback);

	int getCountAll(int productId);

	int getCountSideEffects(int productId);

	int getCountIsEffective(int productId);

	int getCountIsAffordable(int productId);

	List<String> getSideEffectComments(int productId);

	List<String> getOtherComments(int productId);
	
}
