package com.stm.salesfast.backend.entity;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.stm.salesfast.backend.dto.AlignmentsDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentEntity {
	private String physicianName;
	private String address;
	private String contact;
	private String emailId;
	private String confirmationStatus;
	private Time time;
	private String product;
	
	@Override
	public String toString(){
		return " Physician Name : "+(physicianName)+"\n Email : "+emailId+"\n Contact : "+contact+"\n Address : "+(address)+"\n Confirmation Status: "+confirmationStatus+"\n Time : "+time+"\n Product : "+product;
	}
}
