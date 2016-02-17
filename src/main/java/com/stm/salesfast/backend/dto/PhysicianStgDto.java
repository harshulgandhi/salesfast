package com.stm.salesfast.backend.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
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
	private boolean isNew;
	private String status;
	
	@Override
	public String toString(){
		return " Physician Name : "+(firstName + " " +lastName)+"\n Email : "+email+"\n Contact : "+contactNumber+"\n Address : "+(addressLineOne+", "+addressLineTwo)+"\n City : "+city+"\n State : "+state+"\n Zip : "+zip+"\n Medical Field: "+medicalField+"\n Is New?: "+isNew;
	}
}
