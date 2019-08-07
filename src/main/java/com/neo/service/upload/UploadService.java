package com.neo.service.upload;


import java.io.File;
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
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.JsonUtils;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.model.bo.FileUploadBO;
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
	public IResult<FileUploadBO> upload(MultipartFile  file,HttpServletRequest request) {
		try {
			if(file != null) {
				String  url  =  String.format(ptsProperty.getFcs_upload_url());
				
				//去传文件给fcs
				String reuslt = httpAPIService.uploadResouse(file,url);
				if(StringUtils.isNotBlank(reuslt)) {
					Map<String,Object> map = JsonUtils.parseJSON2Map(reuslt);
					Integer errorcode  = (Integer)map.get(SysConstant.FCS_ERRORCODE);
					String  message = (String)map.get(SysConstant.FCS_MESSAGE);
					if(errorcode == 0) {
						FileUploadBO fileUploadBO = JsonUtils.json2obj(map.get(SysConstant.FCS_DATA), FileUploadBO.class);
						fileUploadBO.setSrcFileSize(file.getSize());
						request.setAttribute(SysConstant.UPLOAD_RESULT, ResultCode.E_SUCCES.getValue());
						return DefaultResult.successResult(message,fileUploadBO);
					}else {
						return DefaultResult.failResult(message);
					}
				}
			}
			return DefaultResult.failResult(ResultCode.E_UPLOAD_FILE.getInfo());
		} catch (Exception e) {
			return DefaultResult.failResult(ResultCode.E_UPLOAD_FILE.getInfo());
		}
	}


	

	
}
