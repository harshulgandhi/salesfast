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

@Repository
public class TerritoryDaoImpl implements TerritoryDao{
	
	private Logger log = LoggerFactory.getLogger(TerritoryDaoImpl.class.getName());

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final String  FETCH_BY_ID = "SELECT * FROM territories WHERE territoryId = ?";
	private static final String  FETCH_BY_ZIP = "SELECT * FROM territories WHERE zip = ?";
	private static final String FETCH_BY_USER = "SELECT * FROM territories WHERE userID = ?";
	
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
}
