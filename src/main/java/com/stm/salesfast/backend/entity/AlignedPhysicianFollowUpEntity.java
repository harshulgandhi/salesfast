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
public class AlignedPhysicianFollowUpEntity {
	private int appointmentId;
	private int physicianId;
	private String firstName;
	private String lastName;
	private String email;
	private String contactNumber;
	private String addressLineOne;
	private String addressLineTwo;
	private String city;
	private String state;
	private String zip;
	private String medicalField;
	public boolean isNew;
	private String status;
	private int productId;
	private String productName;
	private double importanceFactor;
	private Date followUpDate;
	private Time  followUpTime;
	private Time followUpEndTime;
	private String additionalNotes;
}
