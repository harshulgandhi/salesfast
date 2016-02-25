package com.stm.salesfast.backend.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stm.salesfast.backend.dao.specs.MeetingUpdateDao;
import com.stm.salesfast.backend.dto.MeetingUpdateDto;

@Repository
public class MeetingUpdateDaoImpl implements MeetingUpdateDao {

	private static final String INSERT = "INSERT INTO meeting_update "
			+ "(date, status, isEDetailed, physicianId, productId, medicalFieldId, appointmentId)"
			+ "VALUES = (?,?,?,?,?,?,?)";
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public void insert(MeetingUpdateDto meetingUpdate) {
		
		// TODO Auto-generated method stub
		try{
			jdbcTemplate.update(INSERT,(ps)->{
				ps.setDate(1, meetingUpdate.getDate());
				ps.setString(2, meetingUpdate.getStatus());
				ps.setBoolean(3, meetingUpdate.isEDetailed());
				ps.setInt(4, meetingUpdate.getPhysicianId());
				ps.setInt(5, meetingUpdate.getProductId());
				ps.setString(6, meetingUpdate.getMedicalFieldId());
				ps.setInt(7, meetingUpdate.getAppointmentId());
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		
	}

}
