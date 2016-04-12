package com.stm.salesfast.backend.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stm.salesfast.backend.dao.specs.TerritoryDao;
import com.stm.salesfast.backend.dto.ProductDto;
import com.stm.salesfast.backend.dto.TerritoryDto;
import com.stm.salesfast.backend.dto.UserDto;
import com.stm.salesfast.backend.entity.AssignedSalesRepInfoEntity;

@Repository
public class TerritoryDaoImpl implements TerritoryDao{
	
	private Logger log = LoggerFactory.getLogger(TerritoryDaoImpl.class.getName());

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final String  FETCH_BY_ID = "SELECT * FROM territories WHERE territoryId = ?";
	private static final String  FETCH_BY_ZIP = "SELECT * FROM territories WHERE zip = ?";
	private static final String FETCH_BY_USER = "SELECT * FROM territories WHERE userID = ?";
	private static final String FETCH_SALESREPS_FOR_DM = "SELECT userWithMedField.*, products.productId, "
			+ "products.productName FROM (SELECT training_material.medicalFieldId, "
			+ "filteredUser.* FROM ( SELECT user.* FROM territories, user WHERE "
			+ "territories.districtId IN (SELECT districtId FROM districts WHERE "
			+ "userId = ?) AND territories.userId = user.userId  GROUP BY userId) "
			+ "as filteredUser, training_material WHERE filteredUser.userId = "
			+ "training_material.userId GROUP BY filteredUser.userId) as userWithMedField, "
			+ "products WHERE userWithMedField.medicalFieldId = products.medicalFieldId "
			+ "ORDER BY userWithMedField.userId ASC";
	
	@Override
	public TerritoryDto getBy(int territoryId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(
					FETCH_BY_ID, (rs, rownum) -> {
						return new TerritoryDto(territoryId, rs.getString("territoryName"), rs.getString("zip"),rs.getInt("userId"),rs.getInt("districtId"));}
					, territoryId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public TerritoryDto getBy(String zip) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(
					FETCH_BY_ZIP, (rs, rownum) -> {
						return new TerritoryDto(rs.getInt("territoryId"), rs.getString("territoryName"), zip,rs.getInt("userId"),rs.getInt("districtId"));}
					, zip);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<TerritoryDto> getByUser(int userId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(
					FETCH_BY_USER, (rs, rownum) -> {
						return new TerritoryDto(rs.getInt("territoryId"), rs.getString("territoryName"), rs.getString("zip"),userId,rs.getInt("districtId"));}
					, userId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<AssignedSalesRepInfoEntity> getSalesRepsByDM(int userId){
		try{
			return jdbcTemplate.query(
					FETCH_SALESREPS_FOR_DM, (rs, rownum) -> {
						return new AssignedSalesRepInfoEntity(rs.getInt("userId"), rs.getString("firstName"), rs.getString("lastName"),rs.getString("email"),rs.getString("contactNumber"),rs.getString("addressLineOne"),rs.getString("addressLineTwo"),rs.getString("city"),rs.getString("state"),rs.getString("zip"), rs.getDate("startDate"), rs.getDate("endDate"), rs.getString("medicalFieldId"), rs.getInt("productId"), rs.getString("productName"));}
					, userId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
		
	}
}
