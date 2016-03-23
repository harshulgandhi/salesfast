package com.stm.salesfast.backend.utils;

import java.io.Serializable;

import org.codehaus.jackson.*;
import org.codehaus.jackson.map.Serializers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AjaxRequestListMapper implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int physicianId;
	private int productId;
	private String appointmentStartTime;
	private String appointmentEndTime;
	private String appointmentDate;
	private String appointmentStatus;
	private String additionalNotes;
	
}
