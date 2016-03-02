package com.stm.salesfast.backend.entity;

import java.sql.Date;
import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.stm.salesfast.backend.dto.AlignmentsDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentEntity {
	private int appointmentId;
	private int physicianId;
	private String physicianName;
	private String address;
	private String contact;
	private String emailId;
	private String confirmationStatus;
	private Time time;
	private Date date;
	private String product;
	private boolean hasMeetingUpdate;
	private boolean hasMeetingExperience;
	
	@Override
	public String toString(){
		return "Physician Name : "+(physicianName)+"\n Email : "+emailId+"\n Contact : "+contact+"\n Address : "+(address)+"\n Confirmation Status: "+confirmationStatus+"\n Time : "+time+"\n Product : "+product+" Meeting update : "+hasMeetingUpdate+" Meeting Experience : "+hasMeetingExperience;
	}
}
