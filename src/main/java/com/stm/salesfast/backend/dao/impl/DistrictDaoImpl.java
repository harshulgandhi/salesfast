package com.stm.salesfast.backend.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stm.salesfast.backend.dao.specs.DistrictDao;
import com.stm.salesfast.backend.dto.DistrictsDto;

@Repository
public class DistrictDaoImpl implements DistrictDao{

	private Logger log = LoggerFactory.getLogger(DistrictDaoImpl.class.getName());

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final String  FETCH_BY_ID = "SELECT * FROM districts WHERE districtId = ?";
	private static final String  FETCH_BY_USER = "SELECT * FROM districts WHERE userId = ?";
	
	@Override
	public DistrictsDto getBy(int districtId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(
					FETCH_BY_ID, (rs, rownum) -> {
						return new DistrictsDto(districtId, rs.getString("districtName"), rs.getString("state"),rs.getInt("userId"));
					}, districtId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public DistrictsDto getByUser(int userId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(
					FETCH_BY_ID, (rs, rownum) -> {
						return new DistrictsDto(rs.getInt("districtId"), rs.getString("districtName"), rs.getString("state"),userId);
					}, userId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

}
