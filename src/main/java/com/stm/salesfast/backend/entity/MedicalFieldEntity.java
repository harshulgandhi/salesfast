package com.stm.salesfast.backend.entity;

import java.sql.Date;
import java.sql.Time;

import com.stm.salesfast.backend.dto.MedicalFieldDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;



@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class MedicalFieldEntity {
	
	private String medicalFieldId;
	private String medicalFieldName;
	public MedicalFieldEntity(MedicalFieldDto eachMedField) {
		this.medicalFieldId = eachMedField.getMedicalFieldId();
		this.medicalFieldName = eachMedField.getMedicalFieldName();
	}
	
}
