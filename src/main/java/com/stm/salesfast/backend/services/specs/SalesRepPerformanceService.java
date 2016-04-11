package com.stm.salesfast.backend.services.specs;

import java.sql.Date;
import java.util.List;

import com.stm.salesfast.backend.entity.AppointmentCountPerDayEntity;
import com.stm.salesfast.backend.entity.SalesRepDailyPerformanceEntity;

public interface SalesRepPerformanceService {
	List<SalesRepDailyPerformanceEntity> getDailyPerformanceData(int userId);

	int getNoOfAppointmentsIfExists(List<AppointmentCountPerDayEntity> listToBeChecked, Date forDate);
}
