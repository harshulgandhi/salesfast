package com.stm.salesfast.backend.services.specs;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface AddNewProductService {
	public void saveUploadedFiles(MultipartFile virtualTrainingFile, MultipartFile eDetailingFile) throws IllegalStateException, IOException;
}
