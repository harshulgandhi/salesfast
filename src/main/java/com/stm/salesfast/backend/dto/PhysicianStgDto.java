package com.stm.salesfast.backend.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class PhysicianStgDto {
	private int physicianId;
	private String firstName;
	private String lastName;
	private String email;
	private String contactNumber;
	private String addressLineOne;
	private String addressLineTwo;
	private String city;
	private String state;
	private String zip;
	private String medicalField;
	public boolean isNew;
	private String status;
	private Date practiceStartDate;
	
/*	@Override
	public String toString(){
		return " Physician Name : "+(firstName + " " +lastName)+"\n Email : "+email+"\n Contact : "+contactNumber+"\n Address : "+(addressLineOne+", "+addressLineTwo)+"\n City : "+city+"\n State : "+state+"\n Zip : "+zip+"\n Medical Field: "+medicalField+"\n Is New?: "+isNew;
	}*/
	
	
}
