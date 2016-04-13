package com.stm.salesfast.backend.services.specs;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.stm.salesfast.backend.entity.AppointmentCountPerDayEntity;
import com.stm.salesfast.backend.entity.SalesRepDailyPerformanceEntity;
import com.stm.salesfast.backend.entity.SalesRepMeetingPerformanceEntity;

public interface SalesRepPerformanceService {
	int getNoOfAppointmentsIfExists(List<AppointmentCountPerDayEntity> listToBeChecked, Date forDate);

	List<SalesRepDailyPerformanceEntity> getDailyPerformanceData(int userId,
			int month);

	List<SalesRepDailyPerformanceEntity> addRestOfDates(
			List<SalesRepDailyPerformanceEntity> dailyPerformaceList, int month);

	SalesRepMeetingPerformanceEntity getMeetingStatuses(int userId);

	boolean ifListContainsParticularDay(List<SalesRepDailyPerformanceEntity> dailyPerformaceList,
			LocalDate particularDate, LocalDate firstDate);
}
