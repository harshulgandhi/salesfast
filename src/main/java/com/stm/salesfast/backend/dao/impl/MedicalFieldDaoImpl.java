package com.stm.salesfast.backend.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stm.salesfast.backend.dao.specs.MedicalFieldDao;
import com.stm.salesfast.backend.dto.MedicalFieldDto;

@Repository
public class MedicalFieldDaoImpl implements MedicalFieldDao{
	private Logger log = LoggerFactory.getLogger(ProductsDaoImpl.class.getName());

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final String  FETCH_BY_ID = "SELECT * FROM medical_fields WHERE productId = ?";
	private static final String  FETCH_ALL = "SELECT * FROM medical_fields";
	
	@Override
	public MedicalFieldDto getBy(String medicalFieldId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(
					FETCH_BY_ID, (rs, rownum) -> {
						return new MedicalFieldDto(medicalFieldId, rs.getString("medicalFieldName"));}
					, medicalFieldId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<MedicalFieldDto> getAll() {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(
					FETCH_ALL, (rs, rownum) -> {
						return new MedicalFieldDto(rs.getString("medicalFieldId"), rs.getString("medicalFieldName"));
						});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

}
