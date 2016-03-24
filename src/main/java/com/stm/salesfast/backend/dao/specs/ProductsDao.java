package com.stm.salesfast.backend.dao.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.ProductDto;

public interface ProductsDao {

	public ProductDto getProduct(int productId);
	public ProductDto getProduct(String productName);
	public List<ProductDto> getProductForMedicalField(String medicalFieldId);
	public void insert(ProductDto product);
	public List<ProductDto> getAll();
	
}
