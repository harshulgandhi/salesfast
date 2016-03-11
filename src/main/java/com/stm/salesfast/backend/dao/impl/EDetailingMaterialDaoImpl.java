package com.stm.salesfast.backend.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stm.salesfast.backend.dao.specs.EDetailingMaterialDao;
import com.stm.salesfast.backend.dto.EDetailingMaterialDto;

@Repository
public class EDetailingMaterialDaoImpl implements EDetailingMaterialDao {

	private Logger log = LoggerFactory.getLogger(EDetailingMaterialDaoImpl.class.getName());

	private static final String FETCH_BY_ID = "SELECT * FROM edetailing_material WHERE eDetailingMaterialId = ?";
	private static final String FETCH_BY_PHYS = "SELECT * FROM edetailing_material WHERE physicianId = ?";
	private static final String INSERT = "INSERT INTO edetailing_material "
			+ "(detailingFileName, physicianId, medicalFieldId, productid) "
			+ "VALUES (?,?,?,?)";
	private static final String FETCH_ALL_PHYS = "SELECT physicianId FROM edetailing_material WHERE medicalFieldId = ? GROUP BY physicianId";
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public EDetailingMaterialDto getById(int eDetailingId) {
		try{
			return jdbcTemplate.queryForObject(FETCH_BY_ID, (rs, rownnum)->{
				return new EDetailingMaterialDto(eDetailingId, rs.getString("detailingFileName"), rs.getInt("physicianId"), rs.getString("medicalFieldId"), rs.getInt("productId"));
			}, eDetailingId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<EDetailingMaterialDto> getByPhysicianId(int physicianId) {
		try{
			return jdbcTemplate.query(FETCH_BY_PHYS, (rs, rownnum)->{
				return new EDetailingMaterialDto(rs.getInt("eDetailingMaterialId"), rs.getString("detailingFileName"), rs.getInt("physicianId"), rs.getString("medicalFieldId"), rs.getInt("productId"));
			}, physicianId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void insert(EDetailingMaterialDto eDetailing) {
		try{
			jdbcTemplate.update(INSERT, (ps)->{
				ps.setString(1, eDetailing.getDetailingFileName());
				ps.setInt(2, eDetailing.getPhysicianId());
				ps.setString(3, eDetailing.getMedicalFieldID());
				ps.setInt(4, eDetailing.getProductId());
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}

	}
	
	@Override
	public List<Integer> getPhysicians(String medicalFieldId) {
		try{
			return jdbcTemplate.query(FETCH_ALL_PHYS, (rs, rownnum)->{
				return rs.getInt("physicianId");
			}, medicalFieldId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

}
