package com.stm.salesfast.backend.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stm.salesfast.backend.dao.specs.AlignmentsDao;
import com.stm.salesfast.backend.dto.AlignmentsDto;
import com.stm.salesfast.backend.dto.UserDto;

@Repository
public class AlignmentsDaoImpl implements AlignmentsDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String FETCH_BY_ID = "SELECT * FROM alignments WHERE alignmentId = ?";
	private static final String FETCH_BY_USERID = "SELECT * FROM alignments WHERE userId = ?";
	private static final String FETCH_BY_PHYSICIANID = "SELECT * FROM alignments WHERE physicianId = ?";
	private static final String FETCH_BY_USERID_ZIP = "SELECT * FROM alignments WHERE userId = ? and zip = ?";
	
	
	@Override
	public AlignmentsDto getAlignmentById(int alignmentId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(FETCH_BY_ID, (rs, rownum) -> {
				return new AlignmentsDto(alignmentId, rs.getInt("physicianId"), rs.getInt("userId"),rs.getInt("territoryId"),rs.getInt("districtId"),rs.getString("zip"));
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
				return new AlignmentsDto(rs.getInt("alignmentId"), physicianId, rs.getInt("userId"),rs.getInt("territoryId"),rs.getInt("districtId"),rs.getString("zip"));
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
				return new AlignmentsDto(rs.getInt("alignmentId"), rs.getInt("physicianId"), userId,rs.getInt("territoryId"),rs.getInt("districtId"),rs.getString("zip"));
				}, userId);
			
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
							return new AlignmentsDto(rs.getInt("alignmentId"), rs.getInt("physicianId"), userId,rs.getInt("territoryId"),rs.getInt("districtId"),zip);
						});
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

}
