package com.stm.salesfast.backend.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.MedicalFieldDao;
import com.stm.salesfast.backend.dto.MedicalFieldDto;
import com.stm.salesfast.backend.entity.MedicalFieldEntity;
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
	public List<MedicalFieldEntity> getAllMedicalFields() {
		// TODO Auto-generated method stub
		List<MedicalFieldDto> medicalFieldDto = medicalFieldDao.getAll();
		List<MedicalFieldEntity> medicalFieldEntities = new ArrayList<>();
		for(MedicalFieldDto eachMedField : medicalFieldDto){
			medicalFieldEntities.add(new MedicalFieldEntity(eachMedField));
		}
		return medicalFieldEntities;
	}
	
	@Override
	public List<String> getAllMedicalFieldNames(){
		List<MedicalFieldEntity> medicalFields = getAllMedicalFields();
		List<String> medicalFieldNames = new ArrayList<>();
		for(MedicalFieldEntity eachMedField : medicalFields ){
			medicalFieldNames.add(eachMedField.getMedicalFieldName());
		}
		return medicalFieldNames;
	}
}
