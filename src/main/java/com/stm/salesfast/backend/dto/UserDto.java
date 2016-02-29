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
}
