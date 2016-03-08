package com.stm.salesfast.backend.services.specs;

import com.stm.salesfast.backend.dto.DistrictsDto;

public interface DistrictService {
	public DistrictsDto getDistrictById(int districtId);
	public DistrictsDto getDistrictByManagerId(int userId);
}
