package com.stm.salesfast.backend.dao.impl;

import java.util.List;

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
	private static final String COUNT_MED_EFFICIENCY = "SELECT COUNT(*) FROM patient_sample_feedback "
			+ "WHERE isMedicineEffective = 1 AND "
			+ "productId = ?";
	
	private static final String COUNT_SIDE_EFFECTS = "SELECT COUNT(*) FROM patient_sample_feedback "
			+ "WHERE hasSideEffects = 1 AND "
			+ "productId = ?"; 
	
	private static final String COUNT_AFFORDABLE = "SELECT COUNT(*) FROM patient_sample_feedback "
			+ "WHERE isAffordable = 1 AND "
			+ "productId = ?"; 
	private static final String FETCH_SIDEEFFECT_COMMENTS = "SELECT sideEffectsComments FROM patient_sample_feedback "
			+ "WHERE productId = ? "
			+ "AND sideEffectsComments <> ''";
	private static final String FETCH_OTHER_COMMENTS = "SELECT otherComments FROM patient_sample_feedback "
			+ "WHERE productId = ? "
			+ "AND otherComments <> ''";
	private static final String COUNT_ALL = "SELECT COUNT(*) FROM patient_sample_feedback "
			+ "WHERE productId = ?";
	
	
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
	
	@Override
	public int countMedEfficiency(int productId){
		try {
			return jdbcTemplate.queryForObject(COUNT_MED_EFFICIENCY, (rs, rownum) -> {
				return rs.getInt(1);
			}, productId);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int countHasSideeffects(int productId){
		try {
			return jdbcTemplate.queryForObject(COUNT_SIDE_EFFECTS, (rs, rownum) -> {
				return rs.getInt(1);
			}, productId);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int countIsAffordable(int productId){
		try {
			return jdbcTemplate.queryForObject(COUNT_AFFORDABLE, (rs, rownum) -> {
				return rs.getInt(1);
			}, productId);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int countAll(int productId){
		try {
			return jdbcTemplate.queryForObject(COUNT_ALL, (rs, rownum) -> {
				return rs.getInt(1);
			}, productId);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public List<String> sideEffectComments(int productId){
		try {
			return jdbcTemplate.query(FETCH_SIDEEFFECT_COMMENTS, (rs, rownum) -> {
				return rs.getString(1);
			}, productId);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<String> otherComments(int productId){
		try {
			return jdbcTemplate.query(FETCH_OTHER_COMMENTS, (rs, rownum) -> {
				return rs.getString(1);
			}, productId);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}
