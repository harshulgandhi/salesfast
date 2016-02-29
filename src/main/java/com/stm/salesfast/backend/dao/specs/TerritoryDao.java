package com.stm.salesfast.backend.dao.specs;

import com.stm.salesfast.backend.dto.TerritoryDto;

public interface TerritoryDao {
	
	public TerritoryDto getBy(int territoryId);
	
	public TerritoryDto getBy(String zip);
}
