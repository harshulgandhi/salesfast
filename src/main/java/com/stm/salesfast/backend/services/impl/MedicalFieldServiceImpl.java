package com.stm.salesfast.backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.MedicalFieldDao;
import com.stm.salesfast.backend.dto.MedicalFieldDto;
import com.stm.salesfast.backend.services.specs.MedicalFieldService;

@Service
public class MedicalFieldServiceImpl implements MedicalFieldService{

	@Autowired
	MedicalFieldDao medicalFieldDao;
	
	@Override
	public MedicalFieldDto getByMedicalField(String medicalFieldId) {
		// TODO Auto-generated method stub
		return medicalFieldDao.getBy(medicalFieldId);
	}

	@Override
	public List<MedicalFieldDto> getAllMedicalFields() {
		// TODO Auto-generated method stub
		return medicalFieldDao.getAll();
	}
	
}
