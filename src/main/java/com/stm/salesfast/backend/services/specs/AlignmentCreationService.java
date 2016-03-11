package com.stm.salesfast.backend.services.specs;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.stm.salesfast.backend.dto.AlignmentsDto;

public interface AlignmentCreationService {
	public List<AlignmentsDto> calculateAlignments();
	public void createAlignments() throws IOException;
	public void calculatePhysicianImportance() throws ParseException;
	void getDataForProcessing();
}
