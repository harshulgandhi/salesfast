package com.stm.salesfast.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class MeetingUpdateEntity {

	private int appointmentId;
	private int physicianId;
	private String productName;
	private String meetingStatus;
	private String isEDetailing;
	
}
