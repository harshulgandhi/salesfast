package com.stm.salesfast.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.stm.salesfast.backend.dto.PhysicianStgDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AlignedPhysicianEntity implements Comparable<AlignedPhysicianEntity>{

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
	private int productId;
	private String productName;
	private double importanceFactor;
	
	public AlignedPhysicianEntity(PhysicianStgDto physicianDto,
			String productName2, int productId) {
		// TODO Auto-generated constructor stub
		this.physicianId = physicianDto.getPhysicianId();
		this.firstName = physicianDto.getFirstName();
		this.lastName = physicianDto.getLastName();
		this.email = physicianDto.getEmail();
		this.contactNumber = physicianDto.getContactNumber();
		this.addressLineOne = physicianDto.getAddressLineOne();
		this.addressLineTwo = physicianDto.getAddressLineTwo();
		this.city = physicianDto.getCity();
		this.state = physicianDto.getState();
		this.zip = physicianDto.getZip();
		this.medicalField = physicianDto.getMedicalField();
		this.isNew = physicianDto.isNew();
		this.status = physicianDto.getStatus();
		this.importanceFactor = physicianDto.getImportanceFactor();
		this.productName = productName2;
		this.productId = productId;
		
	}

	@Override
	public int compareTo(AlignedPhysicianEntity o) {
		// TODO Auto-generated method stub
		if(importanceFactor > o.importanceFactor) return -1;
		else return 1;
	}
}
