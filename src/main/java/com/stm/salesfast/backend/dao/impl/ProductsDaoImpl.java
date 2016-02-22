package com.stm.salesfast.backend.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stm.salesfast.backend.dao.specs.ProductsDao;
import com.stm.salesfast.backend.dto.ProductDto;
import com.stm.salesfast.backend.dto.UserAccountDto;

@Repository
public class ProductsDaoImpl implements ProductsDao{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final String  FETCH_BY_ID = "SELECT * FROM products WHERE productId = ?";
	
	@Override
	public ProductDto getProductById(int productId) {
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

}
