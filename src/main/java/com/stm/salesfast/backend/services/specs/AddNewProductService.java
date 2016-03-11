package com.stm.salesfast.backend.services.specs;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.stm.salesfast.backend.entity.NewProductEntity;

public interface AddNewProductService {
	public void saveUploadedFiles(MultipartFile virtualTrainingFile, MultipartFile eDetailingFile) throws IllegalStateException, IOException;

	public void recalculateAlignments() throws IOException;

	public void addNewProduct(NewProductEntity newProduct) throws IOException;

	public void sendNewProductNotificationEmails(String subject, String body, String toEmailId);

	public void updatesAboutNewProductToPhysicians(
			List<Integer> physiciansToBeUpdated, NewProductEntity newProduct);

	public void updatesAboutNewProductToSalesReps(List<Integer> physiciansToBeUpdated,
			NewProductEntity newProduct);
}
