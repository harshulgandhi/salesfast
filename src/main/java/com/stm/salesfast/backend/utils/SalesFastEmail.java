package com.stm.salesfast.backend.utils;


import java.util.List;

import com.stm.salesfast.backend.dto.UserDto;


public interface SalesFastEmail {
	
//	public void addTo(UserDto user);
//	public void addTo(List<UserDto> users);
	public void addSubject(String subject);
	public void addTextBody(String body);
	public void addHTMLBody(String body);
	public void setFromEmail(String email);
	public void setFromName(String name);
	public void sendMail();
	
}