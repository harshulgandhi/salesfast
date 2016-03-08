package com.stm.salesfast.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.TerritoryDao;
import com.stm.salesfast.backend.dto.TerritoryDto;
import com.stm.salesfast.backend.services.specs.TerritoryService;


@Service
public class TerritoryServiceImpl implements TerritoryService {

	@Autowired
	TerritoryDao territoryDao;
	
	@Override
	public TerritoryDto getBy(int territoryId) {
		// TODO Auto-generated method stub
		return territoryDao.getBy(territoryId);
	}

	@Override
	public TerritoryDto getBy(String zip) {
		// TODO Auto-generated method stub
		return territoryDao.getBy(zip);
	}

	@Override
	public TerritoryDto getByUser(int userId) {
		// TODO Auto-generated method stub
		
		return territoryDao.getByUser(userId).size() == 0 ? null : territoryDao.getByUser(userId).get(0);
	}

	
}
