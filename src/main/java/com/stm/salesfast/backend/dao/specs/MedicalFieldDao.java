package com.stm.salesfast.backend.dao.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.MedicalFieldDto;

public interface MedicalFieldDao {

	public MedicalFieldDto getBy(String medicalFieldId);
	public List<MedicalFieldDto> getAll();
}
