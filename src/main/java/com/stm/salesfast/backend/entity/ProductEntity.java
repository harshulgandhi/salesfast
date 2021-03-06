package com.stm.salesfast.backend.entity;


import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ProductEntity {
	private int productId;
	private String productName;
	private String medicalFieldId;
	private Date releasedOn;
}
