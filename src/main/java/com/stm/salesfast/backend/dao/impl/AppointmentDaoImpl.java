package com.stm.salesfast.backend.dao.impl;

import java.sql.Date;
import java.sql.Time;
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
																		+ "AND (hasMeetingUpdate = 0 OR hasMeetingExperienceFromSR = 0) "
																		+ "AND (confirmationStatus = 'CONFIRMED' OR "
																		+ "confirmationStatus = 'CANCELLED') "
																		+ "ORDER BY time";
	
	private static final String INSERT = "INSERT INTO appointment "+
										"(time, date, physicianId, userId, productId, confirmationStatus, zip, cancellationReason, additionalNotes) "+
										"VALUES (?,?,?,?,?,?,?,?,?)";
	private static final String FETCHID_BY_PHYS_USER = "SELECT appointmentId FROM appointment WHERE userId = ? AND physicianId = ?";
	private static final String FETCHID_BY_PHYS_USER_PROD = "SELECT appointmentId FROM appointment WHERE userId = ? AND physicianId = ? AND productId = ?";
	private static final String UPDATE_HASMEETINGUPDATE = "UPDATE `salesfast`.`appointment` "
														+"SET `hasMeetingUpdate` = ? "
														+"WHERE `appointmentId` = ?;";
	
	private static final String UPDATE_HASMEETINGEXPERIENCE_FROM_SR = "UPDATE `salesfast`.`appointment` "
														+"SET `hasMeetingExperienceFromSR` = ? "
														+"WHERE `appointmentId` = ?";
	
	private static final String UPDATE_HASMEETINGEXPERIENCE_FROM_PH = "UPDATE `salesfast`.`appointment` "
														+"SET `hasMeetingExperienceFromPH` = ? "
														+"WHERE `appointmentId` = ?";
	
	
	private static final String UPDATE_STATUS = "UPDATE appointment SET confirmationStatus = ?,"
												+ "cancellationReason = ?"
												+ "WHERE appointmentId = ?";
	private static final String FETCH_APPOINTMENT_BY_STATUS = "SELECT * FROM appointment WHERE confirmationStatus = ? AND userId = ?";
	
	private static final String UPDATE_FOLLOWUP_APPOINTMENT = "UPDATE appointment SET time = ?, "
															+ "date = ?, "
															+ "confirmationStatus = ?, "
															+ "additionalNotes = ? "
															+ "WHERE appointmentId = ?";
	
	private static final String FETCH_BY_PHYS = "SELECT * FROM appointment WHERE (confirmationStatus = ? OR confirmationStatus = ? ) AND physicianId = ?";
	
	@Override
	public AppointmentDto getAppointmentById(int appointmentId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(FETCH_BY_ID, (rs, rownnum)->{
				return new AppointmentDto(appointmentId, rs.getTime("time"), rs.getDate("date"), rs.getInt("physicianId"), rs.getInt("userId"), rs.getInt("productId"), rs.getString("confirmationStatus"), rs.getString("zip"), rs.getString("cancellationReason"), rs.getString("additionalNotes"), rs.getBoolean("hasMeetingUpdate"),rs.getBoolean("hasMeetingExperienceFromSR"),rs.getBoolean("hasMeetingExperienceFromPH"));
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
				return new AppointmentDto(rs.getInt("appointmentId"), rs.getTime("time"), rs.getDate("date"), rs.getInt("physicianId"), userId, rs.getInt("productId"), rs.getString("confirmationStatus"), rs.getString("zip"),rs.getString("cancellationReason"), rs.getString("additionalNotes"), rs.getBoolean("hasMeetingUpdate"),rs.getBoolean("hasMeetingExperienceFromSR"),rs.getBoolean("hasMeetingExperienceFromPH"));
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
				ps.setString(8, appointment.getCancellationReason());
				ps.setString(9, appointment.getAdditionalNotes());
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
	public void updateFollowUps(Time time, Date date,  String status,  String additionalNotes,int appointmentId) {
		// TODO Auto-generated method stub
		try{
			jdbcTemplate.update(UPDATE_FOLLOWUP_APPOINTMENT, (ps)->{
				ps.setTime(1, time);
				ps.setDate(2, date);
				ps.setString(3, status);
				ps.setString(4, additionalNotes);
				ps.setInt(5, appointmentId);
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
	public int getIdByPhysIdUserIdProductId(int physicianId, int userId, int productId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(FETCHID_BY_PHYS_USER_PROD, (rs, rownum) -> {
				return rs.getInt("appointmentId");
				}, userId, physicianId, productId);
			
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
	public void setMeetinExperienceFlagFromSR(int appointmentId, int meetingExpFromSR) {
		// TODO Auto-generated method stub
		try{
			jdbcTemplate.update(UPDATE_HASMEETINGEXPERIENCE_FROM_SR, (ps)->{
				ps.setInt(1, meetingExpFromSR);
				ps.setInt(2, appointmentId);
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void setMeetinExperienceFlagFromPH(int appointmentId, int  meetingExpFromPH) {
		// TODO Auto-generated method stub
		try{
			jdbcTemplate.update(UPDATE_HASMEETINGEXPERIENCE_FROM_PH, (ps)->{
				ps.setInt(1, meetingExpFromPH);
				ps.setInt(2, appointmentId);
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the list of all appointments that are in
	 * follow up state
	 * */
	@Override
	public List<AppointmentDto> getAppointmentByStatus(String confirmationStatus,int userId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_APPOINTMENT_BY_STATUS, (rs, rownnum)->{
				return new AppointmentDto(rs.getInt("appointmentId"), rs.getTime("time"), rs.getDate("date"), rs.getInt("physicianId"), userId, rs.getInt("productId"),confirmationStatus, rs.getString("zip"),rs.getString("cancellationReason"), rs.getString("additionalNotes"), rs.getBoolean("hasMeetingUpdate"),rs.getBoolean("hasMeetingExperienceFromSR"),rs.getBoolean("hasMeetingExperienceFromPH"));
			}, confirmationStatus, userId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Returns the list of all appointments for physician
	 * that are not "CANCELLED"
	 * */
	@Override
	public List<AppointmentDto> getAppointmentForPhysician(String confirmationStatus1, String confirmationStatus2, int physicianId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_BY_PHYS, (rs, rownnum)->{
				return new AppointmentDto(rs.getInt("appointmentId"), rs.getTime("time"), rs.getDate("date"), rs.getInt("physicianId"), rs.getInt("userId"), rs.getInt("productId"),rs.getString("confirmationStatus"), rs.getString("zip"),rs.getString("cancellationReason"), rs.getString("additionalNotes"), rs.getBoolean("hasMeetingUpdate"),rs.getBoolean("hasMeetingExperienceFromSR"),rs.getBoolean("hasMeetingExperienceFromPH"));
			}, confirmationStatus1, confirmationStatus2, physicianId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
}
