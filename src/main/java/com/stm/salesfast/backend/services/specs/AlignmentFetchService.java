package com.stm.salesfast.backend.services.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.AlignmentsDto;
import com.stm.salesfast.backend.dto.PhysicianStgDto;

public interface AlignmentFetchService {
	
	public List<AlignmentsDto> getAlignmentByUserId(int userId);
	public AlignmentsDto getAlignmentByPhysicianId(int physicianId);
	public List<AlignmentsDto> getAlignmentByUserIdZip(int userId, String zip);
	
	public List<PhysicianStgDto> getAlignmentByUserIdToShow(int userId);
	public PhysicianStgDto getAlignmentByPhysicianIdToShow(int physicianId);
	public List<PhysicianStgDto> getAlignmentByUserIdToShow(int userId, String zip);
	
	public int getAlignedProduct(int userId, int physicianId);
	public List<PhysicianStgDto> getAlignmentByUserIdInVicinityOfAppointments(int userId);
	
}
