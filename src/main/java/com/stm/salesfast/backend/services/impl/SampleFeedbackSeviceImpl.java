package com.stm.salesfast.backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.PatientSampleFeedbackDao;
import com.stm.salesfast.backend.dto.PatientSampleFeedbackDto;
import com.stm.salesfast.backend.entity.SampleFeedbackEntity;
import com.stm.salesfast.backend.services.specs.SampleFeedbackService;

@Service
public class SampleFeedbackSeviceImpl implements SampleFeedbackService{

	@Autowired
	PatientSampleFeedbackDao sampleFeedbackDao;
	
	@Override
	public void insertFeedback(SampleFeedbackEntity sampleFeedback) {
		sampleFeedbackDao.insert(new PatientSampleFeedbackDto(
				Boolean.parseBoolean(sampleFeedback.getIsMedicineEffective()),
				Boolean.parseBoolean(sampleFeedback.getHasSideEffects()),
				Boolean.parseBoolean(sampleFeedback.getIsAffordable()),
				sampleFeedback.getSideEffectsComments(),
				sampleFeedback.getOtherComments(),
				sampleFeedback.getProductId()
				));
	}
	
	@Override
	public int getCountAll(int productId){
		return sampleFeedbackDao.countAll(productId);
	}
	
	@Override
	public int getCountSideEffects(int productId){
		return sampleFeedbackDao.countHasSideeffects(productId);
	}
	
	@Override
	public int getCountIsEffective(int productId){
		return sampleFeedbackDao.countIsAffordable(productId);
	}
	
	@Override
	public int getCountIsAffordable(int productId){
		return sampleFeedbackDao.countIsAffordable(productId);
	}
	
	@Override
	public List<String> getSideEffectComments(int productId){
		return sampleFeedbackDao.sideEffectComments(productId);
	}
	
	@Override
	public List<String> getOtherComments(int productId){
		return sampleFeedbackDao.otherComments(productId);
	}

}
