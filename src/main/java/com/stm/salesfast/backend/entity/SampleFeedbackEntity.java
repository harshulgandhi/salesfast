package com.stm.salesfast.backend.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class SampleFeedbackEntity {
	
	
	private String hasSideEffects;
	private String isAffordable;
	private String isMedicineEffective;
	private String otherComments;
	private int productId;
	private String sideEffectsComments;
	
}
