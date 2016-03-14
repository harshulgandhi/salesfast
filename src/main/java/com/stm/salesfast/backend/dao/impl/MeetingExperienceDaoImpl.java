package com.stm.salesfast.backend.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.stm.salesfast.backend.dto.MeetingExperienceDto;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stm.salesfast.backend.dao.specs.MeetingExperienceDao;

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
	private static final String FETCH_ALL = "SELECT * FROM meeting_experience";
	
	private static final String COUNT_ALL = "SELECT count(*) FROM meeting_experience";
	private static final String COUNT_ALL_PHY = "SELECT count(*) FROM meeting_experience WHERE isPhysicianEntry = 1";
	private static final String COUNT_ALL_SR = "SELECT count(*) FROM meeting_experience WHERE isSalesRepEntry = 1";
	private static final String COUNT_LIKED_PROD = "SELECT count(*) FROM meeting_experience WHERE isPhysicianEntry = ? "
			+ "AND isSalesRepEntry = ? "
			+ "AND likedTheProduct = 1";
	private static final String COUNT_AFFORDABLE = "SELECT count(*) FROM meeting_experience WHERE isPhysicianEntry = ? "
			+ "AND isSalesRepEntry = ? "
			+ "AND likedPriceAffordability = 1";
	private static final String COUNT_SIDEEFECTS = "SELECT count(*) FROM meeting_experience WHERE isPhysicianEntry = ? "
			+ "AND isSalesRepEntry = ? "
			+ "AND impressiveLessSideEffects = 1";
	private static final String COUNT_PRESENTATION = "SELECT count(*) FROM meeting_experience WHERE isPhysicianEntry = ? "
			+ "AND isSalesRepEntry = ? "
			+ "AND likedPresentation = 1";
	private static final String COUNT_CONFIDENCE = "SELECT count(*) FROM meeting_experience WHERE isPhysicianEntry = ? "
			+ "AND isSalesRepEntry = ? "
			+ "AND salesRepConfidence = 1";
	private static final String COUNT_REPUTATION = "SELECT count(*) FROM meeting_experience WHERE isPhysicianEntry = ? "
			+ "AND isSalesRepEntry = ? "
			+ "AND impressiveCompanyReputation = 1";
	
	
	private static final String COUNT_ALL_LOST = "SELECT count(*) FROM meeting_experience WHERE "
			+ "appointmentId IN "
			+ "(SELECT appointmentId FROM meeting_update WHERE "
			+ "status = 'LOST')";
	private static final String COUNT_LIKED_PROD_LOST = "SELECT count(*) FROM meeting_experience WHERE "
			+ "likedTheProduct = 1 AND appointmentId IN "
			+ "(SELECT appointmentId FROM meeting_update WHERE "
			+ "status = 'LOST')";
	private static final String COUNT_AFFORDABLE_LOST = "SELECT count(*) FROM meeting_experience WHERE "
			+ "likedPriceAffordability = 1 AND appointmentId IN "
			+ "(SELECT appointmentId FROM meeting_update WHERE "
			+ "status = 'LOST')";
	private static final String COUNT_SIDEEFECTS_LOST = "SELECT count(*) FROM meeting_experience WHERE "
			+ "impressiveLessSideEffects = 1 AND appointmentId IN "
			+ "(SELECT appointmentId FROM meeting_update WHERE "
			+ "status = 'LOST')";
	private static final String COUNT_PRESENTATION_LOST = "SELECT count(*) FROM meeting_experience WHERE "
			+ "likedPresentation = 1 AND appointmentId IN "
			+ "(SELECT appointmentId FROM meeting_update WHERE "
			+ "status = 'LOST')";
	private static final String COUNT_CONFIDENCE_LOST = "SELECT count(*) FROM meeting_experience WHERE "
			+ "salesRepConfidence = 1 AND appointmentId IN "
			+ "(SELECT appointmentId FROM meeting_update WHERE "
			+ "status = 'LOST')";
	private static final String COUNT_REPUTATION_LOST = "SELECT count(*) FROM meeting_experience WHERE "
			+ "impressiveCompanyReputation = 1 AND appointmentId IN "
			+ "(SELECT appointmentId FROM meeting_update WHERE "
			+ "status = 'LOST')";
	
	
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
	
	@Override
	public List<MeetingExperienceDto> getAll() {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_ALL, (rs, rownum) -> {
					return new MeetingExperienceDto(rs.getInt("meetingExperienceId"), rs.getBoolean("isPhysicianEntry"), rs.getBoolean("isSalesRepEntry"), rs.getString("status"),rs.getBoolean("likedTheProduct"),rs.getBoolean("likedPriceAffordability"), rs.getBoolean("impressiveLessSideEffects"), rs.getBoolean("likedPresentation"), rs.getBoolean("salesRepConfidence"), rs.getBoolean("impressiveCompanyReputation"), rs.getInt("appointmentId"));
				});
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public int countAll(){
		try {
			return jdbcTemplate.queryForObject(COUNT_ALL, (rs, rownum) -> {
				return rs.getInt(1);
			});
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int countAllPhy(){
		try {
			return jdbcTemplate.queryForObject(COUNT_ALL_PHY, (rs, rownum) -> {
				return rs.getInt(1);
			});
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int countAllSR(){
		try {
			return jdbcTemplate.queryForObject(COUNT_ALL_SR, (rs, rownum) -> {
				return rs.getInt(1);
			});
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int countLikedProduct(int isPhy, int isSR){
		try {
			return jdbcTemplate.queryForObject(COUNT_LIKED_PROD, (rs, rownum) -> {
				return rs.getInt(1);
			},isPhy,isSR);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int countPriceAffordability(int isPhy, int isSR){
		try {
			return jdbcTemplate.queryForObject(COUNT_AFFORDABLE, (rs, rownum) -> {
				return rs.getInt(1);
			},isPhy,isSR);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int countLessSideEffects(int isPhy, int isSR){
		try {
			return jdbcTemplate.queryForObject(COUNT_SIDEEFECTS, (rs, rownum) -> {
				return rs.getInt(1);
			},isPhy,isSR);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int countLikedPresentation(int isPhy, int isSR){
		try {
			return jdbcTemplate.queryForObject(COUNT_PRESENTATION, (rs, rownum) -> {
				return rs.getInt(1);
			},isPhy,isSR);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int countRepsConfidence(int isPhy, int isSR){
		try {
			return jdbcTemplate.queryForObject(COUNT_CONFIDENCE, (rs, rownum) -> {
				return rs.getInt(1);
			},isPhy,isSR);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int countOrgReputation(int isPhy, int isSR){
		try {
			return jdbcTemplate.queryForObject(COUNT_REPUTATION, (rs, rownum) -> {
				return rs.getInt(1);
			},isPhy,isSR);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}
	

	@Override
	public int countAll_Lost(){
		try {
			return jdbcTemplate.queryForObject(COUNT_ALL_LOST, (rs, rownum) -> {
				return rs.getInt(1);
			});
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int countLikedProduct_Lost(){
		try {
			return jdbcTemplate.queryForObject(COUNT_LIKED_PROD_LOST, (rs, rownum) -> {
				return rs.getInt(1);
			});
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int countPriceAffordabilityLost(){
		try {
			return jdbcTemplate.queryForObject(COUNT_AFFORDABLE_LOST, (rs, rownum) -> {
				return rs.getInt(1);
			});
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int countLessSideEffectsLost(){
		try {
			return jdbcTemplate.queryForObject(COUNT_SIDEEFECTS_LOST, (rs, rownum) -> {
				return rs.getInt(1);
			});
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int countLikedPresentationLost(){
		try {
			return jdbcTemplate.queryForObject(COUNT_PRESENTATION_LOST, (rs, rownum) -> {
				return rs.getInt(1);
			});
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int countRepsConfidenceLost(){
		try {
			return jdbcTemplate.queryForObject(COUNT_CONFIDENCE_LOST, (rs, rownum) -> {
				return rs.getInt(1);
			});
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int countOrgReputationLost(){
		try {
			return jdbcTemplate.queryForObject(COUNT_REPUTATION_LOST, (rs, rownum) -> {
				return rs.getInt(1);
			});
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
