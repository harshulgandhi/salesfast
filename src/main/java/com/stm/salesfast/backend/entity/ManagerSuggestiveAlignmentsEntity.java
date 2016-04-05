package com.stm.salesfast.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ManagerSuggestiveAlignmentsEntity {
	private int alignmentId;
	private int physicianId;
	private String physicianName;
	private String physicianAddressLineOne;
	private String physicianAddressLineTwo;
	private String physicianCity;
	private String physicianState;
	private String physicianZip;
	private String phone;
	private String email;
	private int productId;
	private String productName;
	private boolean isPhysicianNew;
	private double importanceFactor;
	private int salesRepId;
	private String salesRepName;
}
