package com.stm.salesfast.backend.dao.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.EDetailingMaterialDto;

public interface EDetailingMaterialDao {
	
	public EDetailingMaterialDto getById(int eDetailingId);
	
	public List<EDetailingMaterialDto> getByPhysicianId(int physicianId);
	
	public void insert(EDetailingMaterialDto eDetailing);

	List<Integer> getPhysicians(String medicalFieldId);

	public String getDetailingFileName(int productId);
	
}
