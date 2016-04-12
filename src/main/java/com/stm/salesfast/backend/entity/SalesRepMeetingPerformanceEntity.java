package com.stm.salesfast.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class SalesRepMeetingPerformanceEntity {
	private double Lost;
	private double Prescribing;
	private double Not_Interested;
}
