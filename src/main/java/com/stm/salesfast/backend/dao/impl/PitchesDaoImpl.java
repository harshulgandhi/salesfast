package com.stm.salesfast.backend.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stm.salesfast.backend.dao.specs.PitchesDao;
import com.stm.salesfast.backend.dto.PitchesDto;

@Repository
public class PitchesDaoImpl implements PitchesDao{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final String INSERT = "INSERT INTO pitches (appointmentId, meetingStatus, fileName, pitchScore) "
			+ "VALUES (?,?,?,?)";
	private static final String FETCH_BY_APPOINTMENT = "SELECT * FROM pitches WHERE appointmentId = ?";
	private static final String FETCH_BY_ID = "SELECT * FROM pitches WHERE pitchesId = ?";
	private static final String UPDATE_STATUS = "UPDATE pitches SET meetingStatus = ? WHERE appointmentId = ?";
	private static final String UPDATE_PITCH_FILE = "UPDATE pitches SET fileName = ? WHERE appointmentId = ?";
	
	
	@Override
	public void insert(PitchesDto pitch) {
		// TODO Auto-generated method stub
		try{
			jdbcTemplate.update(INSERT, (ps)->{
				ps.setInt(1, pitch.getAppointmentId());
				ps.setString(2, pitch.getMeetingStatus());
				ps.setString(3, pitch.getFileName());
				ps.setInt(4, pitch.getPitchScore());
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
	}

	@Override
	public PitchesDto getPitchByAppointmentId(int appointmentId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(FETCH_BY_APPOINTMENT, (rs, rownnum)->{
				return new PitchesDto(rs.getInt("pitchesId"), rs.getInt("appointmentId"), rs.getString("meetingStatus"), rs.getString("fileName"), rs.getInt("pitchScore"));
			}, appointmentId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public PitchesDto getPitchById(int pitchId) {
		// TODO Auto-generated method stub
		try{
			return jdbcTemplate.queryForObject(FETCH_BY_ID, (rs, rownnum)->{
				return new PitchesDto(rs.getInt("pitchesId"), rs.getInt("appointmentId"), rs.getString("meetingStatus"), rs.getString("fileName"), rs.getInt("pitchScore"));
			}, pitchId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void updateStatus(int appointmentId, String status) {
		// TODO Auto-generated method stub
		try{
			jdbcTemplate.update(UPDATE_STATUS, (ps)->{
				ps.setString(1, status);
				ps.setInt(2, appointmentId);
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void updatePitchFile(int appointmentId, String fileName) {
		// TODO Auto-generated method stub
		try{
			jdbcTemplate.update(UPDATE_PITCH_FILE, (ps)->{
				ps.setString(1, fileName);
				ps.setInt(2, appointmentId);
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
	}
}
