package com.stm.salesfast.backend.utils;

import java.io.Serializable;

import org.codehaus.jackson.*;
import org.codehaus.jackson.map.Serializers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AjaxRequestListMapper implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int physicianId;
	private int productId;
	private String appointmentTime;
	
}
