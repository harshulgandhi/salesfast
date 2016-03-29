package com.stm.salesfast.backend.entity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ViewAllPitchEntity {
	private int pitchId;
	private int appointmentId;
	private String meetingStatus;
	private String salesRepName;
	private String physicianName;
	private Date date;
	private String productName;
}
