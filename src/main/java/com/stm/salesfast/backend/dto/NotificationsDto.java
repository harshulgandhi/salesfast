package com.stm.salesfast.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class NotificationsDto {

	private int notificationId;
	private String notification;
	private boolean hasRead;
	private int userId;
	private String notificationCategory;

	public NotificationsDto(String notification2, boolean b, int userId2, String notificationCategory) {
		// TODO Auto-generated constructor stub
		this.notification = notification2;
		this.hasRead = b;
		this.userId = userId2;
		this.notificationCategory = notificationCategory;
	}	
}
