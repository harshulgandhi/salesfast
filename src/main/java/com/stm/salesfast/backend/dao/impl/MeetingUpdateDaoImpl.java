package com.stm.salesfast.backend.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stm.salesfast.backend.dao.specs.MeetingUpdateDao;
import com.stm.salesfast.backend.dto.MeetingUpdateDto;
import com.stm.salesfast.constant.PhysicianStatus;

@Repository
public class MeetingUpdateDaoImpl implements MeetingUpdateDao {

	private Logger log = LoggerFactory.getLogger(MeetingUpdateDaoImpl.class.getName());

	private static String INSERT = "INSERT INTO `salesfast`.`meeting_update` "
			+ "(`date`,`status`,`isEDetailed`,`physicianId`,`productId`,`medicalFieldId`,`appointmentId`) "
			+ "VALUES (?,?,?,?,?,?,?);";
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public void insert(MeetingUpdateDto meetingUpdate) {
		// TODO Auto-generated method stub
		
		try{
			jdbcTemplate.update(INSERT,(ps)->{
				ps.setDate(1, meetingUpdate.getDate());
				ps.setString(2, PhysicianStatus.valueOf(meetingUpdate.getStatus()).toString());
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
