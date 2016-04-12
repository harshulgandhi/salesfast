package com.stm.salesfast.backend.services.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.TerritoryDto;
import com.stm.salesfast.backend.dto.UserDto;
import com.stm.salesfast.backend.entity.AssignedSalesRepInfoEntity;

public interface TerritoryService {
	
	public TerritoryDto getBy(int territoryId);
	
	public TerritoryDto getBy(String zip);
	
	public TerritoryDto getByUser(int userId);

	public List<AssignedSalesRepInfoEntity> getSalesRepByDMId(int districtManagerId);
}
