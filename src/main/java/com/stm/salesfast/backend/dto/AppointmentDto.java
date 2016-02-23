package com.stm.salesfast.backend.dto;

import java.sql.Time;
import java.sql.Date;

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
	private String zip;
	
	@Override
	public String toString(){
		return "Appointment Id : "+appointmnetId+" | Physician Id : "+physicianId+" | User Id : "+userId+" | Time : "+time+" | Date : "+date+" | Product Id : "+productId+" | Confirmation Status: "+confirmationStatus+" | ZIP: "+zip;
	}

	public AppointmentDto(Time time2, Date currentDate, int physId,
			int userId2, int productId2, String confirmationStatus2, String zip) {
		// TODO Auto-generated constructor stub
		this.time = time2;
		this.date = currentDate;
		this.physicianId = physId;
		this.userId = userId2;
		this.productId = productId2;
		this.confirmationStatus = confirmationStatus2;
		this.zip = zip;
	}
}
