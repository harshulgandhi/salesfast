package com.stm.salesfast.backend.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class PitchViewEntity {
	private int pitchId;
	private int appointmentId;
	private String meetingStatus;
	private String fileLocation;
	private int pitchScore; 
}
