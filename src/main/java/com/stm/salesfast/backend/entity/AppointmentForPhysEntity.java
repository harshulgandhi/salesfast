package com.stm.salesfast.backend.entity;

import java.sql.Date;
import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AppointmentForPhysEntity {

		private int appointmentId;
		private int physicianId;
		private String salesRepName;
		private String contact;
		private String status;
		private Time time;
		private Date date;
		private String product;
		private boolean hasMeetingExperienceFromPH;
		
}
