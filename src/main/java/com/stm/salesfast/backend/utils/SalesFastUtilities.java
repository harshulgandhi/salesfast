package com.stm.salesfast.backend.utils;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class SalesFastUtilities {
	
	private final static double AVERAGE_MILLIS_PER_MONTH = 365.24 * 24 * 60 * 60 * 1000 / 12;
	private final static double AVERAGE_MILLIS_PER_DAY = 24 * 60 * 60 * 1000;
	
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
	
	public static double numberOfDays(Date fromDate) throws ParseException{
		Date currDate = getCurrentDate();
		return (currDate.getTime() - fromDate.getTime())/AVERAGE_MILLIS_PER_DAY;
	}
	
	public static double numberOfDaysToDate(Date toDate) throws ParseException{
		Date currDate = getCurrentDate();
		return (toDate.getTime() - currDate.getTime())/AVERAGE_MILLIS_PER_DAY;
	}
	
	public static String getCurrentUserName(){
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getUsername(); //get logged in user name
	}
	
	public static int generateRandomNumber(){
		 Random rand= new Random();
         return rand.nextInt(900) + 100;
	}
	
	public static double checkSimilarQuestions(String question){
		String shorter  = "Dose for O_Med_2 is harmful to diabetic patient?";
		String longer= "Are diabetic patients severly effected by O_Med_2?";
		int longerLength = longer.length();
		return (longerLength - StringUtils.getLevenshteinDistance(longer, shorter)) / (double) longerLength;
	}

	/*public static void main(String[] args) {
		System.out.println("distance : "+SalesFastUtilities.checkSimilarQuestions(""));
	}*/
}
