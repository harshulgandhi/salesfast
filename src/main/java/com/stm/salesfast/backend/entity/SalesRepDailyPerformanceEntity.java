package com.stm.salesfast.backend.entity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class SalesRepDailyPerformanceEntity implements Comparable<SalesRepDailyPerformanceEntity>{
	private Date date;
	private int noOfLostAppointments;
	private int noOfPrescribingAppointments;
	
	@Override
	public int compareTo(SalesRepDailyPerformanceEntity o) {
		// TODO Auto-generated method stub
		return (Long.compare(date.getTime(),o.getDate().getTime())); 
	}
}
