package com.neo.service.file;


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
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.cons.entity.HttpResultEntity;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.commons.util.SysLogUtils;
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
				IResult<HttpResultEntity> result = httpAPIService.uploadResouse(file,url);
				System.out.println(result.getMessage());
				System.out.println(result.getData());
				if (!HttpUtils.isHttpSuccess(result)) {
					return DefaultResult.failResult(EnumResultCode.E_UPLOAD_FILE.getInfo());
				}
				Map<String, Object> map = JsonUtils.parseJSON2Map(result.getData().getBody());
				String message = EnumResultCode.getTypeInfo(Integer.valueOf(map.get(SysConstant.FCS_ERRORCODE).toString()));
				System.out.println(map.toString());
				if (!EnumResultCode.E_SUCCES.getValue().equals(Integer.valueOf(map.get(SysConstant.FCS_ERRORCODE).toString()))) {
					SysLogUtils.error("fcs上传文件失败:" + map.toString() + ",失败错误信息为:" + message);
					return DefaultResult.failResult(EnumResultCode.getTypeInfo(Integer.valueOf(map.get(SysConstant.FCS_ERRORCODE).toString())));
				}

				FileUploadBO fileUploadBO = JsonUtils.json2obj(map.get(SysConstant.FCS_DATA), FileUploadBO.class);
				fileUploadBO.setSrcFileSize(file.getSize());
				request.setAttribute(SysConstant.UPLOAD_RESULT, EnumResultCode.E_SUCCES.getValue());
				return DefaultResult.successResult(message,fileUploadBO);

			}
			return DefaultResult.failResult(EnumResultCode.E_UPLOAD_FILE.getInfo());
		} catch (Exception e) {
			return DefaultResult.failResult(EnumResultCode.E_UPLOAD_FILE.getInfo());
		}
	}

	
	

	
}
