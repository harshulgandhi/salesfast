package com.stm.salesfast.backend.services.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.PhysicianStgDto;
import com.stm.salesfast.backend.entity.PhysicianAppointmentCancellationEntity;

public interface PhysicianFetchService {

	public PhysicianStgDto getPhysicianById(int physicianId);
	
	public String getPhysicianZipById(int physId);
	
	
	public List<PhysicianStgDto> getAllPhysicians();

	public void updateImportanceFactor(double importanceFactor, int physicianId);

	PhysicianAppointmentCancellationEntity getAppointmentDetailForPhysician(int appointmentId);

	void updatePhysicianStatus(int physicianId, String status);
	
	public String getPhysicianName(int physicianId);

	public int getPhysicianIdByName(String firstName, String lastName, String email);
}
