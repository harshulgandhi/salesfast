package com.stm.salesfast.backend.entity;

import java.sql.Date;

import com.stm.salesfast.backend.dto.ProductDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class NewProductEntity {

	private String productName;
	private Date releaseDate;
	private String medicalFieldId;	
	private String typeOfProduct;
	private boolean hasLessPrice;
	private boolean hasLessSideEffects;
	private int improvedOverProduct;
}
