package com.stm.salesfast.backend.entity;

import com.stm.salesfast.backend.dto.NotificationsDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class NotificationEntity {
	
		private int notificationId;
		private String notification;
		private boolean hasRead;
		private int userId;
		private String notificationCategory;
		
		public NotificationEntity(NotificationsDto notification) {
				this.notificationId = notification.getNotificationId();
				this.notification = notification.getNotification();
				this.hasRead = notification.isHasRead();
				this.userId = notification.getUserId();
				this.notificationCategory = notification.getNotificationCategory();
		}
}
