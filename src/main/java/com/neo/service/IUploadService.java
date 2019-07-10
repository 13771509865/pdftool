package com.neo.service;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadService {
	public String uploadFile (MultipartFile file);
}
