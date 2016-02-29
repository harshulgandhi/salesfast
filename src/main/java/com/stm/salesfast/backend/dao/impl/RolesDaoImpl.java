package com.stm.salesfast.backend.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stm.salesfast.backend.dao.specs.RolesDao;
import com.stm.salesfast.backend.dto.RolesDto;

@Repository
public class RolesDaoImpl implements RolesDao {
	
	private Logger log = LoggerFactory.getLogger(RolesDaoImpl.class.getName());
	
	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	private static final String FETCH_BY_ID = "SELECT * FROM roles WHERE roleId = ?";
	private static final String FETCH_BY_ROLENAME= "SELECT * FROM roles WHERE shortName = ?";
	private static final String FETCH_ALL= "SELECT * FROM roles";
	
	@Override
	public RolesDto getById(int roleId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(FETCH_BY_ID, (rs, rownum) -> {
				return new RolesDto(roleId, rs.getString("roleName"), rs.getString("shortName"));
				}, roleId);
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public RolesDto getByRoleName(String shortName) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(FETCH_BY_ROLENAME, (rs, rownum) -> {
				return new RolesDto(rs.getInt("roleId"), rs.getString("roleName"), shortName);
				}, shortName);
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<RolesDto> getAllRoles() {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.query(FETCH_ALL, (rs, rownum) -> {
				return new RolesDto(rs.getInt("roleId"), rs.getString("roleName"), rs.getString("shortName"));
				});
			
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

}
