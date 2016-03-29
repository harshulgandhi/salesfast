package com.stm.salesfast.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class PitchesDto {
	private int pitchesId;
	private int appointmentId;
	private String meetingStatus;
	private String fileName;
	private int pitchScore; 
	
	public PitchesDto(int appointmentId, String status, String fileName, int pitchScore){
		this.appointmentId = appointmentId;
		this.meetingStatus = status;
		this.fileName = fileName;
		this.pitchScore = pitchScore;
	}
}
