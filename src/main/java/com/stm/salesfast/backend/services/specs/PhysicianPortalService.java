package com.stm.salesfast.backend.services.specs;

import java.util.List;

import com.stm.salesfast.backend.entity.AppointmentForPhysEntity;

public interface PhysicianPortalService {
	
	public List<AppointmentForPhysEntity> getMeetingsForPhysician(int physicianId);
	
}
