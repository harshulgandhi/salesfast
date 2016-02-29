package com.stm.salesfast.backend.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stm.salesfast.backend.dao.specs.UserAccountDao;
import com.stm.salesfast.backend.dto.UserAccountDto;

@Repository
public class UserAccountDaoImpl implements UserAccountDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private 
	static final String FETCH = "SELECT * FROM user_account where username = ?";
	static final String FETCH_BY_USERID = "SELECT * FROM user_account where userId = ?";
	
	@Override
	public void saveOrUpdateLoginCredentials(UserAccountDto loginCred) {
		// TODO Auto-generated method stub

	}

	@Override
	public UserAccountDto getLoginCredentials(String userName) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(
					FETCH, (rs, rownum) -> {
						return new UserAccountDto(rs.getInt("userAccountId"), userName, rs.getString("password"), rs.getInt("userId"));}
					, userName);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;	
	}

	@Override
	public UserAccountDto getLoginCredentialsByUserId(int userId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(
					FETCH, (rs, rownum) -> {
						return new UserAccountDto(rs.getInt("userAccountId"), rs.getString("userName"), rs.getString("password"), userId);}
					, userId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

}