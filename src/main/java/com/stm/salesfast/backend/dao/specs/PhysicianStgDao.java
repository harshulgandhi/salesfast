package com.stm.salesfast.backend.dao.specs;

import com.stm.salesfast.backend.dto.PhysicianStgDto;

public interface PhysicianStgDao {
	public PhysicianStgDto getBy(int physicianId);
	
	public void insert(PhysicianStgDto physician);
	
	public void deleteBy(int physicianId);
}
