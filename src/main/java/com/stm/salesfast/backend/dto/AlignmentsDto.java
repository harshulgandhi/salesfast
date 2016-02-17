package com.stm.salesfast.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AlignmentsDto {
	private int alignmentId;
	private int physicianId;
	private int userId;
	private int territoryId;
	private int districtId;
	private String zip;
	
	@Override
	public String toString(){
		return "Alignment Id : "+alignmentId+" | Physician Id : "+physicianId+" | User Id : "+userId+" | Zip : "+zip+" | Territory Id : "+territoryId+" | District Id : "+districtId;
	}
}
