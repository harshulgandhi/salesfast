package com.stm.salesfast.backend.utils;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SalesFastUtilities {
	
	private final static double AVERAGE_MILLIS_PER_MONTH = 365.24 * 24 * 60 * 60 * 1000 / 12;
	
	public static Date getCurrentDate() throws ParseException{
		SimpleDateFormat currDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		java.util.Date utilToday = currDateFormat.parse(currDateFormat.format(new java.util.Date()));
		
		return new Date(utilToday.getTime());
	}

	public static Time getTimeForStringTime(String toTime, String format) throws ParseException{
		DateFormat formatter = new SimpleDateFormat(format);
		Time timeFromString =  new Time(formatter.parse(toTime).getTime());
		return timeFromString;
	}
	
	public static Date getDateForStringTime(String toDate, String format) throws ParseException{
		DateFormat formatter = new SimpleDateFormat(format);
		Date dateFromString =  new Date(formatter.parse(toDate).getTime());
		return dateFromString;
	}
	
	public static double numberOfMonth(Date fromDate) throws ParseException{
		Date currDate = getCurrentDate();
		return (currDate.getTime() - fromDate.getTime())/AVERAGE_MILLIS_PER_MONTH;
	}
	
}
