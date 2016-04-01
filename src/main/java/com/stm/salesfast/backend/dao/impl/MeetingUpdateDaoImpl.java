package com.stm.salesfast.backend.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stm.salesfast.backend.dao.specs.MeetingUpdateDao;
import com.stm.salesfast.backend.dto.MeetingExperienceDto;
import com.stm.salesfast.backend.dto.MeetingUpdateDto;
import com.stm.salesfast.constant.PhysicianStatus;

@Repository
public class MeetingUpdateDaoImpl implements MeetingUpdateDao {

	private Logger log = LoggerFactory.getLogger(MeetingUpdateDaoImpl.class.getName());
	private static final String FETCH_BY_APPOINTMENTID = "SELECT * FROM meeting_update WHERE appointmentId = ?";
	private static String INSERT = "INSERT INTO `salesfast`.`meeting_update` "
			+ "(`date`,`status`,`isEDetailed`,`physicianId`,`productId`,`medicalFieldId`,`appointmentId`) "
			+ "VALUES (?,?,?,?,?,?,?);";
	private static final String FETCH_FOR_PRESCRIBING = "SELECT * FROM meeting_update WHERE appointmentId  = ? AND status = 'PRESCRIBING'";
	private static final String FETCH_FOR_PHYS_PORTAL = "SELECT * FROM meeting_update WHERE (status = ? OR status = ? ) AND physicianId = ?";
	private static final String FETCH_LOST_PHY = "SELECT physicianId FROM meeting_update "
													+ "WHERE status='LOST' AND "
													+ "(physicianId, productId) IN "
													+ "(SELECT physicianId, productId from appointment "
													+ " WHERE userId = ?)";
	private static final String FETCH_PRESCRIBING_PHY = "SELECT physicianId FROM meeting_update "
													+ "WHERE status='PRESCRIBING' AND "
													+ "(physicianId, productId) IN "
													+ "(SELECT physicianId, productId from appointment "
													+ " WHERE userId = ?)";
	private static final String FETCH_STATUS_FOR_APPOINTMENTS = "SELECT status from meeting_update  "
													+ "WHERE appointmentId in "
													+ "(SELECT appointmentId from appointment "
													+ "WHERE userId = ? AND physicianId = ?)";
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public void insert(MeetingUpdateDto meetingUpdate) {
		// TODO Auto-generated method stub
		
		try{
			jdbcTemplate.update(INSERT,(ps)->{
				ps.setDate(1, meetingUpdate.getDate());
				ps.setString(2, PhysicianStatus.valueOf(meetingUpdate.getStatus()).toString());
				ps.setBoolean(3, meetingUpdate.isEDetailed());
				ps.setInt(4, meetingUpdate.getPhysicianId());
				ps.setInt(5, meetingUpdate.getProductId());
				ps.setString(6, meetingUpdate.getMedicalFieldId());
				ps.setInt(7, meetingUpdate.getAppointmentId());
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
	}

	@Override
	public MeetingUpdateDto getByAppointmentId(int appointmentId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(FETCH_BY_APPOINTMENTID, (rs, rownum) -> {
					return new MeetingUpdateDto(rs.getInt("meetingUpdateId"), rs.getDate("date"), rs.getString("status"), rs.getBoolean("isEDetailed"), rs.getInt("physicianId"), rs.getInt("productId"),rs.getString("medicalFieldId"),appointmentId);
				}, appointmentId);
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public List<MeetingUpdateDto> getByPrescribingPhysicians(int physicianId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_FOR_PRESCRIBING, (rs, rownum) -> {
					return new MeetingUpdateDto(rs.getInt("meetingUpdateId"), rs.getDate("date"), rs.getString("status"), rs.getBoolean("isEDetailed"), physicianId, rs.getInt("productId"),rs.getString("medicalFieldId"),rs.getInt("appointmentId"));
				}, physicianId);
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<MeetingUpdateDto> getForPhysiciansPortal(String status1, String status2, int physicianId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_FOR_PHYS_PORTAL, (rs, rownum) -> {
					return new MeetingUpdateDto(rs.getInt("meetingUpdateId"), rs.getDate("date"), rs.getString("status"), rs.getBoolean("isEDetailed"), physicianId, rs.getInt("productId"),rs.getString("medicalFieldId"),rs.getInt("appointmentId"));
				}, status1, status2, physicianId);
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Integer> getLostPhysiciansForUser(int userId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_LOST_PHY, (rs, rownum) -> {
					return rs.getInt("physicianId");
				}, userId);
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}	
	
	@Override
	public List<Integer> getPrescribingPhysiciansForUser(int userId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_PRESCRIBING_PHY, (rs, rownum) -> {
					return rs.getInt("physicianId");
				}, userId);
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<String> getStatusesByAppointments(int userId, int physicianId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_STATUS_FOR_APPOINTMENTS, (rs, rownum) -> {
					return rs.getString("status");
				}, userId);
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	
}
