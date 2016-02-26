package com.stm.salesfast.backend.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MeetingUpdateDto {
	
	private int meetinUpdateId;
	private Date date;
	private String status;
	private boolean isEDetailed;
	private int physicianId;
	private int productId;
	private String medicalFieldId;
	private int appointmentId;
	
	public MeetingUpdateDto(Date currentDate, String meetingStatus,
			boolean eDetailing, int physicianId2, int productId2,
			String medicalFieldId2, int appointmentId2) {
		// TODO Auto-generated constructor stub
		this.date = currentDate;
		this.status = meetingStatus;
		this.isEDetailed = eDetailing;
		this.physicianId = physicianId2;
		this.productId = productId2;
		this.medicalFieldId = medicalFieldId2;
		this.appointmentId = appointmentId2;
	}
	
	@Override
	public String toString(){
		return " Date : "+(date)+"\n Status : "+status+"\n isEDetailed : "+isEDetailed+"\n Physician Id : "+(physicianId)+"\n Product Id: "+productId+"\n Medical Field Id : "+medicalFieldId+"\n Appointment Id : "+appointmentId;
	}
}
