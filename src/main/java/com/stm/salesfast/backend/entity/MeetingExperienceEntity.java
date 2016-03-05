package com.stm.salesfast.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class MeetingExperienceEntity {

	private int appointmentId;
	private boolean likedTheProduct;
	private boolean likedPriceAffordability;
	private boolean  impressiveLessSideEffects;
	private boolean likedPresentation;
	private boolean salesRepConfidence;
	private boolean impressiveCompanyReputation;
	
	
	@Override
	public String toString(){
		return "Appointment Id : "+appointmentId+"\nlikedTheProduct : "+(likedTheProduct)+"\n likedPriceAffordability : "+likedPriceAffordability+"\n impressiveLessSideEffects : "+impressiveLessSideEffects+"\n likedPresentation : "+(likedPresentation)+"\n salesRepConfidence : "+(salesRepConfidence)+"\n impressiveCompanyReputation : "+(impressiveCompanyReputation);
	}
}
