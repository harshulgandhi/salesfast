package com.stm.salesfast.backend.dao.specs;

import com.stm.salesfast.backend.dto.ProductDto;

public interface ProductsDao {

	public ProductDto getProductById(int productId);
}
