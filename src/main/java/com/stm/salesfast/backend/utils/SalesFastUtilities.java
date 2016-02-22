package com.stm.salesfast.backend.utils;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SalesFastUtilities {
	
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
}
