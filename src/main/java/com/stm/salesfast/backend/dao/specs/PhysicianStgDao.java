package com.stm.salesfast.backend.dao.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.PhysicianStgDto;

public interface PhysicianStgDao {
	public PhysicianStgDto getBy(int physicianId);
	
	public void insert(PhysicianStgDto physician);
	
	public void deleteBy(int physicianId);
	
	public String getZipById(int physicianId);
	
	public List<PhysicianStgDto> getAll();

	public void updateImportance(double importanceFactor, int physicianId);

	void updateStatus(int physicianId, String status);
}
