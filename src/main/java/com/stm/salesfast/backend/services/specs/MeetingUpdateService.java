package com.stm.salesfast.backend.services.specs;

import java.text.ParseException;
import java.util.List;

import com.stm.salesfast.backend.dto.MeetingUpdateDto;
import com.stm.salesfast.backend.dto.ProductDto;
import com.stm.salesfast.backend.entity.MeetingUpdateEntity;

public interface MeetingUpdateService {

	public void insertMeetingUpdate(MeetingUpdateEntity meetingUpdateEntity) throws ParseException;
	
	public MeetingUpdateDto getMeetingUpdateByAppointmentId(int appointmentId);
	
	public List<Integer> getPrescribingProduct(int physicianId);

	void setupEDetailing(MeetingUpdateEntity meetingUpdateEntity);

	void sendMail(String subject, String body, String toEmailId);


	List<MeetingUpdateDto> getForPhysiciansPortal(String status1,
			String status2, int physicianId);
}
