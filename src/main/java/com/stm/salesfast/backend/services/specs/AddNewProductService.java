package com.stm.salesfast.backend.services.specs;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.stm.salesfast.backend.entity.NewProductEntity;

public interface AddNewProductService {
	public void saveUploadedFiles(MultipartFile virtualTrainingFile, MultipartFile eDetailingFile) throws IllegalStateException, IOException;

	void recalculateAlignments() throws IOException;

	void addNewProduct(NewProductEntity newProduct) throws IOException;
}
