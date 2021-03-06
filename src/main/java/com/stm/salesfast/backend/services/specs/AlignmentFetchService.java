package com.stm.salesfast.backend.services.specs;

import java.io.IOException;
import java.util.List;

import com.stm.salesfast.backend.dto.AlignmentsDto;
import com.stm.salesfast.backend.dto.PhysicianStgDto;
import com.stm.salesfast.backend.dto.UserDto;
import com.stm.salesfast.backend.entity.AlignedPhysicianEntity;
import com.stm.salesfast.backend.entity.ManagerSuggestiveAlignmentsEntity;
import com.stm.salesfast.backend.entity.ManagerViewAlignmentsEntity;

public interface AlignmentFetchService {
	
	public List<AlignmentsDto> getAlignmentByUserId(int userId);
	public AlignmentsDto getAlignmentByPhysicianId(int physicianId);
	public List<AlignmentsDto> getAlignmentByUserIdZip(int userId, String zip);
	
	public List<AlignedPhysicianEntity> getAlignmentByUserIdToShow(int userId);
	public PhysicianStgDto getAlignmentByPhysicianIdToShow(int physicianId);
	public List<PhysicianStgDto> getAlignmentByUserIdToShow(int userId, String zip);
	
	public List<Integer> getAlignedProduct(int userId, int physicianId);
	public List<AlignedPhysicianEntity> getAlignmentByUserIdInVicinityOfAppointments(int userId);
	
	public void insert(AlignmentsDto alignment) throws IOException;
	public UserDto getUserForAlignment(int physicianId, int productId);
	public List<Integer> getUserIdsWorkingInMedicalField(String medicalFieldId);
	public List<ManagerViewAlignmentsEntity> getAlignmentForManagersView(int userId);
	public void deleteAlignmentByDM(int alignmentId);
	public List<ManagerSuggestiveAlignmentsEntity> getAlignmentForSuggestionsManualAlignment(int userId);
}