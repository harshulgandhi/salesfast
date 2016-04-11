package com.stm.salesfast.backend.dto;

import java.sql.Date;
import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ProductDto {
	private int productId;
	private String productName;
	private Date releaseDate;
	private String medicalFieldId;		
	private String typeOfProduct;
	private boolean isAffordable;
	private boolean hasLessSideEffects;
	private int improvedOverProduct;
	
	public ProductDto(String productName2, Date releaseDate2, String medicalFieldId2, String typeOfProduct, boolean isAffordable, boolean hasLessSideEffects, int improvedOverProduct) {
		this.productName = productName2;
		this.releaseDate = releaseDate2;
		this.medicalFieldId = medicalFieldId2;
		this.typeOfProduct = typeOfProduct;
		this.isAffordable = isAffordable;
		this.hasLessSideEffects = hasLessSideEffects;
		this.improvedOverProduct = improvedOverProduct;
	}
}
