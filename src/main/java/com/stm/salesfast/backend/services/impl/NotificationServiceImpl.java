package com.stm.salesfast.backend.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.NotificationsDao;
import com.stm.salesfast.backend.dto.NotificationsDto;
import com.stm.salesfast.backend.entity.NotificationEntity;
import com.stm.salesfast.backend.services.specs.NotificationService;
import com.stm.salesfast.constant.ConstantValues;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	NotificationsDao notificationDao;
	
	@Override
	public NotificationEntity getByNotificationId(int notificationId) {
		// TODO Auto-generated method stub
		return new NotificationEntity(notificationDao.getBy(notificationId));
	}

	@Override
	public List<NotificationEntity> getAlreadyReadNotifications(int userId) {
		// TODO Auto-generated method stub
		List<NotificationEntity> notificationEntities = new ArrayList<>();
		List<NotificationsDto> notificationDtos =notificationDao.getByHasRead(userId); 
		for(NotificationsDto eachNotification : notificationDtos){
			notificationEntities.add(new NotificationEntity(eachNotification));
		}
		return notificationEntities;
	}

	@Override
	public void deleteNotification(int notificationId) {
		// TODO Auto-generated method stub
		notificationDao.deleteBy(notificationId);
	}

	@Override
	public void updateNotificationContent(String notification,
			int notificationId) {
		// TODO Auto-generated method stub
		notificationDao.updateNotification(notification, notificationId);
	}
	
	@Override
	public void insertNotificationAppointmentCancellation(int userId, String physicianName, String notificationCategory) {
		// TODO Auto-generated method stub
		String notification = String.format(ConstantValues.FORMAT_APPOINTMENT_CANCELLATION, physicianName);
		notificationDao.insertNotification(new NotificationsDto(notification, false, userId, notificationCategory));
	}
	
	@Override
	public void insertNotificationAppointmentCancellationBySR(int userId, String salesRepName, String notificationCategory) {
		// TODO Auto-generated method stub
		String notification = String.format(ConstantValues.FORMAT_APPOINTMENT_CANCELLATION_BY_SR, salesRepName);
		notificationDao.insertNotification(new NotificationsDto(notification, false, userId, notificationCategory));
	}

	@Override
	public void insertNotificationAppointmentReschedulingBySR(int userId, String salesRepName, String date, String time, String notificationCategory) {
		// TODO Auto-generated method stub
		String notification = String.format(ConstantValues.FORMAT_APPOINTMENT_RESCHEDULING_BY_SR, salesRepName, date, time);
		notificationDao.insertNotification(new NotificationsDto(notification, false, userId, notificationCategory));
	}
	
	@Override
	public List<NotificationEntity> getNotificationsForUser(int userId) {
		// TODO Auto-generated method stub
		
		List<NotificationEntity> notificationEntities = new ArrayList<>();
		List<NotificationsDto> notificationDtos = notificationDao.getByUser(userId);; 
		for(NotificationsDto eachNotification : notificationDtos){
			notificationEntities.add(new NotificationEntity(eachNotification));
		}
		return notificationEntities;
	}
	
	@Override
	public int getNotificationCountForUser(int userId){
		return notificationDao.countBy(userId);
	}

	/**
	 * Insert notification for reminder of
	 * follow up calls
	 * */
	@Override
	public void insertNotificationFollowupReminder(int userId,
			String physicianName, String productName,
			String notificationCategory) {
		String notification = String.format(ConstantValues.FORMAT_FOLLOWUP, physicianName, productName);
		notificationDao.insertNotification(new NotificationsDto(notification, false, userId, notificationCategory));
	}
	
	@Override
	public void insertNotificationNewProductPhysician(int userId, String productName, String notificationCategory){
		String notification = String.format(ConstantValues.FORMAT_PHYSICIAN_NEW_PRODUCT, productName);
		notificationDao.insertNotification(new NotificationsDto(notification, false, userId, notificationCategory));
	}
	
	@Override
	public void insertNotificationDocUpdatePhysician(int userId, String productName, String notificationCategory){
		String notification = String.format(ConstantValues.FORMAT_EDETAILING_DOC_UPDATE, productName);
		notificationDao.insertNotification(new NotificationsDto(notification, false, userId, notificationCategory));
	}
	
	@Override
	public void insertNotificationNewProductSalesRep(int userId, String productName, String notificationCategory){
		String notification = String.format(ConstantValues.FORMAT_FOLLOWUP_CUSTGROWTH_LOST, productName);
		notificationDao.insertNotification(new NotificationsDto(notification, false, userId, notificationCategory));
	}
	
	/**
	 * Notifications in case of improved 
	 * products - 3 notifications for different combinations of type
	 * of improvements possible
	 * */
	@Override
	public void insertNotificationImprovedProductMoreAffordableSalesRep(int userId, String productName, String prevProductName, String notificationCategory){
		String notification = String.format(ConstantValues.FORMAT_FOLLOWUP_CUSTGROWTH_LOST_MORE_AFFORDABLE, productName, prevProductName, prevProductName);
		notificationDao.insertNotification(new NotificationsDto(notification, false, userId, notificationCategory));
	}
	@Override
	public void insertNotificationImprovedProductLessSideEffectsSalesRep(int userId, String productName, String prevProductName, String notificationCategory){
		String notification = String.format(ConstantValues.FORMAT_FOLLOWUP_CUSTGROWTH_LOST_LESS_SIDE_EFFECTS, productName, prevProductName, prevProductName);
		notificationDao.insertNotification(new NotificationsDto(notification, false, userId, notificationCategory));
	}
	@Override
	public void insertNotificationImprovedProductBothReasonsSalesRep(int userId, String productName, String prevProductName, String notificationCategory){
		String notification = String.format(ConstantValues.FORMAT_FOLLOWUP_CUSTGROWTH_LOST_BOTH_REASONS, productName, prevProductName, prevProductName);
		notificationDao.insertNotification(new NotificationsDto(notification, false, userId, notificationCategory));
	}
	
	
	
	@Override
	public void insertNotificationDocUpdateSalesRep(int userId, String productName, String physicianName, String notificationCategory){
		String notification = String.format(ConstantValues.FORMAT_TRAINING_DOC_UPDATE, productName, physicianName);
		notificationDao.insertNotification(new NotificationsDto(notification, false, userId, notificationCategory));
	}
	
	@Override
	public void insertNotificationSalesRepPhysNotInterest(int userId,  String productName, String notificationCategory){
		String notification = String.format(ConstantValues.FORMAT_FOLLOWUP_CUSTGROWTH_NOT_INTERESTED, productName);
		notificationDao.insertNotification(new NotificationsDto(notification, false, userId, notificationCategory));
	}
	
	@Override
	public void insertNotificationSalesRepPhysPrescribing(int userId, String productName, String notificationCategory){
		String notification = String.format(ConstantValues.FORMAT_FOLLOWUP_CUSTRETENTION, productName);
		notificationDao.insertNotification(new NotificationsDto(notification, false, userId, notificationCategory));
	}

	@Override
	public void insertNotificationLiveMeetingQuestion(String salesRepName, int userId, String notificationCategory){
		String notification = String.format(ConstantValues.FORMAT_NEW_QUESTION, salesRepName);
		notificationDao.insertNotification(new NotificationsDto(notification, false, userId, notificationCategory));
	}
	
	@Override
	public void insertNotificationQuestionAnswered(String answeredByName, int userId, String notificationCategory){
		String notification = String.format(ConstantValues.FORMAT_QUESTION_ANSWERED, answeredByName);
		notificationDao.insertNotification(new NotificationsDto(notification, false, userId, notificationCategory));
	}
}
