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
	public void insertNotificationNewProductPhysician(int userId, String productName,
			String notificationCategory);
	public void insertNotificationNewProductSalesRep(int userId, String productName, String notificationCategory);
	public void insertNotificationSalesRepPhysNotInterest(int userId,
			String productName, String notificationCategory);
	public void insertNotificationSalesRepPhysPrescribing(int userId,
			String productName,
			String notificationCategory);
	public void insertNotificationLiveMeetingQuestion(String salesRepName, int userId,
			String notificationCategory);
	public void insertNotificationQuestionAnswered(String answeredByName, int userId,
			String notificationCategory);
	public int getNotificationCountForUser(int userId);
	public void insertNotificationAppointmentCancellationBySR(int userId,
			String salesRepName, String notificationCategory);
	public void insertNotificationAppointmentReschedulingBySR(int userId,
			String salesRepName, String date, String time,
			String notificationCategory);
	public void insertNotificationDocUpdatePhysician(int userId, String productName,
			String notificationCategory);
	public void insertNotificationDocUpdateSalesRep(int userId, String productName,
			String physicianName, String notificationCategory);
	public void insertNotificationImprovedProductMoreAffordableSalesRep(int userId,
			String productName, String prevProductName,
			String notificationCategory);
	public void insertNotificationImprovedProductLessSideEffectsSalesRep(int userId,
			String productName, String prevProductName,
			String notificationCategory);
	public void insertNotificationImprovedProductBothReasonsSalesRep(int userId,
			String productName, String prevProductName,
			String notificationCategory);
	
}
