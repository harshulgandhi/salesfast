package com.stm.salesfast.backend.services.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.MedicalFieldDto;

public interface MedicalFieldService {
	public MedicalFieldDto getByMedicalField(String medicalFieldId);
	public List<MedicalFieldDto> getAllMedicalFields();
}
