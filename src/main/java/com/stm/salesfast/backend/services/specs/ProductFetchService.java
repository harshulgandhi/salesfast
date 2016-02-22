package com.stm.salesfast.backend.services.specs;

import com.stm.salesfast.backend.dto.ProductDto;

public interface ProductFetchService {
	
	public ProductDto getProductById(int productId);
}
