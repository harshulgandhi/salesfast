package com.stm.salesfast.backend.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stm.salesfast.backend.dao.specs.AlignmentsDao;
import com.stm.salesfast.backend.dto.AlignmentsDto;
import com.stm.salesfast.backend.dto.UserAccountDto;
import com.stm.salesfast.backend.dto.UserDto;

@Repository
public class AlignmentsDaoImpl implements AlignmentsDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String INSERT = "INSERT INTO alignments "
			+ "(physicianId, userId, territoryId, districtId, zip, productId)"
			+ "VALUES = (?,?,?,?,?,?)";
	private static final String FETCH_BY_ID = "SELECT * FROM alignments WHERE alignmentId = ?";
	private static final String FETCH_BY_USERID = "SELECT * FROM alignments WHERE userId = ?";
	private static final String FETCH_BY_PHYSICIANID = "SELECT * FROM alignments WHERE physicianId = ?";
	private static final String FETCH_BY_USERID_ZIP = "SELECT * FROM alignments WHERE userId = ? and zip = ?";
	private static final String FETCH_BY_USERIDPHYSICIANID = "SELECT * FROM alignments WHERE userId = ? and physicianId = ?";
	
	
	@Override
	public AlignmentsDto getAlignmentById(int alignmentId) {
		// TODO Auto-generated method stub
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
	public void insertAlignment(AlignmentsDto alignmentDto) {
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
			e.printStackTrace();
		}
	}

	@Override
	public AlignmentsDto getAlignmentByUserIdPhysId(int userId, int physicianId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(
					FETCH_BY_USERIDPHYSICIANID, (rs, rownum) -> {
						return new AlignmentsDto(rs.getInt("alignmentId"), physicianId, userId,rs.getInt("territoryId"),rs.getInt("districtId"),rs.getString("zip"), rs.getInt("productId"));}
					, userId, physicianId);
			/*return (AlignmentsDto) jdbcTemplate.query(
					FETCH_BY_USERIDPHYSICIANID,
					ps -> {
						ps.setInt(1, userId);
						ps.setInt(2, physicianId);
						},(rs, rownum) -> {
							return new AlignmentsDto(rs.getInt("alignmentId"), physicianId, userId,rs.getInt("territoryId"),rs.getInt("districtId"),rs.getString("zip"), rs.getInt("productId"));
						});*/
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

}
