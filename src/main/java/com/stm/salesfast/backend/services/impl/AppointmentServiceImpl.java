package com.stm.salesfast.backend.services.impl;

import java.sql.Time;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.AlignmentsDao;
import com.stm.salesfast.backend.dao.specs.AppointmentDao;
import com.stm.salesfast.backend.dto.AppointmentDto;
import com.stm.salesfast.backend.services.specs.AlignmentFetchService;
import com.stm.salesfast.backend.services.specs.AppointmentService;
import com.stm.salesfast.backend.services.specs.UserAccountService;
import com.stm.salesfast.backend.utils.SalesFastUtilities;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	String CURRENTUSERNAME = "johny";
	
	@Autowired
	AppointmentDao appointmentDao;
	
	@Autowired
	AlignmentsDao alignmentDao;
	
	@Autowired
	UserAccountService userAccountService;
	
	@Autowired
	AlignmentFetchService alignmentFetchService;
	
	
	@Override
	public void addAppointment(int physId, Time time, String confirmationStatus) throws ParseException {
		// TODO Auto-generated method stub
		int userId = userAccountService.getUserIdByUserName(CURRENTUSERNAME);
		int productId = alignmentFetchService.getAlignedProduct(userId, physId);
		appointmentDao.insertAppointment(new AppointmentDto(time, SalesFastUtilities.getCurrentDate(), physId, userId, productId,confirmationStatus));
	}
}
