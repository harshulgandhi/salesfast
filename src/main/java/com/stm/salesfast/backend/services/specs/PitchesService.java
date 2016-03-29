package com.stm.salesfast.backend.services.specs;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.stm.salesfast.backend.dto.PitchesDto;
import com.stm.salesfast.backend.entity.PitchViewEntity;

public interface PitchesService {
	public void insertPitch(PitchesDto pitch);

	void saveTheFile(MultipartFile pitchFile) throws IllegalStateException,
			IOException;

	void insertNewPitch(int appointmentId, MultipartFile pitch)
			throws IllegalStateException, IOException;

	void updateStatus(String status, int appointmentId);

	PitchViewEntity getPitchForAppointment(int appointmentId);

	void updatePitchFileName(String fileName, int appointmentId);

	void updatePitchFile(int appointmentId, MultipartFile pitchdocument) throws IllegalStateException, IOException;
}
