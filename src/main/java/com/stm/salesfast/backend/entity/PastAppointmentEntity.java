package com.stm.salesfast.backend.entity;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class PastAppointmentEntity {
	private int physicianId;
	private String physicianName;
	private int productId;
	private String productName;
	private Date date;
	private String meetingStatus;
	private List<String> reasonsBySalesRep;
	private List<String> reasonsByPhysician;
	private PitchViewEntity pitch;
}
