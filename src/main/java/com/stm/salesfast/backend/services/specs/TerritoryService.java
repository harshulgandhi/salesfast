package com.stm.salesfast.backend.services.specs;

import com.stm.salesfast.backend.dto.TerritoryDto;

public interface TerritoryService {
	
	public TerritoryDto getBy(int territoryId);
	
	public TerritoryDto getBy(String zip);
}
