package com.stm.salesfast.backend.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stm.salesfast.backend.controllers.AppointmentController;
import com.stm.salesfast.backend.dao.specs.ProductsDao;
import com.stm.salesfast.backend.dto.ProductDto;
import com.stm.salesfast.backend.dto.UserAccountDto;

@Repository
public class ProductsDaoImpl implements ProductsDao{
	private Logger log = LoggerFactory.getLogger(ProductsDaoImpl.class.getName());

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final String  FETCH_BY_ID = "SELECT * FROM products WHERE productId = ?";
	private static final String  FETCH_BY_NAME = "SELECT * FROM products WHERE productName = ?";
	private static final String  FETCH_BY_MEDICALFIELD = "SELECT * FROM products WHERE medicalFieldId= ?";
	
	@Override
	public ProductDto getProduct(int productId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(
					FETCH_BY_ID, (rs, rownum) -> {
						return new ProductDto(productId, rs.getString("productName"), rs.getString("releaseDate"),rs.getString("medicalFieldId"));}
					, productId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;	
	}

	@Override
	public ProductDto getProduct(String productName) {
		// TODO Auto-generated method stub
		try{
			log.debug("Fetching for product : "+productName);
			return jdbcTemplate.queryForObject(
					FETCH_BY_NAME, (rs, rownum) -> {
						return new ProductDto(rs.getInt("productId"), productName, rs.getString("releaseDate"),rs.getString("medicalFieldId"));}
					, productName);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;	
	}

	@Override
	public List<ProductDto> getProductForMedicalField(String medicalFieldId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(
					FETCH_BY_NAME, (rs, rownum) -> {
						return new ProductDto(rs.getInt("productId"), rs.getString("productName"), rs.getString("releaseDate"),medicalFieldId)
						;}
					, medicalFieldId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

}
