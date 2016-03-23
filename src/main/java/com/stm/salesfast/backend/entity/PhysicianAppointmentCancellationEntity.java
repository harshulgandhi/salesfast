package com.stm.salesfast.backend.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class PhysicianAppointmentCancellationEntity {

	private int appointmentId;
	private String salesRepName;
	private String organisationName;
	private String product;
	private String startTime;
	private String endTime;
	private String status;
}
