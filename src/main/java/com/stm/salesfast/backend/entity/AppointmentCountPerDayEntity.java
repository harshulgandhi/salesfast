package com.stm.salesfast.backend.entity;

import java.sql.Date;
import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AppointmentCountPerDayEntity {
	private int noOfAppointment;
	private Date date;
}
