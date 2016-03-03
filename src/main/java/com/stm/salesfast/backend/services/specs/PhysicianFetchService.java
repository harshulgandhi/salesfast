package com.stm.salesfast.backend.services.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.PhysicianStgDto;
import com.stm.salesfast.backend.entity.PhysicianAppointmentEntity;

public interface PhysicianFetchService {

	public PhysicianStgDto getPhysicianById(int physicianId);
	
	public String getPhysicianZipById(int physId);
	
	
	public List<PhysicianStgDto> getAllPhysicians();

	public void updateImportanceFactor(double importanceFactor, int physicianId);

	PhysicianAppointmentEntity getAppointmentDetailForPhysician(int appointmentId);

	void updatePhysicianStatus(int physicianId, String status);
}
