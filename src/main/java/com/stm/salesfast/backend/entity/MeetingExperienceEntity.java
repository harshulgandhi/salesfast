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
	
}
