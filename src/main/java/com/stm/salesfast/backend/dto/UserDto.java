package com.stm.salesfast.backend.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class UserDto {
	private int userId;
	private String firstName;
	private String lastName;
	private String email;
	private String contactNumber;
	private String addressLineOne;
	private String addressLineTwo;
	private String city;
	private String state;
	private String zip;
	private Date startDate;
	private Date endDate;
	
	public UserDto(String firstName2, String lastName2, String email2,
			String contactNumber2, String addressLineOne2,
			String addressLineTwo2, String city2, String state2, String zip2,
			Object object, Object object2) {
		// TODO Auto-generated constructor stub
		this.firstName = firstName2;
		this.lastName = lastName2;
		this.email = email2;
		this.contactNumber = contactNumber2;
		this.addressLineOne = addressLineOne2;
		this.addressLineTwo = addressLineTwo2;
		this.city = city2;
		this.state = state2;
		this.zip = zip2;
		this.startDate = (Date) object;
		this.endDate = (Date) object2;
	}

}
