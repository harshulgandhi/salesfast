package com.stm.salesfast.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AlignmentsViewManager {
	
	private int alignmentId;
	private int physicianId;
	private String physicianName;
	private String physicianAddress;
	private String phone;
	private String email;
	private int productId;
	private String productName;
	private boolean isPhysicianNew;
}
