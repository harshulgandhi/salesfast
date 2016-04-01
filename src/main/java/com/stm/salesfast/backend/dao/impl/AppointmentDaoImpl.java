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
																		+ "ORDER BY startTime";
	
	private static final String INSERT = "INSERT INTO appointment "+
										"(startTime, endTime, date, physicianId, userId, productId, confirmationStatus, zip, cancellationReason, additionalNotes) "+
										"VALUES (?,?,?,?,?,?,?,?,?,?)";
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
	
	private static final String UPDATE_APPOINTMENT = "UPDATE appointment SET startTime = ?, "
															+ "endTime = ?, "
															+ "date = ?, "
															+ "confirmationStatus = ?, "
															+ "additionalNotes = ? "
															+ "WHERE appointmentId = ?";
	
	private static final String FETCH_BY_PHYS = "SELECT * FROM appointment WHERE (confirmationStatus = ? OR confirmationStatus = ? ) AND physicianId = ?";
	
	private static final String FETCH_NOT_INTERESTED_PHY_FORUSER = "SELECT physicianId FROM appointment WHERE confirmationStatus = 'NOT INTERESTED' AND userId = ? GROUP BY physicianId";
	
	private static final String UPDATE_HAS_PITCH = "UPDATE appointment SET hasPitch = 1 WHERE appointmentId = ?";
	
	private static final String FETCH_FOR_MED_FIELD = "SELECT * FROM appointment "
			+ "WHERE productId in ( "
			+ "SELECT productId FROM products WHERE "
			+ "medicalFieldId = ?) AND hasPitch = 1";
	private static final String FETCH_FOR_PRODUCT = "SELECT * FROM appointment "
			+ "WHERE productId = ? AND hasPitch = 1";
	private static final String FETCH_FOR_SALESREP= "SELECT * FROM appointment "
			+ "WHERE userId = ? AND hasPitch = 1";
	private static final String FETCH_FOR_PHYS = "SELECT * FROM appointment "
			+ "WHERE physicianId = ? AND hasPitch = 1";
	private static final String FETCH_ALL_HAVING_PITCH = "SELECT * FROM appointment "
			+ "WHERE hasPitch = 1";
	private static final String FETCH_PAST_APPOINTMENTS = "SELECT * FROM appointment "
			+ "WHERE userId = ? AND "
			+ "date < DATE_SUB(NOW(),INTERVAL 0 YEAR) "
			+ "ORDER BY date desc";
	
	
	@Override
	public AppointmentDto getAppointmentById(int appointmentId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(FETCH_BY_ID, (rs, rownnum)->{
				return new AppointmentDto(appointmentId, rs.getTime("startTime"), rs.getTime("endTime"),  rs.getDate("date"), rs.getInt("physicianId"), rs.getInt("userId"), rs.getInt("productId"), rs.getString("confirmationStatus"), rs.getString("zip"), rs.getString("cancellationReason"), rs.getString("additionalNotes"), rs.getBoolean("hasMeetingUpdate"),rs.getBoolean("hasMeetingExperienceFromSR"),rs.getBoolean("hasMeetingExperienceFromPH"), rs.getBoolean("hasPitch"));
			}, appointmentId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * For fetching all pitches*/
	@Override
	public List<AppointmentDto> getAppointmentByMedicalField(String medicalFieldId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_FOR_MED_FIELD, (rs, rownnum)->{
				return new AppointmentDto(rs.getInt("appointmentId"), rs.getTime("startTime"), rs.getTime("endTime"),  rs.getDate("date"), rs.getInt("physicianId"), rs.getInt("userId"), rs.getInt("productId"), rs.getString("confirmationStatus"), rs.getString("zip"), rs.getString("cancellationReason"), rs.getString("additionalNotes"), rs.getBoolean("hasMeetingUpdate"),rs.getBoolean("hasMeetingExperienceFromSR"),rs.getBoolean("hasMeetingExperienceFromPH"), rs.getBoolean("hasPitch"));
			}, medicalFieldId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * For fetching all pitches*/
	@Override
	public List<AppointmentDto> getAppointmentByProduct(int productId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_FOR_PRODUCT, (rs, rownnum)->{
				return new AppointmentDto(rs.getInt("appointmentId"), rs.getTime("startTime"), rs.getTime("endTime"),  rs.getDate("date"), rs.getInt("physicianId"), rs.getInt("userId"), rs.getInt("productId"), rs.getString("confirmationStatus"), rs.getString("zip"), rs.getString("cancellationReason"), rs.getString("additionalNotes"), rs.getBoolean("hasMeetingUpdate"),rs.getBoolean("hasMeetingExperienceFromSR"),rs.getBoolean("hasMeetingExperienceFromPH"), rs.getBoolean("hasPitch"));
			}, productId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * For fetching all pitches*/
	@Override
	public List<AppointmentDto> getAppointmentBySalesRep(int userId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_FOR_SALESREP, (rs, rownnum)->{
				return new AppointmentDto(rs.getInt("appointmentId"), rs.getTime("startTime"), rs.getTime("endTime"),  rs.getDate("date"), rs.getInt("physicianId"), rs.getInt("userId"), rs.getInt("productId"), rs.getString("confirmationStatus"), rs.getString("zip"), rs.getString("cancellationReason"), rs.getString("additionalNotes"), rs.getBoolean("hasMeetingUpdate"),rs.getBoolean("hasMeetingExperienceFromSR"),rs.getBoolean("hasMeetingExperienceFromPH"), rs.getBoolean("hasPitch"));
			}, userId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * For fetching all pitches*/
	@Override
	public List<AppointmentDto> getAppointmentByPhysician(int physicianId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_FOR_PHYS, (rs, rownnum)->{
				return new AppointmentDto(rs.getInt("appointmentId"), rs.getTime("startTime"), rs.getTime("endTime"),  rs.getDate("date"), rs.getInt("physicianId"), rs.getInt("userId"), rs.getInt("productId"), rs.getString("confirmationStatus"), rs.getString("zip"), rs.getString("cancellationReason"), rs.getString("additionalNotes"), rs.getBoolean("hasMeetingUpdate"),rs.getBoolean("hasMeetingExperienceFromSR"),rs.getBoolean("hasMeetingExperienceFromPH"), rs.getBoolean("hasPitch"));
			}, physicianId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * For fetching all pitches*/
	@Override
	public List<AppointmentDto> getAllAppointmentHavingPitch() {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_ALL_HAVING_PITCH, (rs, rownnum)->{
				return new AppointmentDto(rs.getInt("appointmentId"), rs.getTime("startTime"), rs.getTime("endTime"),  rs.getDate("date"), rs.getInt("physicianId"), rs.getInt("userId"), rs.getInt("productId"), rs.getString("confirmationStatus"), rs.getString("zip"), rs.getString("cancellationReason"), rs.getString("additionalNotes"), rs.getBoolean("hasMeetingUpdate"),rs.getBoolean("hasMeetingExperienceFromSR"),rs.getBoolean("hasMeetingExperienceFromPH"), rs.getBoolean("hasPitch"));
			});
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
				return new AppointmentDto(rs.getInt("appointmentId"), rs.getTime("startTime"), rs.getTime("endTime"), rs.getDate("date"), rs.getInt("physicianId"), userId, rs.getInt("productId"), rs.getString("confirmationStatus"), rs.getString("zip"),rs.getString("cancellationReason"), rs.getString("additionalNotes"), rs.getBoolean("hasMeetingUpdate"),rs.getBoolean("hasMeetingExperienceFromSR"),rs.getBoolean("hasMeetingExperienceFromPH"), rs.getBoolean("hasPitch"));
			}, userId);
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
	public List<AppointmentDto> getPastAppointmentByUserId(int userId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_PAST_APPOINTMENTS, (rs, rownnum)->{
				return new AppointmentDto(rs.getInt("appointmentId"), rs.getTime("startTime"), rs.getTime("endTime"), rs.getDate("date"), rs.getInt("physicianId"), userId, rs.getInt("productId"), rs.getString("confirmationStatus"), rs.getString("zip"),rs.getString("cancellationReason"), rs.getString("additionalNotes"), rs.getBoolean("hasMeetingUpdate"),rs.getBoolean("hasMeetingExperienceFromSR"),rs.getBoolean("hasMeetingExperienceFromPH"), rs.getBoolean("hasPitch"));
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
				ps.setTime(1, appointment.getStartTime());
				ps.setTime(2, appointment.getEndTime());
				ps.setDate(3, appointment.getDate());
				ps.setInt(4, appointment.getPhysicianId());
				ps.setInt(5, appointment.getUserId());
				ps.setInt(6, appointment.getProductId());
				ps.setString(7, appointment.getConfirmationStatus());
				ps.setString(8, appointment.getZip());
				ps.setString(9, appointment.getCancellationReason());
				ps.setString(10, appointment.getAdditionalNotes());
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
	public void updateAppointment(Time startTime, Time endTime, Date date,  String status,  String additionalNotes,int appointmentId) {
		// TODO Auto-generated method stub
		try{
			jdbcTemplate.update(UPDATE_APPOINTMENT, (ps)->{
				ps.setTime(1, startTime);
				ps.setTime(2, endTime);
				ps.setDate(3, date);
				ps.setString(4, status);
				ps.setString(5, additionalNotes);
				ps.setInt(6, appointmentId);
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void updatePitchFlagTrue(int appointmentId) {
		// TODO Auto-generated method stub
		try{
			jdbcTemplate.update(UPDATE_HAS_PITCH, (ps)->{
				ps.setInt(1, appointmentId);
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
				return new AppointmentDto(rs.getInt("appointmentId"), rs.getTime("startTime"), rs.getTime("endTime"), rs.getDate("date"), rs.getInt("physicianId"), userId, rs.getInt("productId"),confirmationStatus, rs.getString("zip"),rs.getString("cancellationReason"), rs.getString("additionalNotes"), rs.getBoolean("hasMeetingUpdate"),rs.getBoolean("hasMeetingExperienceFromSR"),rs.getBoolean("hasMeetingExperienceFromPH"), rs.getBoolean("hasPitch"));
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
				return new AppointmentDto(rs.getInt("appointmentId"),rs.getTime("startTime"), rs.getTime("endTime"), rs.getDate("date"), rs.getInt("physicianId"), rs.getInt("userId"), rs.getInt("productId"),rs.getString("confirmationStatus"), rs.getString("zip"),rs.getString("cancellationReason"), rs.getString("additionalNotes"), rs.getBoolean("hasMeetingUpdate"),rs.getBoolean("hasMeetingExperienceFromSR"),rs.getBoolean("hasMeetingExperienceFromPH"), rs.getBoolean("hasPitch"));
			}, confirmationStatus1, confirmationStatus2, physicianId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Integer> getNotInterestedPhysicians(int userId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_NOT_INTERESTED_PHY_FORUSER, (rs, rownnum)->{
				return rs.getInt("physicianId");
			}, userId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

}
