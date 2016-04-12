package com.stm.salesfast.backend.dao.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.TerritoryDto;
import com.stm.salesfast.backend.dto.UserDto;
import com.stm.salesfast.backend.entity.AssignedSalesRepInfoEntity;

public interface TerritoryDao {
	
	public TerritoryDto getBy(int territoryId);
	
	public TerritoryDto getBy(String zip);

	List<TerritoryDto> getByUser(int userId);

	List<AssignedSalesRepInfoEntity> getSalesRepsByDM(int userId);
}
