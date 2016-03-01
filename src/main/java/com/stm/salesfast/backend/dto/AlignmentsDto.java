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
	private int productId;
	
	@Override
	public String toString(){
		return "Alignment Id : "+alignmentId+" | Physician Id : "+physicianId+" | User Id : "+userId+" | Zip : "+zip+" | Territory Id : "+territoryId+" | District Id : "+districtId+" | Product Id : "+productId;
	}

	public AlignmentsDto(int physicianId2, int userId2, int territoryId2,
			int districtId2, String zip2, int productId2) {
		// TODO Auto-generated constructor stub
		this.physicianId = physicianId2;
		this.userId = userId2;
		this.territoryId = territoryId2;
		this.districtId = districtId2;
		this.zip = zip2;
		this.productId = productId2;
	}
}
