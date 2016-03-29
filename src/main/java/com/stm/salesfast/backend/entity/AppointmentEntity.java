package com.stm.salesfast.backend.entity;

import java.sql.Date;
import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.stm.salesfast.backend.dto.AlignmentsDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AppointmentEntity {
	private int appointmentId;
	private int physicianId;
	private String physicianName;
	private String address;
	private String contact;
	private String emailId;
	private String confirmationStatus;
	private Time startTime;
	private Time endTime;
	private Date date;
	private String product;
	private boolean hasMeetingUpdate;
	private boolean hasMeetingExperience;
	private String cancellationReason;
	private String additionalUpdate;
	private boolean hasPitch;
	
}
