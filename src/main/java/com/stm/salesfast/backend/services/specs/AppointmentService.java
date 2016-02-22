package com.stm.salesfast.backend.services.specs;

import java.sql.Time;
import java.text.ParseException;

public interface AppointmentService {

	public void addAppointment(int physId, Time time, String confirmationStatus) throws ParseException;
	
	
}
