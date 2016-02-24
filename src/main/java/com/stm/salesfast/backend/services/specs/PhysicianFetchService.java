package com.stm.salesfast.backend.services.specs;

import com.stm.salesfast.backend.dto.PhysicianStgDto;
import com.stm.salesfast.backend.entity.PhysicianAppointmentEntity;

public interface PhysicianFetchService {

	public PhysicianStgDto getPhysicianById(int physicianId);
	
	public String getPhysicianZipById(int physId);
	
	public PhysicianAppointmentEntity getAppointmentDetailForPhysician(String username, int appointmentId);
}
