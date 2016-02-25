package com.stm.salesfast.backend.services.specs;

import java.text.ParseException;

import com.stm.salesfast.backend.dto.ProductDto;
import com.stm.salesfast.backend.entity.MeetingUpdateEntity;

public interface MeetingUpdateService {

	public void insertMeetinUpdate(MeetingUpdateEntity meetingUpdateEntity) throws ParseException;
}
