package com.neo.service.upload;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.ResultCode;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.cons.entity.FileHeaderEntity;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.service.httpclient.HttpAPIService;

@Service("uploadService")
public class UploadService {

	@Autowired
	private PtsProperty ptsProperty;
	
	@Autowired
	private HttpAPIService httpAPIService;

	
	
	/**
	 * 文件上传到fcs服务器
	 * @author xujun
	 * @param request
	 * @date 2019-07-19
	 * @return
	 */
	public IResult<String> upload(HttpServletRequest  request) {
		
		MultipartHttpServletRequest  multipartRequest  =  (MultipartHttpServletRequest)  request;
		MultipartFile  file  =  multipartRequest.getFile("file");
		if(file != null) {
			
			String  fileName  =  file.getOriginalFilename();
			String  url  =  String.format(ptsProperty.getFcs_upload_url());
			
			//去传文件给fcs
			String reuslt = httpAPIService.uploadResouse(file,url,fileName);
			
			if(StringUtils.isNotBlank(reuslt)) {
				Map<String,Object> map = JsonUtils.parseJSON2Map(reuslt);
				String errorcode  = map.get(SysConstant.FCS_ERRORCODE).toString();
				String data = map.get(SysConstant.FCS_DATA).toString();
				String  message = map.get(SysConstant.FCS_MESSAGE).toString();
				if("0".equals(errorcode)) {
					//这里加了一个源文件大小的参数进去，转换记录的时候要用
					Map<String,Object> srcFilePathMap = JsonUtils.parseJSON2Map(data);
					srcFilePathMap.put(SysConstant.SRC_FILE_SIZE, file.getSize());
					return DefaultResult.successResult(srcFilePathMap.toString());
				}else {
					return DefaultResult.failResult(message);
				}
			}
		}
		return DefaultResult.failResult(ResultCode.E_UPLOAD_FILE.getInfo());
	}



}
