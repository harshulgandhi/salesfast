package com.stm.salesfast.backend.services.specs;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.stm.salesfast.backend.entity.ManagerProductViewEntity;

public interface ManagerViewProductService {
	public List<ManagerProductViewEntity> getDocuments(int productId);

	public void updateFile(MultipartFile productFile, int productId, String fileType)
			throws IllegalStateException, IOException;

	public void updatesAboutDocUpdateToPhysicians(List<Integer> physiciansToBeUpdated,
			int productId);

	public void updatesAboutDocUpdateToSalesReps(List<Integer> salesrepsToBeUpdated,
			int productId);

	public void sendNewProductNotificationEmails(String subject, String body,
			String toEmailId); 
}
