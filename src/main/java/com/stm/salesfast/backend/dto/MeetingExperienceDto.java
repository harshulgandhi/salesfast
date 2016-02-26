package com.stm.salesfast.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
@Data
public class MeetingExperienceDto {
	
	
	private int meetingExperienceId;
	private boolean isPhysicianEntry;
	private boolean isSalesRepEntry;
	private String status;
	private boolean likedTheProduct;
	private boolean likedPriceAffordability;
	private boolean  impressiveLessSideEffects;
	private boolean likedPresentation;
	private boolean salesRepConfidence;
	private boolean impressiveCompanyReputation;
	private int appointmentId;
	
	public MeetingExperienceDto(boolean boolean1, boolean boolean2,
			String string, boolean boolean3, boolean boolean4,
			boolean boolean5, boolean boolean6, boolean boolean7,
			boolean boolean8, int appointmentId2) {
		
		this.isPhysicianEntry = boolean1;
		this.isSalesRepEntry = boolean2;
		this.status = string;
		this.likedTheProduct = boolean3;
		this.likedPriceAffordability = boolean4;
		this.impressiveLessSideEffects = boolean5;
		this.likedPresentation = boolean6;
		this.salesRepConfidence = boolean7;
		this.impressiveCompanyReputation = boolean8;
		this.appointmentId = appointmentId2;
		
	}
}
