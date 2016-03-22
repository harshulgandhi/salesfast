package com.stm.salesfast.backend.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FutureAppointmentUpdateEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String appointmentTime;
	private String appointmentDate;
	private String appointmentStatus;
	private String additionalNotes;
	private int appointmentId;
}
