package com.stm.salesfast.backend.dao.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.AlignmentsDto;

public interface AlignmentsDao {
	
	public AlignmentsDto getAlignmentById(int alignmentId);
	
	public AlignmentsDto getAlignmentByPhysicianId(int physicianId);
	
	public List<AlignmentsDto> getAlignmentByUserId(int userId);
	
	public List<AlignmentsDto> getAlignmentByUserIdZip(int userId, String zip);
	
}
