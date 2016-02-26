package com.stm.salesfast.backend.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.stm.salesfast.backend.dto.AlignmentsDto;
import com.stm.salesfast.backend.dto.MeetingExperienceDto;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stm.salesfast.backend.dao.specs.MeetingExperienceDao;
import com.stm.salesfast.constant.PhysicianStatus;

@Repository
public class MeetingExperienceDaoImpl implements MeetingExperienceDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final String INSERT = "INSERT INTO meeting_experience "
			+ "(`isPhysicianEntry`, `isSalesRepEntry`, `status`, `likedTheProduct`, `likedPriceAffordability`, `impressiveLessSideEffects`, `likedPresentation`,"
			+ "`salesRepConfidence`,`impressiveCompanyReputation`,`appointmentId`)"
			+ "VALUES (?,?,?,?,?,?,?,?,?,?);";
	
	private static final String FETCH_BY_APPOINTMENTID = "SELECT * FROM meeting_experience WHERE appointmentId = ?";
	private static final String FETCH_SALESREP_ENTRIES= "SELECT * FROM meeting_experience WHERE isSalesRepEntry = 1";
	private static final String FETCH_PHYSICIAN_ENTRIES = "SELECT * FROM meeting_experience WHERE isPhysicianEntry = 1";
	
			
	
	@Override
	public void insert(MeetingExperienceDto meetingExperience) {
		// TODO Auto-generated method stub
		try{
			jdbcTemplate.update(INSERT,(ps)->{
				ps.setBoolean(1, meetingExperience.isPhysicianEntry());
				ps.setBoolean(2, meetingExperience.isSalesRepEntry());
				ps.setString(3, meetingExperience.getStatus());
				ps.setBoolean(4, meetingExperience.isLikedTheProduct());
				ps.setBoolean(5, meetingExperience.isLikedPriceAffordability());
				ps.setBoolean(6, meetingExperience.isImpressiveLessSideEffects());
				ps.setBoolean(7, meetingExperience.isLikedPresentation());
				ps.setBoolean(8, meetingExperience.isSalesRepConfidence());
				ps.setBoolean(9, meetingExperience.isImpressiveCompanyReputation());
				ps.setInt(10, meetingExperience.getAppointmentId());
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
	}

	@Override
	public List<MeetingExperienceDto> getByAppointmentId(int appointmentId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_BY_APPOINTMENTID, (rs, rownum) -> {
					return new MeetingExperienceDto(rs.getInt("meetingExperienceId"), rs.getBoolean("isPhysicianEntry"), rs.getBoolean("isSalesRepEntry"), rs.getString("status"),rs.getBoolean("likedTheProduct"),rs.getBoolean("likedPriceAffordability"), rs.getBoolean("impressiveLessSideEffects"), rs.getBoolean("likedPresentation"), rs.getBoolean("salesRepConfidence"), rs.getBoolean("impressiveCompanyReputation"), appointmentId);
				}, appointmentId);
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<MeetingExperienceDto> getSalesRepEntries() {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_SALESREP_ENTRIES, (rs, rownum) -> {
					return new MeetingExperienceDto(rs.getInt("meetingExperienceId"), rs.getBoolean("isPhysicianEntry"), rs.getBoolean("isSalesRepEntry"), rs.getString("status"),rs.getBoolean("likedTheProduct"),rs.getBoolean("likedPriceAffordability"), rs.getBoolean("impressiveLessSideEffects"), rs.getBoolean("likedPresentation"), rs.getBoolean("salesRepConfidence"), rs.getBoolean("impressiveCompanyReputation"), rs.getInt("appointmentId"));
				});
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<MeetingExperienceDto> getPhysicianEntries() {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_PHYSICIAN_ENTRIES, (rs, rownum) -> {
					return new MeetingExperienceDto(rs.getInt("meetingExperienceId"), rs.getBoolean("isPhysicianEntry"), rs.getBoolean("isSalesRepEntry"), rs.getString("status"),rs.getBoolean("likedTheProduct"),rs.getBoolean("likedPriceAffordability"), rs.getBoolean("impressiveLessSideEffects"), rs.getBoolean("likedPresentation"), rs.getBoolean("salesRepConfidence"), rs.getBoolean("impressiveCompanyReputation"), rs.getInt("appointmentId"));
				});
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

}
