package com.stm.salesfast.backend.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.stm.salesfast.backend.utils.AjaxRequestListMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FollowupAppointmentUpdateEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private int physicianId;
	private int productId;
	private String appointmentStartTime;
	private String appointmentEndTime;
	private String appointmentDate;
	private String appointmentStatus;
	private String additionalNotes;
	private int appointmentId;
}
