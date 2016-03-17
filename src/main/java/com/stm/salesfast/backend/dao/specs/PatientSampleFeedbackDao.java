package com.stm.salesfast.backend.dao.specs;


import java.util.List;

import com.stm.salesfast.backend.dto.PatientSampleFeedbackDto;


public interface PatientSampleFeedbackDao {
	
	public void insert(PatientSampleFeedbackDto sampleFeedback);

	public int countMedEfficiency(int productId);

	public	int countIsAffordable(int productId);

	public	int countHasSideeffects(int productId);

	public List<String> sideEffectComments(int productId);

	public List<String> otherComments(int productId);

	public int countAll(int productId);
}
