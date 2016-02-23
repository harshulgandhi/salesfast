package com.stm.salesfast.backend.services.specs;

import com.stm.salesfast.backend.dto.PhysicianStgDto;

public interface PhysicianFetchService {

	public PhysicianStgDto getPhysicianById(int physicianId);
	
	public String getPhysicianZipById(int physId);
}
