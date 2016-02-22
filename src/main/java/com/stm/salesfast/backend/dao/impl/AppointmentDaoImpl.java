package com.stm.salesfast.backend.dao.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stm.salesfast.backend.dao.specs.AppointmentDao;
import com.stm.salesfast.backend.dto.AppointmentDto;

@Repository
public class AppointmentDaoImpl implements AppointmentDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final String FETCH_BY_ID = "SELECT * FROM appointment WHERE appointmentId = ?";
	private static final String FETCH_BY_USERID = "SELECT * FROM appointment WHERE userId = ?";
	private static final String FETCH_BY_PHYSICIANID = "SELECT * FROM appointment WHERE physicianId = ?";
	private static final String INSERT = "INSERT INTO appointment"+
	"(time, date, physicianId, userId, productId, confirmationStatus)"+
	"VALUES (?,?,?,?,?,?)";
	
	@Override
	public AppointmentDto getAppointmentById(int appointmentId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(FETCH_BY_ID, (rs, rownnum)->{
				return new AppointmentDto(appointmentId, rs.getTime("time"), rs.getDate("date"), rs.getInt("physicianId"), rs.getInt("userId"), rs.getInt("productId"), rs.getString("confirmationStatus"));
			}, appointmentId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<AppointmentDto> getAppointmentByUserId(int userId) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public AppointmentDto getAppointmentByPhysicianId(int physicianId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertAppointment(AppointmentDto appointment) {
		// TODO Auto-generated method stub
		try{
			jdbcTemplate.update(INSERT, (ps)->{
				ps.setTime(1, appointment.getTime());
				ps.setDate(2, appointment.getDate());
				ps.setInt(3, appointment.getPhysicianId());
				ps.setInt(4, appointment.getUserId());
				ps.setInt(5, appointment.getProductId());
				ps.setString(6, appointment.getConfirmationStatus());
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
	}

}
