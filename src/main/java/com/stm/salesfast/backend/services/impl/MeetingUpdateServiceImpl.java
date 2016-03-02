package com.stm.salesfast.backend.services.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.MeetingUpdateDao;
import com.stm.salesfast.backend.dto.MeetingUpdateDto;
import com.stm.salesfast.backend.dto.ProductDto;
import com.stm.salesfast.backend.entity.MeetingUpdateEntity;
import com.stm.salesfast.backend.services.specs.AppointmentService;
import com.stm.salesfast.backend.services.specs.MeetingUpdateService;
import com.stm.salesfast.backend.services.specs.ProductFetchService;
import com.stm.salesfast.backend.utils.SalesFastUtilities;

@Service
public class MeetingUpdateServiceImpl implements MeetingUpdateService {

	@Autowired
	ProductFetchService productService;
	
	@Autowired
	MeetingUpdateDao meetingUpdateDao;
	
	@Autowired
	AppointmentService appointmentService;
	
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
		
		appointmentService.setHasMeetingUpdateFlag(meetingUpdateEntity.getAppointmentId(), 1);
	}

	@Override
	public MeetingUpdateDto getMeetingUpdateByAppointmentId(int appointmentId) {
		// TODO Auto-generated method stub
		return meetingUpdateDao.getByAppointmentId(appointmentId);
	}

	@Override
	public List<Integer> getPrescribingProduct(int physicianId) {
		// TODO Auto-generated method stub
		List<MeetingUpdateDto> prescribingRecords = meetingUpdateDao.getByPrescribingPhysicians(physicianId);
		List<Integer> prescribingProducts = new ArrayList<>();
		for(MeetingUpdateDto eachRecord : prescribingRecords){
			prescribingProducts.add(eachRecord.getProductId());
		}
		return prescribingProducts;
	}

}
