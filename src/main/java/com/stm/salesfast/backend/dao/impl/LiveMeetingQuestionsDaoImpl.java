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
	
	private static final String INSERT_QUESTION_ONLY = "INSERT INTO live_meeting_questions "
			+ "(userId, question, questionAskedOn) "
			+ "VALUES (?,?,?)";
	private static final String FETCH_ALL = "SELECT * FROM live_meeting_questions WHERE answer IS NOT NULL";
	
	private static final String FETCH_QUES_wo_ANSWER = "SELECT * FROM live_meeting_questions "
			+ "WHERE answer IS null AND "
			+ "answeredByUser IS null AND "
			+ "userId != ? "
			+ "ORDER BY questionAskedOn DESC";
	private static final String INSERT_ANSWER_TOA_QUES = "UPDATE live_meeting_questions "
			+ "SET answer = ?, "
			+ "answeredByUser = ? "
			+ "WHERE liveMeetingQuestionId = ?";
	
	private static final String FETCH_ALL_ASKED_BY_SELF = "SELECT * FROM live_meeting_questions WHERE "
			+ "userId = ? ORDER BY questionAskedOn DESC";
	
	
	
	@Override
	public void insert(LiveMeetingQuestionsDto liveMeetingQuestion) {
		try{
			jdbcTemplate.update(INSERT, (ps)->{
				ps.setInt(1, liveMeetingQuestion.getUserId());
				ps.setString(2, liveMeetingQuestion.getQuestion());
				ps.setString(3, liveMeetingQuestion.getAnswer());
				ps.setInt(4, liveMeetingQuestion.getAnsweredByUser());
				ps.setDouble(5, liveMeetingQuestion.getImportanceIndex());
				ps.setDate(6, liveMeetingQuestion.getQuestionAskedOn());
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void insertQuestionOnly(LiveMeetingQuestionsDto liveMeetingQuestion) {
		try{
			jdbcTemplate.update(INSERT_QUESTION_ONLY, (ps)->{
				ps.setInt(1, liveMeetingQuestion.getUserId());
				ps.setString(2, liveMeetingQuestion.getQuestion());
				ps.setDate(3, liveMeetingQuestion.getQuestionAskedOn());
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
	}

	@Override
	public void insertAnswerToAQuestion(String answer, int answeredByUser, int liveMeetingQuestionId) {
		try{
			jdbcTemplate.update(INSERT_ANSWER_TOA_QUES, (ps)->{
				ps.setString(1,answer);
				ps.setInt(2, answeredByUser);
				ps.setInt(3,liveMeetingQuestionId);
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public List<LiveMeetingQuestionsDto> getAll() {
		try{
			return jdbcTemplate.query(FETCH_ALL, (rs, rownnum)->{
				return new LiveMeetingQuestionsDto(rs.getInt("liveMeetingQuestionId"), rs.getInt("userId"),rs.getString("question"),rs.getString("answer"), rs.getInt("answeredByUser"), rs.getFloat("importanceIndex"), rs.getDate("questionAskedOn"));
			});
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<LiveMeetingQuestionsDto> getAllwoAnswer(int userId) {
		try{
			return jdbcTemplate.query(FETCH_QUES_wo_ANSWER, (rs, rownnum)->{
				return new LiveMeetingQuestionsDto(rs.getInt("liveMeetingQuestionId"), rs.getInt("userId"),rs.getString("question"),rs.getString("answer"), rs.getInt("answeredByUser"), rs.getFloat("importanceIndex"), rs.getDate("questionAskedOn"));
			}, userId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<LiveMeetingQuestionsDto> getAllAskedBySelf(int userId) {
		try{
			return jdbcTemplate.query(FETCH_ALL_ASKED_BY_SELF, (rs, rownnum)->{
				return new LiveMeetingQuestionsDto(rs.getInt("liveMeetingQuestionId"), rs.getInt("userId"),rs.getString("question"),rs.getString("answer"), rs.getInt("answeredByUser"), rs.getFloat("importanceIndex"), rs.getDate("questionAskedOn"));
			}, userId);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
}
