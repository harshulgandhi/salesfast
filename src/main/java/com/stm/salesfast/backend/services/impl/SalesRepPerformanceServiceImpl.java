package com.stm.salesfast.backend.services.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.entity.AppointmentCountPerDayEntity;
import com.stm.salesfast.backend.entity.SalesRepDailyPerformanceEntity;
import com.stm.salesfast.backend.services.specs.AppointmentService;
import com.stm.salesfast.backend.services.specs.SalesRepPerformanceService;

@Service
public class SalesRepPerformanceServiceImpl implements SalesRepPerformanceService {

	@Autowired
	AppointmentService appointmentService;
	
	@Override
	public List<SalesRepDailyPerformanceEntity> getDailyPerformanceData(int userId) {
		List<SalesRepDailyPerformanceEntity> dailyPerformanceList = new ArrayList<>();
		List<AppointmentCountPerDayEntity> lostAppointments = appointmentService.getPerDayAppointmentCountByPerformance(userId, "LOST");
		List<AppointmentCountPerDayEntity> prescribingAppointments = appointmentService.getPerDayAppointmentCountByPerformance(userId, "LOST");
		if(lostAppointments.size() > prescribingAppointments.size()){
			for(AppointmentCountPerDayEntity eachDayCount : lostAppointments){
				dailyPerformanceList.add(new SalesRepDailyPerformanceEntity(
						eachDayCount.getDate(),
						eachDayCount.getNoOfAppointment(),
						getNoOfAppointmentsIfExists(lostAppointments, eachDayCount.getDate())
				));
			}
		}else{
			for(AppointmentCountPerDayEntity eachDayCount : prescribingAppointments){
				dailyPerformanceList.add(new SalesRepDailyPerformanceEntity(
						eachDayCount.getDate(),
						getNoOfAppointmentsIfExists(prescribingAppointments, eachDayCount.getDate()),
						eachDayCount.getNoOfAppointment()
				));
			}
		}
		
		return dailyPerformanceList;
	}
	
	@Override
	public int getNoOfAppointmentsIfExists(List<AppointmentCountPerDayEntity> listToBeChecked, Date forDate){
		for(AppointmentCountPerDayEntity eachEntity : listToBeChecked){
			if(eachEntity.getDate() == forDate){
				return eachEntity.getNoOfAppointment();
			}
		}
		return 0;
	}

}
