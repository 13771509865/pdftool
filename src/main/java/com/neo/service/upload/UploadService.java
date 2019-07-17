package com.neo.service.upload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.ResultCode;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.util.JsonResultUtils;

@Service("uploadService")
public class UploadService {
	
	
    @Autowired
    private ConfigProperty config;

	
	public IResult<List<String>> uploadFile(HttpServletRequest request) {
		try {
  		  //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
          CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
          //检查form中是否有enctype="multipart/form-data"
          if(multipartResolver.isMultipart(request))
          {
              //将request变成多部分request
              MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
              //获取multiRequest 中所有的文件名
              Iterator iter=multiRequest.getFileNames();
              List<String> result = new ArrayList<>();
              while(iter.hasNext()) {
                  //一次遍历所有文件
                  MultipartFile file=multiRequest.getFile(iter.next().toString());
                  if(file!=null ){
                      String relativePath = UUID.randomUUID()+File.separator+file.getOriginalFilename();
                      String path=config.getInputDir() +File.separator+relativePath;
                      File targetFile =  new File(path);
                      if(!targetFile.exists()) {
                      	targetFile.getParentFile().mkdirs();
                      	targetFile.createNewFile();
                      }
                      //上传
                      file.transferTo(targetFile);
                      result.add(relativePath);
                  }
              }
              return DefaultResult.successResult(result);
          }else{
              return DefaultResult.failResult(ResultCode.E_UPLOAD_FILE.getInfo());
          }
		} catch (Exception e) {
			 return DefaultResult.failResult(ResultCode.E_UPLOAD_FILE.getInfo());
		}
  	
	}
}
