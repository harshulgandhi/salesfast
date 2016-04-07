package com.stm.salesfast.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class MeetingExperienceDetailedDataEntity {
	private boolean isPhysicianEntry;
	private boolean isSalesRepEntry;
	private String status;
	private boolean likedTheProduct;
	private boolean likedPriceAffordability;
	private boolean impressiveLessSideEffects;
	private boolean likedPresentation;
	private boolean salesRepConfidence;
	private boolean impressiveCompanyReputation;
	private int appointmentId;
	private int userId;
	private String salesRepFirstName;
	private String salesRepLastName;
	private int productId;
	private String productName;
	private String medicalFieldId;
	
}
