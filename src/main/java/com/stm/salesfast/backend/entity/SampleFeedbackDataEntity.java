package com.stm.salesfast.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SampleFeedbackDataEntity {
	private double isMedicineEffective;
	private double hasSideEffects;
	private double isAffordable;
}
