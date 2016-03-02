package com.stm.salesfast.backend.dao.impl;

import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stm.salesfast.backend.dao.specs.PhysicianStgDao;
import com.stm.salesfast.backend.dto.PhysicianStgDto;
import com.stm.salesfast.backend.dto.UserDto;

@Repository
public class PhysicianStgDaoImpl implements PhysicianStgDao {
	
	Logger log = LoggerFactory.getLogger(PhysicianStgDaoImpl.class.getName());	
	
	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	private static final String FETCH_BY_ID = "SELECT * FROM physicians_staging WHERE physicianId = ?";
	private static final String INSERT_USER = "INSERT INTO Physicians_Staging "+
	" (firstName, lastName, email, contactNumber, addressLineOne, addressLineTwo, city, state, zip, medicalField, isNew, status) " +
	" VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String FETCH_ZIP_BYID = "SELECT zip FROM physicians_staging WHERE physicianId = ?";
	private static final String FETCH_ALL = "SELECT * FROM physicians_staging";
	private static final String UPDATE_IMPORTANCE = "UPDATE physicians_staging SET "
													+ " importanceFactor = ? "
													+ " WHERE physicianId = ?";
	
	
	@Override
	public PhysicianStgDto getBy(int physicianId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(FETCH_BY_ID, (rs, rownum) -> {
				return new PhysicianStgDto(physicianId, rs.getString("firstName"), rs.getString("lastName"),rs.getString("email"),rs.getString("contactNumber"),rs.getString("addressLineOne"),rs.getString("addressLineTwo"),rs.getString("city"),rs.getString("state"),rs.getString("zip"), rs.getString("medicalField"), rs.getBoolean("isNew"), rs.getString("status"),rs.getDate("practiceStartDate"), rs.getDouble("importanceFactor"));
				}, physicianId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void insert(PhysicianStgDto physician) {
		// TODO Auto-generated method stub
		jdbcTemplate.update(INSERT_USER, (ps) -> {
			ps.setString(1, physician.getFirstName());
			ps.setString(2, physician.getLastName());
			ps.setString(3, physician.getEmail());
			ps.setString(4, physician.getContactNumber());
			ps.setString(5, physician.getAddressLineOne());
			ps.setString(6, physician.getAddressLineTwo());
			ps.setString(7, physician.getCity());
			ps.setString(8, physician.getState());
			ps.setString(9, physician.getZip());
			ps.setString(10, physician.getMedicalField());
			ps.setBoolean(11, physician.isNew());
			ps.setString(12, physician.getStatus());
		});
	}
	
	@Override
	public void updateImportance(double importanceFactor, int physicianId) {
		// TODO Auto-generated method stub
		jdbcTemplate.update(UPDATE_IMPORTANCE, (ps) -> {
			ps.setDouble(1, importanceFactor);
			ps.setInt(2, physicianId);
		});
	}


	@Override
	public void deleteBy(int physicianId) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getZipById(int physicianId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(FETCH_ZIP_BYID, (rs, rownum) -> {
				return new String(rs.getString("zip"));
				}, physicianId);
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<PhysicianStgDto> getAll() {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_ALL, (rs, rownum) -> {
				return new PhysicianStgDto(rs.getInt("physicianId"), rs.getString("firstName"), rs.getString("lastName"),rs.getString("email"),rs.getString("contactNumber"),rs.getString("addressLineOne"),rs.getString("addressLineTwo"),rs.getString("city"),rs.getString("state"),rs.getString("zip"), rs.getString("medicalField"), rs.getBoolean("isNew"), rs.getString("status"),rs.getDate("practiceStartDate"),rs.getDouble("importanceFactor"));
				});
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

}
