package com.stm.salesfast.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.ProductsDao;
import com.stm.salesfast.backend.dto.ProductDto;
import com.stm.salesfast.backend.services.specs.ProductFetchService;

@Service
public class ProductFetchServiceImpl implements ProductFetchService {

	@Autowired
	ProductsDao productDao;
	
	@Override
	public ProductDto getProductById(int productId) {
		// TODO Auto-generated method stub
		return productDao.getProductById(productId);
	}

}
