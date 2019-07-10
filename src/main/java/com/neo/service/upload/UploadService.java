package com.neo.service.upload;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.neo.commons.SysConfig;
import com.neo.service.IUploadService;

public class UploadService implements IUploadService{
    @Autowired
    private SysConfig config;

	
	public String uploadFile(MultipartFile file) {
		if(file!=null )
        {
            String relativePath = UUID.randomUUID()+File.separator+file.getOriginalFilename();
            String path=config.getInputDir() +relativePath;
            //上传
            try {
				file.transferTo(new File(path));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
            return relativePath;
        }
		return null;
	}



}
