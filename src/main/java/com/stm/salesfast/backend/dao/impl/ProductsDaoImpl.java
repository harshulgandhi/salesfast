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
	private static final String INSERT = "INSERT INTO products (productName, releaseDate, medicalFieldId, typeOfProduct,"
			+ "isAffordable, hasLessSideEffects, improvedOverProduct) "
			+ "VALUES (?,?,?,?,?,?,?)";
	private static final String FETCH_ALL = "SELECT * FROM products";
	private static final String FETCH_MEDICAL_FIELD = "SELECT medicalFieldId FROM products WHERE productId = ?";
	
	
	@Override
	public ProductDto getProduct(int productId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(
					FETCH_BY_ID, (rs, rownum) -> {
						return new ProductDto(productId, rs.getString("productName"), rs.getDate("releaseDate"),rs.getString("medicalFieldId"), rs.getString("typeOfProduct"), rs.getBoolean("isAffordable"),rs.getBoolean("hasLessSideEffects"), rs.getInt("improvedOverProduct"));}
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
						return new ProductDto(rs.getInt("productId"), productName, rs.getDate("releaseDate"),rs.getString("medicalFieldId"), rs.getString("typeOfProduct"), rs.getBoolean("isAffordable"),rs.getBoolean("hasLessSideEffects"), rs.getInt("improvedOverProduct"));}
					, productName);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;	
	}
	
	@Override
	public String getMedicalField(int productId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(
					FETCH_MEDICAL_FIELD, (rs, rownum) -> {
						return rs.getString(1);}
					, productId);
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
					FETCH_BY_MEDICALFIELD, (rs, rownum) -> {
						return new ProductDto(rs.getInt("productId"), rs.getString("productName"), rs.getDate("releaseDate"),medicalFieldId, rs.getString("typeOfProduct"), rs.getBoolean("isAffordable"),rs.getBoolean("hasLessSideEffects"), rs.getInt("improvedOverProduct"))
						;}
					, medicalFieldId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void insert(ProductDto product){
		try{
			jdbcTemplate.update(INSERT, (ps)->{
				ps.setString(1, product.getProductName());
				ps.setDate(2, product.getReleaseDate());
				ps.setString(3, product.getMedicalFieldId());
				ps.setString(4, product.getTypeOfProduct());
				ps.setBoolean(5, product.isAffordable());
				ps.setBoolean(6, product.isHasLessSideEffects());
				ps.setInt(7, product.getImprovedOverProduct());
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
	}

	@Override
	public List<ProductDto> getAll(){
		try{
			return jdbcTemplate.query(
					FETCH_ALL, (rs, rownum) -> {
						return new ProductDto(rs.getInt("productId"), rs.getString("productName"), rs.getDate("releaseDate"),rs.getString("medicalFieldId"), rs.getString("typeOfProduct"), rs.getBoolean("isAffordable"),rs.getBoolean("hasLessSideEffects"), rs.getInt("improvedOverProduct"));});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
}
