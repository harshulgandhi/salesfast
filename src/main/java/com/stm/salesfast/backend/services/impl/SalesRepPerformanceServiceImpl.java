package com.stm.salesfast.backend.services.impl;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.controllers.DataReportController;
import com.stm.salesfast.backend.entity.AppointmentCountPerDayEntity;
import com.stm.salesfast.backend.entity.MeetingStatusCountEntity;
import com.stm.salesfast.backend.entity.SalesRepDailyPerformanceEntity;
import com.stm.salesfast.backend.entity.SalesRepMeetingPerformanceEntity;
import com.stm.salesfast.backend.services.specs.AppointmentService;
import com.stm.salesfast.backend.services.specs.MeetingUpdateService;
import com.stm.salesfast.backend.services.specs.SalesRepPerformanceService;

@Service
public class SalesRepPerformanceServiceImpl implements SalesRepPerformanceService {
	private Logger log = LoggerFactory.getLogger(SalesRepPerformanceServiceImpl.class.getName());
	
	@Autowired
	AppointmentService appointmentService;
	
	@Autowired
	MeetingUpdateService meetingUpdate;
	
	List<SalesRepDailyPerformanceEntity> toRemovePrevMonth;
	
	@Override
	public List<SalesRepDailyPerformanceEntity> getDailyPerformanceData(int userId, int month) {
		log.info("Selected Month " +month);
		List<SalesRepDailyPerformanceEntity> dailyPerformanceList = new ArrayList<>();
		List<AppointmentCountPerDayEntity> lostAppointments = appointmentService.getPerDayAppointmentCountByPerformance(userId, "LOST");
		List<AppointmentCountPerDayEntity> prescribingAppointments = appointmentService.getPerDayAppointmentCountByPerformance(userId, "PRESCRIBING");
		if(lostAppointments.size() > prescribingAppointments.size()){
			for(AppointmentCountPerDayEntity eachDayCount : lostAppointments){
				dailyPerformanceList.add(new SalesRepDailyPerformanceEntity(
						eachDayCount.getDate(),
						eachDayCount.getNoOfAppointment(),
						getNoOfAppointmentsIfExists(prescribingAppointments, eachDayCount.getDate())
				));
			}
		}else{
			for(AppointmentCountPerDayEntity eachDayCount : prescribingAppointments){
				dailyPerformanceList.add(new SalesRepDailyPerformanceEntity(
						eachDayCount.getDate(),
						getNoOfAppointmentsIfExists(lostAppointments, eachDayCount.getDate()),
						eachDayCount.getNoOfAppointment()
				));
			}
		}
		
		return addRestOfDates(dailyPerformanceList, month);
	}
	
	@Override
	public int getNoOfAppointmentsIfExists(List<AppointmentCountPerDayEntity> listToBeChecked, Date forDate){
		for(AppointmentCountPerDayEntity eachEntity : listToBeChecked){
			if(eachEntity.getDate().getTime() == forDate.getTime()){
				return eachEntity.getNoOfAppointment();
			}
		}
		return 0;
	}
	
	@Override
	public List<SalesRepDailyPerformanceEntity> addRestOfDates(List<SalesRepDailyPerformanceEntity> dailyPerformaceList, int month){
		YearMonth yearMonth = YearMonth.of( 2016, month );  // January of 2015.
		LocalDate start = yearMonth.atDay( 1 );
		LocalDate firstDate = start;
		LocalDate lastDate = yearMonth.atEndOfMonth();
		LocalDate end = LocalDate.now();
		if(!start.isBefore(end)) end = lastDate;
		if(start.getDayOfWeek() == DayOfWeek.SATURDAY) start = start.plusDays(2);
		if(start.getDayOfWeek() == DayOfWeek.SUNDAY) start = start.plusDays(1);
		log.info("Start :"+start.toString()+"; end : "+end.toString());
		
		while(start.isBefore(end.plusDays(1))){
			if(!ifListContainsParticularDay(dailyPerformaceList, start, firstDate)){
				dailyPerformaceList.add(new SalesRepDailyPerformanceEntity(
						Date.valueOf(start),0,0
						));
			}
			if(start.getDayOfWeek() == DayOfWeek.FRIDAY){
				start = start.plusDays(3);
			}else start = start.plusDays(1);
		}
		if(toRemovePrevMonth != null) dailyPerformaceList.removeAll(toRemovePrevMonth);
		Collections.sort(dailyPerformaceList);
		
		return dailyPerformaceList;
	}
	
	@Override
	public boolean ifListContainsParticularDay(List<SalesRepDailyPerformanceEntity> dailyPerformaceList, LocalDate particularDate, LocalDate firstDate){
		for(SalesRepDailyPerformanceEntity eachEntity : dailyPerformaceList){
			if(eachEntity.getDate().getTime()== Date.valueOf(particularDate).getTime()) return true;
			if(eachEntity.getDate().getTime() < Date.valueOf(firstDate).getTime()) toRemovePrevMonth.add(eachEntity);
		}
		return false;
	}
	
	@Override
	public SalesRepMeetingPerformanceEntity getMeetingStatuses(int userId){
		List<MeetingStatusCountEntity> meetingUpdateCount = meetingUpdate.getMeetingUpdateStatusCount(userId);
		List<MeetingStatusCountEntity> appointmentCallCount = appointmentService.getAppointmentStatusCount(userId);
		int countOfLost = 0;
		int countOfPrescribing = 0;
		int countOfNotInterested = 0;
		for(MeetingStatusCountEntity eachEntity : meetingUpdateCount){
			if(eachEntity.getStatus().equals("LOST")){
				countOfLost = eachEntity.getCount();
			}else if(eachEntity.getStatus().equals("PRESCRIBING")){
				countOfPrescribing = eachEntity.getCount();
			}
		}
		for(MeetingStatusCountEntity eachEntity : appointmentCallCount){
			if(eachEntity.getStatus().equals("NOT INTERESTED")){
				countOfNotInterested = eachEntity.getCount();
			}
		}
		int total = countOfLost + countOfPrescribing + countOfNotInterested;
		
		return new SalesRepMeetingPerformanceEntity(countOfLost*100.0/total, countOfPrescribing*100.0/total,
				countOfNotInterested*100.0/total);
		
	}
}
