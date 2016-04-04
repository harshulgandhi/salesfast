package com.stm.salesfast.backend.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
import com.stm.salesfast.backend.dto.UserDto;
import com.stm.salesfast.backend.entity.AlignedPhysicianEntity;
import com.stm.salesfast.backend.services.specs.AlignmentFetchService;
import com.stm.salesfast.backend.services.specs.AppointmentService;
import com.stm.salesfast.backend.services.specs.MeetingUpdateService;
import com.stm.salesfast.backend.services.specs.ProductFetchService;
import com.stm.salesfast.backend.services.specs.UserDetailService;

@Service
public class AlignmentFetchServiceImpl implements AlignmentFetchService {
	
	private Logger log = LoggerFactory.getLogger(AlignmentFetchServiceImpl.class.getName());
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private AlignmentsDao alignmentDao;
	
	@Autowired
	private PhysicianStgDao physicianDao;
	
	@Autowired
	private ProductFetchService productService;
	
	@Autowired
	private UserDetailService userService;
	
	@Autowired
	private MeetingUpdateService meetingUpdateService;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Override
	public List<AlignmentsDto> getAlignmentByUserId(int userId) {
		// TODO Auto-generated method stub
		List<AlignmentsDto> alignmentsByUserId= alignmentDao.getAlignmentByUserId(userId);
		log.info("Alignment by user Id fetched");
		return alignmentsByUserId;
	}

	@Override
	public AlignmentsDto getAlignmentByPhysicianId(int physicianId) {
		// TODO Auto-generated method stub
		AlignmentsDto alignmentsByPhysicianId= alignmentDao.getAlignmentByPhysicianId(physicianId);
		log.info("Alignment by physician id fetched");
		return alignmentsByPhysicianId;
	}

	@Override
	public List<AlignmentsDto> getAlignmentByUserIdZip(int userId, String zip) {
		// TODO Auto-generated method stub
		List<AlignmentsDto> alignmentsByUserIdZip= alignmentDao.getAlignmentByUserIdZip(userId, zip);
		return alignmentsByUserIdZip;
	}

	/**
	 * Method returns alignment specific details with fields that are to be
	 * shown on the UI - CANCELLED Appointments are shown as well
	 * */
	@Override
	public List<AlignedPhysicianEntity> getAlignmentByUserIdToShow(int userId) {
		// TODO Auto-generated method stub
		List<AlignmentsDto> alignmentsByUserId = alignmentDao.getAlignmentByUserIdNotInAppointments(userId);
		List<AlignedPhysicianEntity> alignedPhysicians = new ArrayList<AlignedPhysicianEntity>();
		for(AlignmentsDto eachAlignment : alignmentsByUserId){
			List<String> updateStatuses = meetingUpdateService.getStatusForAllAppointments(userId, eachAlignment.getPhysicianId());
			List<String> dedupeUpdateStatuses = updateStatuses.stream().distinct().collect(Collectors.toList());
			String combinedUpdateStatus =  (dedupeUpdateStatuses.size() > 0) ? String.join(",", dedupeUpdateStatuses) : "";
			String notInterestedConfirmationStatus = appointmentService.getNotInterestedAppointmentStatus(eachAlignment.getPhysicianId(), userId);
			combinedUpdateStatus = ( notInterestedConfirmationStatus == null ) ?
											combinedUpdateStatus : (combinedUpdateStatus + notInterestedConfirmationStatus);
			
			PhysicianStgDto physicianDto = physicianDao.getBy(eachAlignment.getPhysicianId());
			alignedPhysicians.add(new AlignedPhysicianEntity(physicianDto,
					productService.getProductById(eachAlignment.getProductId()).getProductName(),
					productService.getProductById(eachAlignment.getProductId()).getProductId(),
					combinedUpdateStatus));
		}
		Collections.sort(alignedPhysicians);		//Ordering physicians in reverse order of importance metric
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
	public List<AlignedPhysicianEntity> getAlignmentByUserIdInVicinityOfAppointments(int userId) {
		// TODO Auto-generated method stub
		List<AlignmentsDto> alignmentsByUserId = alignmentDao.getAlignmentByUserIdInVicinity(userId);
		List<AlignedPhysicianEntity> alignedPhysicians = new ArrayList<AlignedPhysicianEntity>();
		for(AlignmentsDto eachAlignment : alignmentsByUserId){
			PhysicianStgDto physicianDto = physicianDao.getBy(eachAlignment.getPhysicianId());
			List<String> updateStatuses = meetingUpdateService.getStatusForAllAppointments(userId, eachAlignment.getPhysicianId());
			List<String> dedupeUpdateStatuses = updateStatuses.stream().distinct().collect(Collectors.toList());
			String combinedUpdateStatus =  (dedupeUpdateStatuses.size() > 0) ? String.join(",", dedupeUpdateStatuses) : "";
			
			alignedPhysicians.add(new AlignedPhysicianEntity(physicianDto, 
					productService.getProductById(eachAlignment.getProductId()).getProductName(),
					productService.getProductById(eachAlignment.getProductId()).getProductId(),
					combinedUpdateStatus
					));
		}
		return alignedPhysicians;
	}

	@Override
	public List<Integer> getAlignedProduct(int userId, int physicianId) {
		// TODO Auto-generated method stub
		List<Integer> alignedProducts = new ArrayList<>();
		for(AlignmentsDto eachAlignment : alignmentDao.getAlignmentByUserIdPhysId(userId, physicianId) ){
			alignedProducts.add(eachAlignment.getProductId());
		}
		return alignedProducts;
	}

	@Override
	public List<PhysicianStgDto> getAlignmentByUserIdToShow(int userId,
			String zip) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(AlignmentsDto alignment) throws IOException {
		alignmentDao.insertAlignment(alignment);
	}
	
	@Override
	public UserDto getUserForAlignment(int physicianId, int productId){
		AlignmentsDto alignment = alignmentDao.getAlignmentByPhysicianProductId(physicianId, productId);
		return userService.getUserDetails(alignment.getUserId()); 
	}

	@Override
	public List<Integer> getUserIdsWorkingInMedicalField(String medicalFieldId){
		return alignmentDao.getUsersForParticularMedicalField(medicalFieldId);
	}
}