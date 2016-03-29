package com.stm.salesfast.backend.dto;

import java.sql.Time;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AppointmentDto {
	private int appointmnetId;
	private Time startTime;
	private Time endTime;
	private Date date;
	private int physicianId;
	private int userId;
	private int productId;
	private String confirmationStatus;
	private String zip;
	private String cancellationReason;
	private String additionalNotes;
	private boolean hasMeetingUpdate;
	private boolean hasMeetingExperienceFromSR;
	private boolean hasMeetingExperienceFromPH;
	private boolean hasPitch;

	public AppointmentDto(Time startTime, Time endTime, Date currentDate, int physId,
			int userId2, int productId2, String confirmationStatus2, String zip, String cancellationReason, String additionalNotes, boolean hasMeetingExperienceFromSR, boolean hasMeetingUpdate, boolean hasMeetingExperienceFromPH, boolean hasPitch) {
		// TODO Auto-generated constructor stub
		this.startTime = startTime;
		this.endTime = endTime;
		this.date = currentDate;
		this.physicianId = physId;
		this.userId = userId2;
		this.productId = productId2;
		this.confirmationStatus = confirmationStatus2;
		this.zip = zip;
		this.cancellationReason = cancellationReason;
		this.additionalNotes = additionalNotes;
		this.hasMeetingExperienceFromSR = false;
		this.hasMeetingUpdate = false;
		this.hasMeetingExperienceFromPH = false;
		this.hasPitch = hasPitch;
	}
}
