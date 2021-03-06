package com.stm.salesfast.backend.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class EDetailingMaterialEntity {
	private String filePath;
	private String fileName;
	private String productName;
	private String salesRepName;
	private String salesRepContact;
}