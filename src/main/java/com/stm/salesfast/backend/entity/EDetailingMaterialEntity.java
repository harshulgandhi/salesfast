package com.stm.salesfast.backend.entity;

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
public class EDetailingMaterialEntity {

	private String filePath;
	private String fileName;
	private String labelToShow;
	private String salesRepName;
	private String salesRepContact;
}
