package com.stm.salesfast.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class PatientSampleFeedbackDto {
	private int patientSameplFeedbackId;
	private boolean isMedicineEffective;
	private boolean hasSideEffects;
	private boolean isAffordable;
	private String sideEffectsComments;
	private String otherComments;
	private int productId;
	
	public PatientSampleFeedbackDto(boolean isMedicineEffective, boolean hasSideEffects, boolean isAffordable, String sideEffectComments, String otherComments, int productId){
		this.isMedicineEffective = isMedicineEffective;
		this.hasSideEffects = hasSideEffects;
		this.isAffordable = isAffordable;
		this.sideEffectsComments = sideEffectComments;
		this.otherComments = otherComments;
		this.productId = productId;
	}
}
