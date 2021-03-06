package com.stm.salesfast.backend.dao.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stm.salesfast.backend.dao.specs.UserDao;
import com.stm.salesfast.backend.dto.UserDto;

@Repository
public class UserDaoImpl implements UserDao {
	
	Logger log = LoggerFactory.getLogger(UserDaoImpl.class.getName());	
	
	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	private static final String FETCH_BY_ID = "SELECT * FROM User WHERE userId = ?";
	private static final String FETCH_ALL = "SELECT * FROM User";
	private static final String INSERT_USER = "INSERT INTO USER "+
	" (firstName, lastName, email, contactNumber, addressLineOne, addressLineTwo, city, state, zip, startDate, endDate) " +
	" VALUES (?,?,?,?,?,?,?,?,?,?,?)";
	private static final String FETCH_BY_NAME = "SELECT * FROM salesfast.user where firstName = ?"
			+ " AND lastName = ? "
			+ " AND email = ?";
	private static final String FETCH_NON_PHYS_USERS = "SELECT * FROM user WHERE (firstName, "
													+ "lastName, email, contactNumber) NOT IN "
													+ "(SELECT firstName, lastName, email, "
													+ "contactNumber FROM physicians_staging)";
	
	
	@Override
	public UserDto getBy(int userId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(FETCH_BY_ID, (rs, rownum) -> {
				return new UserDto(userId, rs.getString("firstName"), rs.getString("lastName"),rs.getString("email"),rs.getString("contactNumber"),rs.getString("addressLineOne"),rs.getString("addressLineTwo"),rs.getString("city"),rs.getString("state"),rs.getString("zip"), rs.getDate("startDate"), rs.getDate("endDate"));
				}, userId);
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void insert(UserDto user) {
		// TODO Auto-generated method stub
		jdbcTemplate.update(INSERT_USER, (ps) -> {
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getContactNumber());
			ps.setString(5, user.getAddressLineOne());
			ps.setString(6, user.getAddressLineTwo());
			ps.setString(7, user.getCity());
			ps.setString(8, user.getState());
			ps.setString(9, user.getZip());
			ps.setDate(10, (Date) user.getStartDate());
			ps.setDate(11, (Date) user.getEndDate());
		});
	}

	@Override
	public void deleteBy(int userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<UserDto> getAll() {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_ALL, (rs, rownum) -> {
				return new UserDto(rs.getInt("userId"), rs.getString("firstName"), rs.getString("lastName"),rs.getString("email"),rs.getString("contactNumber"),rs.getString("addressLineOne"),rs.getString("addressLineTwo"),rs.getString("city"),rs.getString("state"),rs.getString("zip"), rs.getDate("startDate"), rs.getDate("endDate"));
				});
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public UserDto getBy(String firstName,String lastName, String email) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(FETCH_BY_NAME, (rs, rownum) -> {
				return new UserDto(rs.getInt("userId"), rs.getString("firstName"), rs.getString("lastName"),rs.getString("email"),rs.getString("contactNumber"),rs.getString("addressLineOne"),rs.getString("addressLineTwo"),rs.getString("city"),rs.getString("state"),rs.getString("zip"), rs.getDate("startDate"), rs.getDate("endDate"));
				},  firstName, lastName, email);
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<UserDto> getAllNonPhysicians() {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_NON_PHYS_USERS, (rs, rownum) -> {
				return new UserDto(rs.getInt("userId"), rs.getString("firstName"), rs.getString("lastName"),rs.getString("email"),rs.getString("contactNumber"),rs.getString("addressLineOne"),rs.getString("addressLineTwo"),rs.getString("city"),rs.getString("state"),rs.getString("zip"), rs.getDate("startDate"), rs.getDate("endDate"));
				});
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
}
