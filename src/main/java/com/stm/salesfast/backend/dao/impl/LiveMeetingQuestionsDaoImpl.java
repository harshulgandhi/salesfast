package com.stm.salesfast.backend.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stm.salesfast.backend.dao.specs.LiveMeetingQuestionsDao;
import com.stm.salesfast.backend.dto.LiveMeetingQuestionsDto;

@Repository
public class LiveMeetingQuestionsDaoImpl implements LiveMeetingQuestionsDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final String INSERT = "INSERT INTO live_meeting_questions "
			+ "(userId, question, answer, answeredByUser, importanctIndex) "
			+ "VALUES (?,?,?,?,?)";
	private static final String FETCH_ALL = "SELECT * FROM live_meeting_questions";
	
	
	@Override
	public void insert(LiveMeetingQuestionsDto liveMeetingQuestion) {
		try{
			jdbcTemplate.update(INSERT, (ps)->{
				ps.setInt(1, liveMeetingQuestion.getUserId());
				ps.setString(2, liveMeetingQuestion.getQuestion());
				ps.setString(3, liveMeetingQuestion.getAnswer());
				ps.setInt(4, liveMeetingQuestion.getAnsweredByUser());
				ps.setDouble(5, liveMeetingQuestion.getImportanceIndex());
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
	}

	@Override
	public List<LiveMeetingQuestionsDto> getAll() {
		try{
			return jdbcTemplate.query(FETCH_ALL, (rs, rownnum)->{
				return new LiveMeetingQuestionsDto(rs.getInt("liveMeetingQuestionId"), rs.getInt("userId"),rs.getString("question"),rs.getString("answer"), rs.getInt("answeredByUser"), rs.getFloat("importanceIndex"));
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
}
