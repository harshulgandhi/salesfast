package com.stm.salesfast.backend.dao.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.NotificationsDto;

public interface NotificationsDao {
	
	public NotificationsDto getBy(int notificationId);
	public List<NotificationsDto> getByHasRead(int userId);
	public void deleteBy(int notificationId);
	public void updateNotification(String notification, int notificationId);
	public List<NotificationsDto> getByUser(int userId);
	public void insertNotification(NotificationsDto notification);
}
