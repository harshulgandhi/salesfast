package com.stm.salesfast.backend.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stm.salesfast.backend.dao.specs.UserToRoleDao;
import com.stm.salesfast.backend.dto.UserToRoleDto;

@Repository
public class UserToRoleDaoImpl implements UserToRoleDao {

	Logger log = LoggerFactory.getLogger(UserDaoImpl.class.getName());	
	
	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	private static final String FETCH_BY_USERID= "SELECT * FROM User_To_Role WHERE userId = ?";
	
	@Override
	public List<UserToRoleDto> getAllRolesForUser(int userId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_BY_USERID, (rs, rownum) -> {
				return new UserToRoleDto(rs.getInt("userRoleId"), rs.getInt("userId"), rs.getInt("roleId"));
				}, userId);
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

}