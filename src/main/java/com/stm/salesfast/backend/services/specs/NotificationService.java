package com.stm.salesfast.backend.services.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.NotificationsDto;
import com.stm.salesfast.backend.entity.NotificationEntity;

public interface NotificationService {
	public NotificationEntity getByNotificationId(int notificationId);
	public List<NotificationEntity> getAlreadyReadNotifications(int userId);
	public void deleteNotification(int notificationId);
	public void updateNotificationContent(String notification, int notificationId);
	public List<NotificationEntity> getNotificationsForUser(int userId);
	public void insertNotificationAppointmentCancellation(int userId, String physicianName, String notificationCategory);
	public void insertNotificationFollowupReminder(int userId, String physicianName, String productName, String notificationCategory);
	
}