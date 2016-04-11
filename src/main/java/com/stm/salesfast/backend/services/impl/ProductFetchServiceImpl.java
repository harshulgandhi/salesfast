package com.stm.salesfast.backend.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.ProductsDao;
import com.stm.salesfast.backend.dto.ProductDto;
import com.stm.salesfast.backend.entity.MedicalFieldEntity;
import com.stm.salesfast.backend.entity.NewProductEntity;
import com.stm.salesfast.backend.entity.ProductEntity;
import com.stm.salesfast.backend.services.specs.ProductFetchService;

@Service
public class ProductFetchServiceImpl implements ProductFetchService {

	@Autowired
	ProductsDao productDao;
	
	@Override
	public ProductDto getProductById(int productId) {
		return productDao.getProduct(productId);
	}

	@Override
	public ProductDto getProductByName(String productName) {
		return productDao.getProduct(productName);
	}

	@Override
	public List<ProductDto> getProductByMedicalField(String medicalFieldId) {
		return productDao.getProductForMedicalField(medicalFieldId);
	}
	
	@Override
	public List<ProductDto> getProductByMedicalField(List<MedicalFieldEntity> medicalFields) {
		List<ProductDto> products = new ArrayList<>();
		for(MedicalFieldEntity eachMedicalField : medicalFields){
			products.addAll(productDao.getProductForMedicalField(eachMedicalField.getMedicalFieldId()));
		}
		return products;
	}

	@Override
	public void insertNewProduct(NewProductEntity newProduct){
		productDao.insert(new ProductDto(
				newProduct.getProductName(),
				newProduct.getReleaseDate(),
				newProduct.getMedicalFieldId(),
				newProduct.getTypeOfProduct(),
				newProduct.isHasLessPrice(),
				newProduct.isHasLessSideEffects(),
				newProduct.getImprovedOverProduct()
				));
	}
	
	@Override
	public List<ProductEntity> getAllProducts(){
		List<ProductDto> products = productDao.getAll();
		List<ProductEntity> allProducts = new ArrayList<>();
		for(ProductDto product : products){
			allProducts.add(new ProductEntity(
					product.getProductId(),
					product.getProductName(),
					product.getMedicalFieldId(),
					product.getReleaseDate()));
		}
		return allProducts;
	}
	
	@Override
	public String getMedicalFieldForProduct(int productId){
		return productDao.getMedicalField(productId);
	}
	
}
