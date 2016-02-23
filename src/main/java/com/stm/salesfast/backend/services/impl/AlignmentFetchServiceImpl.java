package com.stm.salesfast.backend.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.impl.AlignmentsDaoImpl;
import com.stm.salesfast.backend.dao.specs.AlignmentsDao;
import com.stm.salesfast.backend.dao.specs.PhysicianStgDao;
import com.stm.salesfast.backend.dto.AlignmentsDto;
import com.stm.salesfast.backend.dto.PhysicianStgDto;
import com.stm.salesfast.backend.services.specs.AlignmentFetchService;

@Service
public class AlignmentFetchServiceImpl implements AlignmentFetchService {
	
	private Logger log = LoggerFactory.getLogger(AlignmentFetchServiceImpl.class.getName());
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private AlignmentsDao alignmentDao;
	
	@Autowired
	private PhysicianStgDao physicianDao;
	
	@Override
	public List<AlignmentsDto> getAlignmentByUserId(int userId) {
		// TODO Auto-generated method stub
		List<AlignmentsDto> alignmentsByUserId= alignmentDao.getAlignmentByUserId(userId);
		for (AlignmentsDto eachAlignment : alignmentsByUserId) log.info("Alignment : \n"+eachAlignment);
		return alignmentsByUserId;
	}

	@Override
	public AlignmentsDto getAlignmentByPhysicianId(int physicianId) {
		// TODO Auto-generated method stub
		AlignmentsDto alignmentsByPhysicianId= alignmentDao.getAlignmentByPhysicianId(physicianId);
		log.info("Alignment : \n"+alignmentsByPhysicianId);
		return alignmentsByPhysicianId;
	}

	@Override
	public List<AlignmentsDto> getAlignmentByUserIdZip(int userId, String zip) {
		// TODO Auto-generated method stub
		List<AlignmentsDto> alignmentsByUserIdZip= alignmentDao.getAlignmentByUserIdZip(userId, zip);
		for (AlignmentsDto eachAlignment : alignmentsByUserIdZip) log.info("Alignment : \n"+eachAlignment);
		return alignmentsByUserIdZip;
	}

	/**
	 * Method returns alignment specific details with fields that are to be
	 * shown on the UI
	 * */
	@Override
	public List<PhysicianStgDto> getAlignmentByUserIdToShow(int userId) {
		// TODO Auto-generated method stub
		List<AlignmentsDto> alignmentsByUserId = alignmentDao.getAlignmentByUserIdNotInAppointments(userId);
		List<PhysicianStgDto> alignedPhysicians = new ArrayList<PhysicianStgDto>();
		for(AlignmentsDto eachAlignment : alignmentsByUserId){
			PhysicianStgDto physicianDto = physicianDao.getBy(eachAlignment.getPhysicianId());
			alignedPhysicians.add(physicianDto);
			log.info(""+physicianDto);
		}
		return alignedPhysicians;
	}
	
	/**
	 * Method returns alignment specific details with fields that are to be
	 * shown on the UI
	 * */
	@Override
	public PhysicianStgDto getAlignmentByPhysicianIdToShow(int physicianId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method returns alignment specific details with fields that are to be
	 * shown on the UI, mainly for alignments that lie in vicinity of 
	 * physicians who have fixed appointments with users
	 * */
	@Override
	public List<PhysicianStgDto> getAlignmentByUserIdInVicinityOfAppointments(int userId) {
		// TODO Auto-generated method stub
		List<AlignmentsDto> alignmentsByUserId = alignmentDao.getAlignmentByUserIdInVicinity(userId);
		List<PhysicianStgDto> alignedPhysicians = new ArrayList<PhysicianStgDto>();
		log.info("Alignments in vicinity\n");
		for(AlignmentsDto eachAlignment : alignmentsByUserId){
			PhysicianStgDto physicianDto = physicianDao.getBy(eachAlignment.getPhysicianId());
			alignedPhysicians.add(physicianDto);
			log.info(""+physicianDto);
		}
		return alignedPhysicians;
	}

	@Override
	public int getAlignedProduct(int userId, int physicianId) {
		// TODO Auto-generated method stub
		return alignmentDao.getAlignmentByUserIdPhysId(userId, physicianId).getProductId();
	}

	@Override
	public List<PhysicianStgDto> getAlignmentByUserIdToShow(int userId,
			String zip) {
		// TODO Auto-generated method stub
		return null;
	}

}
