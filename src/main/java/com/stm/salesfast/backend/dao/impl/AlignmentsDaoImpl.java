package com.stm.salesfast.backend.dao.impl;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stm.salesfast.backend.controllers.LoginController;
import com.stm.salesfast.backend.dao.specs.AlignmentsDao;
import com.stm.salesfast.backend.dto.AlignmentsDto;
import com.stm.salesfast.backend.dto.UserAccountDto;
import com.stm.salesfast.backend.dto.UserDto;

@Repository
public class AlignmentsDaoImpl implements AlignmentsDao {
	
	private Logger log = LoggerFactory.getLogger(AlignmentsDaoImpl.class.getName());
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String INSERT = "INSERT INTO alignments "
			+ "(physicianId, userId, territoryId, districtId, zip, productId) "
			+ "VALUES (?,?,?,?,?,?)";
	private static final String FETCH_BY_ID = "SELECT * FROM alignments WHERE alignmentId = ?";
	private static final String FETCH_BY_USERID = "SELECT * FROM alignments WHERE userId = ?";
	private static final String FETCH_BY_PHYSICIANID = "SELECT * FROM alignments WHERE physicianId = ?";
	private static final String FETCH_BY_USERID_ZIP = "SELECT * FROM alignments WHERE userId = ? and zip = ?";
	private static final String FETCH_BY_USERIDPHYSICIANID = "SELECT * FROM alignments WHERE userId = ? and physicianId = ?";
	private static final String DELETE_BY_USER_PHYSICIAN_PRODUCT = "DELETE FROM alignments WHERE userId = ? AND physicianId = ? AND productId = ?";
	
	/*This query fetches only those alignments for user that  are not in appointments table*/
	private static final String FETCH_BY_USERID_NOTIN_APPOINTMENTS = "SELECT * FROM alignments WHERE alignments.userId = ? AND "
																	+ "NOT EXISTS (SELECT 1 from appointment WHERE "
																	+ "(appointment.physicianId = alignments.physicianId AND "
																	+ "appointment.userId = alignments.userId AND "
																	+ "appointment.productId = alignments.productId) AND "
																	+ "appointment.confirmationStatus != 'CANCELLED')";
	
	/*This query fetches alignments to physicians available in close vicinity to physicians who have confirmed appointments*/
	private static final String FETCH_VICINITY = "SELECT * FROM alignments WHERE alignments.userId = ? AND alignments.zip IN "
												+ "(SELECT appointment.zip FROM appointment WHERE appointment.userId = ? GROUP BY zip) "
												+ "AND NOT EXISTS (SELECT 1 from appointment WHERE "
												+ "appointment.physicianId = alignments.physicianId AND "
												+ "appointment.userId = alignments.userId AND "
												+ "appointment.productId = alignments.productId)";
	private static final String FETCH_BY_PHYSICIAN_PRODUCT = "SELECT * FROM alignments WHERE physicianId = ? and productId = ?";
	
	private static final String FETCH_USER_FOR_MEDICAL_FIELD = "SELECT userId from alignments WHERE productId IN "
															+ "(SELECT productId FROM products WHERE medicalFieldId = ?) "
															+ "GROUP BY userId";
	private static final String DELETE_BY_ID = "DELETE FROM alignments WHERE allignmentId = ?";
	
	private static final String FETCH_ALIGNMENT_SUGGESTION_MANUAL_ALIGNMENT = "SELECT * FROM salesfast.alignments "
															+ "WHERE userId <> ? AND productId IN (SELECT "
															+ "productId FROM products WHERE medicalFieldId =  "
															+ "(SELECT medicalFieldId FROM training_material "
															+ "WHERE userId = ? GROUP BY medicalFieldId)) "
															+ "AND NOT EXISTS (SELECT 1 from appointment WHERE "
															+ "(appointment.physicianId = alignments.physicianId AND "
															+ "appointment.userId = alignments.userId AND "
															+ "appointment.productId = alignments.productId) AND "
															+ "appointment.confirmationStatus != 'CANCELLED')";
	
	@Override
	public AlignmentsDto getAlignmentById(int alignmentId) {
		try{
			return jdbcTemplate.queryForObject(FETCH_BY_ID, (rs, rownum) -> {
				return new AlignmentsDto(alignmentId, rs.getInt("physicianId"), rs.getInt("userId"),rs.getInt("territoryId"),rs.getInt("districtId"),rs.getString("zip"), rs.getInt("productId"));
				}, alignmentId);
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public AlignmentsDto getAlignmentByPhysicianId(int physicianId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(FETCH_BY_PHYSICIANID, (rs, rownum) -> {
				return new AlignmentsDto(rs.getInt("alignmentId"), physicianId, rs.getInt("userId"),rs.getInt("territoryId"),rs.getInt("districtId"),rs.getString("zip"), rs.getInt("productId"));
				}, physicianId);
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<AlignmentsDto> getAlignmentByUserId(int userId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_BY_USERID, (rs, rownum) -> {
				return new AlignmentsDto(rs.getInt("alignmentId"), rs.getInt("physicianId"), userId,rs.getInt("territoryId"),rs.getInt("districtId"),rs.getString("zip"), rs.getInt("productId"));
				}, userId);
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<AlignmentsDto> getAlignmentsForSuggestions(int userId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_ALIGNMENT_SUGGESTION_MANUAL_ALIGNMENT, (rs, rownum) -> {
				return new AlignmentsDto(rs.getInt("alignmentId"), rs.getInt("physicianId"), rs.getInt("userId"),rs.getInt("territoryId"),rs.getInt("districtId"),rs.getString("zip"), rs.getInt("productId"));
				}, userId, userId);
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<AlignmentsDto> getAlignmentByUserIdZip(int userId, String zip) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(
					FETCH_BY_USERID_ZIP,
					ps -> {
						ps.setInt(1, userId);
						ps.setString(2, zip);
						},(rs, rownum) -> {
							return new AlignmentsDto(rs.getInt("alignmentId"), rs.getInt("physicianId"), userId,rs.getInt("territoryId"),rs.getInt("districtId"),zip, rs.getInt("productId"));
						});
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void insertAlignment(AlignmentsDto alignmentDto) throws IOException {
		// TODO Auto-generated method stub
		try{
			jdbcTemplate.update(INSERT,(ps)->{
				ps.setInt(1, alignmentDto.getPhysicianId());
				ps.setInt(2, alignmentDto.getUserId());
				ps.setInt(3, alignmentDto.getTerritoryId());
				ps.setInt(4, alignmentDto.getDistrictId());
				ps.setString(5, alignmentDto.getZip());
				ps.setInt(6, alignmentDto.getProductId());
			});
		}catch(DataAccessException e){
//			throw new IOException(e);
			log.info("######### Duplicate alignments generated");
		}
	}

	@Override
	public List<AlignmentsDto> getAlignmentByUserIdPhysId(int userId, int physicianId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(
					FETCH_BY_USERIDPHYSICIANID, (rs, rownum) -> {
						return new AlignmentsDto(rs.getInt("alignmentId"), physicianId, userId,rs.getInt("territoryId"),rs.getInt("districtId"),rs.getString("zip"), rs.getInt("productId"));}
					, userId, physicianId);
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteByUserPhysicianProduct(int userId, int physicianId, int productId) {
		// TODO Auto-generated method stub
		try{
			jdbcTemplate.update(DELETE_BY_USER_PHYSICIAN_PRODUCT, (ps)->{
				ps.setInt(1, userId);
				ps.setInt(2, physicianId);
				ps.setInt(3, productId);
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void deleteByID(int alignmentId) {
		// TODO Auto-generated method stub
		try{
			jdbcTemplate.update(DELETE_BY_ID, (ps)->{
				ps.setInt(1, alignmentId);
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		
	}

	/**
	 * To fetch alignments for a user that are not in 
	 * appointments table. This helps in removing those physicians
	 * from alignments table on the UI with whom user has fixed an
	 * appointment 
	 * @param userId: User id for which non-appointed alignments are to be fetched	
	 * */
	@Override
	public List<AlignmentsDto> getAlignmentByUserIdNotInAppointments(int userId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_BY_USERID_NOTIN_APPOINTMENTS, (rs, rownum) -> {
				return new AlignmentsDto(rs.getInt("alignmentId"), rs.getInt("physicianId"), userId,rs.getInt("territoryId"),rs.getInt("districtId"),rs.getString("zip"), rs.getInt("productId"));
				}, userId);
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * To fetch alignments for a user in the vicinity
	 * of appointments fixed for a particular day
	 * @param userId: User id for which non-appointed alignments are to be fetched
	 * @param zip: ZIP code in which alignment is to be looked up	
	 * */
	@Override
	public List<AlignmentsDto> getAlignmentByUserIdInVicinity(int userId) {
		try{
			List<AlignmentsDto> resultList = jdbcTemplate.query(FETCH_VICINITY, (rs, rownum) -> {
				return new AlignmentsDto(rs.getInt("alignmentId"), rs.getInt("physicianId"), userId,rs.getInt("territoryId"),rs.getInt("districtId"),rs.getString("zip"), rs.getInt("productId"));
				}, userId, userId);
			
			return resultList;
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public AlignmentsDto getAlignmentByPhysicianProductId(int physicianId, int productId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(FETCH_BY_PHYSICIAN_PRODUCT, (rs, rownum) -> {
				return new AlignmentsDto(rs.getInt("alignmentId"), physicianId, rs.getInt("userId"),rs.getInt("territoryId"),rs.getInt("districtId"),rs.getString("zip"), rs.getInt("productId"));
				}, physicianId, productId);
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Integer> getUsersForParticularMedicalField(String medicalField){
		try{
			return jdbcTemplate.query(FETCH_USER_FOR_MEDICAL_FIELD, (rs, rownum) -> {
				return rs.getInt("userId");
				}, medicalField);
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
}


