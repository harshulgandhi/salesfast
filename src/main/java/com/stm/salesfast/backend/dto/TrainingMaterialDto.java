package com.stm.salesfast.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class TrainingMaterialDto {
	private int trainingMaterialId;
	private String trainingMaterialUrl;
	private int userId;
	private String medicalFieldId;
	private int productId;
	
}
