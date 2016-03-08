package com.stm.salesfast.backend.dao.specs;

import com.stm.salesfast.backend.dto.DistrictsDto;

public interface DistrictDao {
	public DistrictsDto getBy(int districtId);
	public DistrictsDto getByUser(int userId);
}
