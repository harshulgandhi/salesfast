package com.stm.salesfast.backend.entity;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhysicianAppointmentEntity {

	private String salesRepName;
	private String organisationName;
	private String product;
	private String time;
	private String status;
	
	@Override
	public String toString(){
		return " SalesRep Name : "+(salesRepName)+"\n Organisation : "+organisationName+"\n Product : "+product+"\n Time : "+(time)+"\n Status: "+status;
	}
}
