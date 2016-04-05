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
public class ViewAllPitchEntity implements Comparable<ViewAllPitchEntity>{
	private int pitchId;
	private int appointmentId;
	private String meetingStatus;
	private int salesRepId;
	private String salesRepName;
	private int physicianId;
	private String physicianName;
	private Date date;
	private int productId;
	private String productName;
	private String medicalFieldId;
	private String medicalFieldName;
	private String fileLocation;
	private int pitchScore;
	
	@Override
	public int compareTo(ViewAllPitchEntity entity) {
		return (Integer.compare(pitchScore, entity.getPitchScore()) == -1) ? 1 : (Integer.compare(pitchScore, entity.getPitchScore()) == 1) ? -1 : 0 ;
	}
}
