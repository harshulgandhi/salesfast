package com.stm.salesfast.backend.services.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.EDetailingMaterialDto;
import com.stm.salesfast.backend.entity.EDetailingMaterialEntity;

public interface EDetailingMaterialService {
	
	public EDetailingMaterialDto getEDetailingById(int eDetailingId);
	
	public List<EDetailingMaterialDto> getEDetailingByPhysicianId(int physicianId);
	
	public void insert(EDetailingMaterialDto eDetailing);
	
	public List<EDetailingMaterialEntity> getEDetailingMaterialForUI(int physicianId);

	List<Integer> getPhysiciansInEdetailingForMedicalField(String medicalFieldId);
}
