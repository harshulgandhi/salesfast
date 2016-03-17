package com.stm.salesfast.backend.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stm.salesfast.backend.dao.specs.NotificationsDao;
import com.stm.salesfast.backend.dto.NotificationsDto;

@Repository
public class NotificationsDaoImpl implements NotificationsDao {

	private static final String FETCH_BY_USERID = "SELECT * FROM notifications WHERE userId = ?";
	private static final String FETCH_BY_NOTIFICATIONID = "SELECT * FROM notifications WHERE notificationId = ?";
	private static final String FETCH_BY_HASREAD = "SELECT * FROM notifications WHERE userId = ? AND hasRead = 0";
	private static final String DELETE_BY = "DELETE FROM notifications WHERE notificationId = ?";
	private static final String UPDATE_NOTIFICATION = "UPDATE notifications SET notification=? WHERE notificationId = ?";
	private static final String INSERT = "INSERT INTO notifications (notification, hasRead, userId, notificationCategory) VALUES (?,?,?,?)";
	private static final String COUNT_BY_USERID = "SELECT COUNT(*) FROM notifications WHERE userId = ?";
	
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	@Override
	public NotificationsDto getBy(int notificationId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(FETCH_BY_NOTIFICATIONID, (rs, rownnum)->{
				return new NotificationsDto(notificationId, rs.getString("notification"), rs.getBoolean("hasRead"), rs.getInt("userId"), rs.getString("notificationCategory"));
			}, notificationId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<NotificationsDto> getByUser(int userId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_BY_USERID, (rs, rownnum)->{
				return new NotificationsDto(rs.getInt("notificationId"), rs.getString("notification"), rs.getBoolean("hasRead"), userId,rs.getString("notificationCategory"));
			}, userId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<NotificationsDto> getByHasRead(int userId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_BY_HASREAD, (rs, rownnum)->{
				return new NotificationsDto(rs.getInt("notificationId"), rs.getString("notification"), rs.getBoolean("hasRead"), userId, rs.getString("notificationCategory"));
			}, userId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteBy(int notificationId) {
		// TODO Auto-generated method stub
		try{
			jdbcTemplate.update(DELETE_BY, (ps)->{
				ps.setInt(1, notificationId);
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}

	}

	@Override
	public void updateNotification(String notification, int notificationId) {
		// TODO Auto-generated method stub
		try{
			jdbcTemplate.update(UPDATE_NOTIFICATION, (ps)->{
				ps.setString(1, notification);
				ps.setInt(2, notificationId);
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void insertNotification(NotificationsDto notification) {
		try{
			jdbcTemplate.update(INSERT, (ps)->{
				ps.setString(1, notification.getNotification());
				ps.setBoolean(2, notification.isHasRead());
				ps.setInt(3, notification.getUserId());
				ps.setString(4, notification.getNotificationCategory());
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
	}

	@Override
	public int countBy(int userId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(COUNT_BY_USERID, (rs, rownnum)->{
				return rs.getInt(1);
			}, userId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return 0;
	}
}
