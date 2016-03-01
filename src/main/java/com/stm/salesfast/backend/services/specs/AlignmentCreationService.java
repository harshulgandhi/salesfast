package com.stm.salesfast.backend.services.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.AlignmentsDto;

public interface AlignmentCreationService {
	public List<AlignmentsDto> calculateAlignments();
	public void createAlignments();
	
}
