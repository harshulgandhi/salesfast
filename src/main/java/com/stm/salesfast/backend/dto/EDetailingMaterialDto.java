package com.stm.salesfast.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class EDetailingMaterialDto {
	private int eDetailingMaterialId;
	private String detailingFileName;
	private int physicianId;
	private String medicalFieldID;
	private int productId;
	
	public EDetailingMaterialDto(String string, int physicianId2,
			String medicalField, int productId2) {
		this.detailingFileName = string;
		this.physicianId = physicianId2;
		this.medicalFieldID = medicalField;
		this.productId = productId2;
	}
}
