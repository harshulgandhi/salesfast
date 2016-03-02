package com.stm.salesfast.backend.entity;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class PhysicianAppointmentEntity {

	private int appointmentId;
	private String salesRepName;
	private String organisationName;
	private String product;
	private String time;
	private String status;
}
