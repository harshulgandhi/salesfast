package com.stm.salesfast.backend.services.specs;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.stm.salesfast.backend.entity.ManagerProductViewEntity;

public interface ManagerViewProductService {
	public List<ManagerProductViewEntity> getDocuments(int productId);

	public void updateFile(MultipartFile productFile, int productId, String fileType)
			throws IllegalStateException, IOException; 
}
