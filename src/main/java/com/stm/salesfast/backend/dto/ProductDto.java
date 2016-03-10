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
	public ProductDto(String productName2, Date releaseDate2, String medicalFieldId2) {
		this.productName = productName2;
		this.releaseDate = releaseDate2;
		this.medicalFieldId = medicalFieldId2;
	}
	private int productId;
	private String productName;
	private Date releaseDate;
	private String medicalFieldId;		
}
