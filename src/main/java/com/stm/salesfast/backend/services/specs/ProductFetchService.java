package com.stm.salesfast.backend.services.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.ProductDto;
import com.stm.salesfast.backend.entity.MedicalFieldEntity;
import com.stm.salesfast.backend.entity.NewProductEntity;
import com.stm.salesfast.backend.entity.ProductEntity;

public interface ProductFetchService {
	
	public ProductDto getProductById(int productId);
	
	public ProductDto getProductByName(String productName);
	
	public List<ProductDto> getProductByMedicalField(String medicalFieldId);

	public void insertNewProduct(NewProductEntity newProduct);

	public List<ProductDto> getProductByMedicalField(
			List<MedicalFieldEntity> medicalFields);

	public List<ProductEntity> getAllProducts();

	public String getMedicalFieldForProduct(int productId);
}
