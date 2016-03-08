package com.stm.salesfast.backend.dao.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.TerritoryDto;

public interface TerritoryDao {
	
	public TerritoryDto getBy(int territoryId);
	
	public TerritoryDto getBy(String zip);

	List<TerritoryDto> getByUser(int userId);
}
