package com.stm.salesfast.backend.services.impl;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.MeetingUpdateDao;
import com.stm.salesfast.backend.dto.MeetingUpdateDto;
import com.stm.salesfast.backend.dto.ProductDto;
import com.stm.salesfast.backend.entity.MeetingUpdateEntity;
import com.stm.salesfast.backend.services.specs.MeetingUpdateService;
import com.stm.salesfast.backend.services.specs.ProductFetchService;
import com.stm.salesfast.backend.utils.SalesFastUtilities;

@Service
public class MeetingUpdateServiceImpl implements MeetingUpdateService {

	@Autowired
	ProductFetchService productService;
	
	@Autowired
	MeetingUpdateDao meetingUpdateDao;
	
	@Override
	public void insertMeetinUpdate(MeetingUpdateEntity meetingUpdateEntity) throws ParseException {
		// TODO Auto-generated method stub
		ProductDto product = productService.getProductByName(meetingUpdateEntity.getProductName());
		meetingUpdateDao.insert(new MeetingUpdateDto(
				SalesFastUtilities.getCurrentDate(), 
				meetingUpdateEntity.getMeetingStatus(),
				meetingUpdateEntity.isEDetailing(),
				meetingUpdateEntity.getPhysicianId(),
				product.getProductId(),
				product.getMedicalFieldId(),
				meetingUpdateEntity.getAppointmentId()));
	}

}
