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
	private String releaseDate;
	private String medicalFieldId;		
}
