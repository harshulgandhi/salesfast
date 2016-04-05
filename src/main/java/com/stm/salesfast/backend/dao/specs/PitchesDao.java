package com.stm.salesfast.backend.dao.specs;

import com.stm.salesfast.backend.dto.PitchesDto;

public interface PitchesDao {
	
	public void insert(PitchesDto pitch);

	public PitchesDto getPitchByAppointmentId(int appointmentId);

	public PitchesDto getPitchById(int pitchId);

	public void updateStatus(int appointmentId, String status);

	public void updatePitchFile(int appointmentId, String fileName);

	public void upvotePitch(int pitchesId);

	public void downvotePitch(int pitchesId);
}
