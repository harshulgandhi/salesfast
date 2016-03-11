package com.stm.salesfast.backend.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stm.salesfast.backend.dao.specs.TrainingMaterialDao;
import com.stm.salesfast.backend.dto.EDetailingMaterialDto;
import com.stm.salesfast.backend.dto.TrainingMaterialDto;

@Repository
public class TrainingMaterialDaoImpl implements TrainingMaterialDao{

	private Logger log = LoggerFactory.getLogger(ProductsDaoImpl.class.getName());

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final String  FETCH_BY_ID = "SELECT * FROM training_material WHERE trainingMaterialId = ?";
	private static final String  FETCH_BY_USERID = "SELECT * FROM training_material WHERE userId = ?";
	private static final String  FETCH_BY_MEDICALFIELD = "SELECT * FROM training_material WHERE medicalFieldId= ?";
	private static final String INSERT = "INSERT INTO training_material (trainingMaterialUrl, userId, medicalFieldId, productId)"
			+ " VALUES (?,?,?,?)";
	
	
	@Override
	public TrainingMaterialDto getBy(int trainingMaterialId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(
					FETCH_BY_ID, (rs, rownum) -> {
						return new TrainingMaterialDto(trainingMaterialId, rs.getString("trainingMaterialUrl"), rs.getInt("userId"),rs.getString("medicalFieldId"), rs.getInt("productId"));}
					, trainingMaterialId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;	
	}

	@Override
	public List<TrainingMaterialDto> getByUserId(int userId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(
					FETCH_BY_USERID, (rs, rownum) -> {
						return new TrainingMaterialDto(rs.getInt("trainingMaterialId"), rs.getString("trainingMaterialUrl"), userId,rs.getString("medicalFieldId"), rs.getInt("productId"))
						;}
					, userId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<TrainingMaterialDto> getByMedicalField(String medicalFieldId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(
					FETCH_BY_MEDICALFIELD, (rs, rownum) -> {
						return new TrainingMaterialDto(rs.getInt("trainingMaterialId"), rs.getString("trainingMaterialUrl"), rs.getInt("userId"),medicalFieldId, rs.getInt("productId"))
						;}
					, medicalFieldId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void insert(TrainingMaterialDto trainingMat) {
		try{
			jdbcTemplate.update(INSERT, (ps)->{
				ps.setString(1, trainingMat.getTrainingMaterialUrl());
				ps.setInt(2, trainingMat.getUserId());
				ps.setString(3, trainingMat.getMedicalFieldId());
				ps.setInt(4, trainingMat.getProductId());
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}

	}

}
