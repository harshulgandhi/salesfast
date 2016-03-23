package com.stm.salesfast.backend.services.specs;

import java.util.List;

import com.stm.salesfast.backend.entity.ManagerProductViewEntity;

public interface ManagerViewProductService {
	public List<ManagerProductViewEntity> getDocuments(int productId); 
}
