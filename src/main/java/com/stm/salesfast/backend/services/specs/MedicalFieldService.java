package com.stm.salesfast.backend.services.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.MedicalFieldDto;
import com.stm.salesfast.backend.entity.MedicalFieldEntity;

public interface MedicalFieldService {
	public MedicalFieldDto getByMedicalField(String medicalFieldId);
	public List<MedicalFieldEntity> getAllMedicalFields();
	public List<String> getAllMedicalFieldNames();
}
