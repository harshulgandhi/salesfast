package com.stm.salesfast.backend.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stm.salesfast.backend.dao.specs.PatientSampleFeedbackDao;
import com.stm.salesfast.backend.dto.PatientSampleFeedbackDto;

@Repository
public class PatientSampleFeedbackDaoImpl implements PatientSampleFeedbackDao{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final String INSERT = "INSERT INTO patient_sample_feedback "
			+ "(isMedicineEffective, hasSideEffects, isAffordable, sideEffectsComments, otherComments, productId) "
			+ "VALUES (?,?,?,?,?,?)";
	
	@Override
	public void insert(PatientSampleFeedbackDto sampleFeedback) {
		try{
			jdbcTemplate.update(INSERT, (ps)->{
				ps.setBoolean(1, sampleFeedback.isMedicineEffective());
				ps.setBoolean(2, sampleFeedback.isHasSideEffects());
				ps.setBoolean(3, sampleFeedback.isAffordable());
				ps.setString(4, sampleFeedback.getSideEffectsComments());
				ps.setString(5, sampleFeedback.getOtherComments());
				ps.setInt(6, sampleFeedback.getProductId());
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
	}

}
