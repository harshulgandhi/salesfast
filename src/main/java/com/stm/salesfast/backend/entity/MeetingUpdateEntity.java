package com.stm.salesfast.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MeetingUpdateEntity {

	private int appointmentId;
	private int physicianId;
	private String productName;
	private String meetingStatus;
	private boolean isEDetailing;
	
	@Override
	public String toString(){
		return "Appointment Id : "+appointmentId+"Physician Id : "+(physicianId)+"\n Product Name : "+productName+"\n isEDetailing : "+isEDetailing+"\n Status : "+(meetingStatus);
	}
}
