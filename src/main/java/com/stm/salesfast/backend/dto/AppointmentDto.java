package com.stm.salesfast.backend.dto;

import java.sql.Date;
import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentDto {
	private int appointmnetId;
	private Time time;
	private Date date;
	private int physicianId;
	private int userId;
	private int productId;
	private String confirmationStatus;
	
	@Override
	public String toString(){
		return "Appointment Id : "+appointmnetId+" | Physician Id : "+physicianId+" | User Id : "+userId+" | Time : "+time+" | Date : "+date+" | Product Id : "+productId+" | Confirmation Status: "+confirmationStatus;
	}
}
