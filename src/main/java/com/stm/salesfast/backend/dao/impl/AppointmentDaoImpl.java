package com.stm.salesfast.backend.dao.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stm.salesfast.backend.dao.specs.AppointmentDao;
import com.stm.salesfast.backend.dto.AppointmentDto;

@Repository
public class AppointmentDaoImpl implements AppointmentDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final String FETCH_BY_ID = "SELECT * FROM appointment WHERE appointmentId = ?";
	private static final String FETCH_BY_USERID_wMeetingUpdateExperienceCheck = "SELECT * FROM appointment WHERE userId = ? "
																		+ "AND (hasMeetingUpdate = 0 OR hasMeetingExperience = 0) "
																		+ "ORDER BY time";
	private static final String INSERT = "INSERT INTO appointment "+
										"(time, date, physicianId, userId, productId, confirmationStatus, zip) "+
										"VALUES (?,?,?,?,?,?,?)";
	private static final String FETCHID_BY_PHYS_USER = "SELECT appointmentId FROM appointment WHERE userId = ? AND physicianId = ?";
	private static final String UPDATE_HASMEETINGUPDATE = "UPDATE `salesfast`.`appointment` "
														+"SET `hasMeetingUpdate` = ? "
														+"WHERE `appointmentId` = ?;";
	
	private static final String UPDATE_HASMEETINGEXPERIENCE = "UPDATE `salesfast`.`appointment` "
														+"SET `hasMeetingExperience` = ? "
														+"WHERE `appointmentId` = ?;";
	private static final String UPDATE_STATUS = "UPDATE appointment SET confirmationStatus = ?,"
												+ "cancellationReason = ?"
												+ "WHERE appointmentId = ?";
	
	
	@Override
	public AppointmentDto getAppointmentById(int appointmentId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(FETCH_BY_ID, (rs, rownnum)->{
				return new AppointmentDto(appointmentId, rs.getTime("time"), rs.getDate("date"), rs.getInt("physicianId"), rs.getInt("userId"), rs.getInt("productId"), rs.getString("confirmationStatus"), rs.getString("zip"), rs.getBoolean("hasMeetingUpdate"),rs.getBoolean("hasMeetingExperience"));
			}, appointmentId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * This method returns appointments of a user based on whether
	 * he/she has entered meeting update or meeting experience details 
	 * */
	@Override
	public List<AppointmentDto> getAppointmentByUserId(int userId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_BY_USERID_wMeetingUpdateExperienceCheck, (rs, rownnum)->{
				return new AppointmentDto(rs.getInt("appointmentId"), rs.getTime("time"), rs.getDate("date"), rs.getInt("physicianId"), userId, rs.getInt("productId"), rs.getString("confirmationStatus"), rs.getString("zip"),rs.getBoolean("hasMeetingUpdate"),rs.getBoolean("hasMeetingExperience"));
			}, userId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public AppointmentDto getAppointmentByPhysicianId(int physicianId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertAppointment(AppointmentDto appointment) {
		// TODO Auto-generated method stub
		try{
			jdbcTemplate.update(INSERT, (ps)->{
				ps.setTime(1, appointment.getTime());
				ps.setDate(2, appointment.getDate());
				ps.setInt(3, appointment.getPhysicianId());
				ps.setInt(4, appointment.getUserId());
				ps.setInt(5, appointment.getProductId());
				ps.setString(6, appointment.getConfirmationStatus());
				ps.setString(7, appointment.getZip());
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
	}

	@Override
	public void updateStatus(int appointmentId, String status, String reason) {
		// TODO Auto-generated method stub
		try{
			jdbcTemplate.update(UPDATE_STATUS, (ps)->{
				ps.setString(1, status);
				ps.setString(2, reason);
				ps.setInt(3, appointmentId);
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public int getIdByPhysIdUserId(int physicianId, int userId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(FETCHID_BY_PHYS_USER, (rs, rownum) -> {
				return rs.getInt("appointmentId");
				}, userId, physicianId);
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return 0;
	}


	@Override
	public void setMeetinUpdateFlag(int appointmentId, int meetingUpdateFlag) {
		// TODO Auto-generated method stub
		try{
			jdbcTemplate.update(UPDATE_HASMEETINGUPDATE, (ps)->{
				ps.setInt(1, meetingUpdateFlag);
				ps.setInt(2, appointmentId);
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void setMeetinExperienceFlag(int appointmentId, int meetingUpdateFlag) {
		// TODO Auto-generated method stub
		try{
			jdbcTemplate.update(UPDATE_HASMEETINGEXPERIENCE, (ps)->{
				ps.setInt(1, meetingUpdateFlag);
				ps.setInt(2, appointmentId);
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
	}
}
