package com.neo.service.file;


import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.cons.constants.UaaConsts;
import com.neo.commons.cons.constants.YzcloudConsts;
import com.neo.commons.cons.entity.FileHeaderEntity;
import com.neo.commons.cons.entity.HttpResultEntity;
import com.neo.commons.cons.entity.ModuleEntity;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.EncryptUtils;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.dao.PtsApplyPOMapper;
import com.neo.model.bo.FileUploadBO;
import com.neo.model.po.PtsApplyPO;
import com.neo.service.httpclient.HttpAPIService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service("uploadService")
public class UploadService {

	@Autowired
	private PtsProperty ptsProperty;

	@Autowired
	private HttpAPIService httpAPIService;

	@Autowired
	private PtsApplyPOMapper ptsApplyPOMapper;


	/**
	 * 文件上传到fcs服务器
	 * @author xujun
	 * @param request
	 * @date 2019-07-19
	 * @return
	 */
	public IResult<FileUploadBO> upload(MultipartFile  file,String originalFilename,HttpServletRequest request) {
		try {
			if(file != null) {
				String  url  =  String.format(ptsProperty.getFcs_upload_url());

				//去传文件给fcs
				IResult<HttpResultEntity> result = httpAPIService.uploadResouse(file,url);
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
				fileUploadBO.setDestinationName(originalFilename);
				//				request.setAttribute(SysConstant.UPLOAD_RESULT, EnumResultCode.E_SUCCES.getValue());
				return DefaultResult.successResult(message,fileUploadBO);

			}
			return DefaultResult.failResult(EnumResultCode.E_UPLOAD_FILE.getInfo());
		} catch (Exception e) {
			return DefaultResult.failResult(EnumResultCode.E_UPLOAD_FILE.getInfo());
		}
	}




	/**
	 * 优云上传文件接口
	 * @param ycFileId
	 * @return
	 */
	public IResult<FileUploadBO> fileUploadFromYc(String ycFileId,FileHeaderEntity fileHeaderEntity){
		Map<String, Object> fcsParams = new HashMap<>();
		fcsParams.put("fileUrl", fileHeaderEntity.getUrl());

		//根据优云提供的下载路径，给fcs下载
		IResult<HttpResultEntity> fcsResult = httpAPIService.doPost(ptsProperty.getFcs_http_download_url(), fcsParams); 
		if (!HttpUtils.isHttpSuccess(fcsResult)) {
			return DefaultResult.failResult(EnumResultCode.E_UPLOAD_FILE.getInfo());
		}

		Map<String, Object> map = JsonUtils.parseJSON2Map(fcsResult.getData().getBody());
		String message = EnumResultCode.getTypeInfo(Integer.valueOf(map.get(SysConstant.FCS_ERRORCODE).toString()));
		if (!EnumResultCode.E_SUCCES.getValue().equals(Integer.valueOf(map.get(SysConstant.FCS_ERRORCODE).toString()))) {
			SysLogUtils.error("优云文件上传fcs失败:" + map.toString() + ",失败错误信息为:" + message);
			return DefaultResult.failResult(EnumResultCode.E_UPLOAD_FILE.getInfo());
		}

		FileUploadBO fileUploadBO = JsonUtils.json2obj(map.get(SysConstant.FCS_DATA), FileUploadBO.class);
		fileUploadBO.setSrcFileSize(fileHeaderEntity.getContentLength());
		String destinationName = fileUploadBO.getData().substring(fileUploadBO.getData().indexOf("/")+1);
		fileUploadBO.setDestinationName(destinationName);
		return DefaultResult.successResult(message,fileUploadBO);
	}



	/**
	 * 根据优云的fileid获取优云文件信息
	 * @param ycFileId
	 * @return
	 */
	public IResult<FileHeaderEntity> getFileHeaderEntity(String ycFileId,String cookie,String accessToken ,String refreshToken){
		if (StringUtils.isBlank(cookie) && (StringUtils.isBlank(accessToken) || StringUtils.isBlank(refreshToken))) {
			return DefaultResult.failResult(EnumResultCode.E_UNLOGIN_ERROR.getInfo());
		}

		Map<String, Object> params = new HashMap<>();
		params.put("fileId", ycFileId);

		Map<String, Object> headers = new HashMap<>();

		if(StringUtils.isNotBlank(accessToken) && StringUtils.isNotBlank(refreshToken)){
			headers.put(UaaConsts.HEADER_ACCESS_TOKEN, accessToken);
			headers.put(UaaConsts.HEADER_REFRESH_TOKEN, refreshToken);
		}else{
			headers.put(UaaConsts.COOKIE, cookie);
		}


		//根据fileid去优云获取文件的下载路径
		IResult<HttpResultEntity> ycResult = httpAPIService.doGet(ptsProperty.getYzcloud_domain()+YzcloudConsts.DOWNLOAD_INTERFACE, params,headers);
		
		
		if (!HttpUtils.isHttpSuccess(ycResult)) {
			return DefaultResult.failResult(EnumResultCode.E_YCSERVICE_UPLOAD_ERROR.getInfo());
		}

		Map<String, Object> ycParams = JsonUtils.parseJSON2Map(ycResult.getData().getBody());
		if(!ycParams.isEmpty() && "0".equals(ycParams.get(YzcloudConsts.CODE).toString()) 
				&& ycParams.get(YzcloudConsts.URL) != null) {
			String url = ycParams.get(YzcloudConsts.URL).toString();
			return httpAPIService.getFileHeaderBOByHead(url);
		}
		return DefaultResult.failResult(ycParams.get(YzcloudConsts.MSG).toString());
	}



	/**
	 * 插入功能应用表
	 * @param request
	 * @param file
	 * @return
	 */
	public IResult<String> insertPtsApply(Long userId,String ipAddress,String fileName,Long fileSize,String module){
		try {
			PtsApplyPO ptsApplyPO = buildPtsApplyPO(userId, ipAddress, fileName, fileSize, module);
			boolean insertPtsApply = ptsApplyPOMapper.insertPtsApply(ptsApplyPO)>0;
			if(insertPtsApply) {
				return DefaultResult.successResult();
			}else {
				return DefaultResult.failResult();
			}	
		} catch (Exception e) {
			e.printStackTrace();
			return DefaultResult.failResult();
		}
	}



	/**
	 * 构建功能应用数量统计对象
	 * @param request
	 * @param file
	 * @return
	 */
	public PtsApplyPO buildPtsApplyPO(Long userId,String ipAddress,String fileName,Long fileSize,String module) {
		PtsApplyPO ptsApplyPO = new PtsApplyPO();
		ptsApplyPO.setFileName(fileName);
		ptsApplyPO.setFileSize(fileSize);

		if(userId ==null) {
			ptsApplyPO.setAddress(ipAddress);
		}else {
			ptsApplyPO.setAddress(String.valueOf(userId));
		}
		
		if(StringUtils.isNotBlank(module)) {
			ModuleEntity moduleEntity =EncryptUtils.decryptModule(module);
			if(moduleEntity.getModule() !=null ) {
				ptsApplyPO.setModule(moduleEntity.getModule());
			}
		}
		
		return ptsApplyPO;

	}


	/**
	 * 判断加密内容
	 * @param module
	 * @param nonceParm
	 * @return
	 */
	public IResult<ModuleEntity> checkModule(String module ,String nonceParm){
		ModuleEntity moduleEntity = new ModuleEntity();
		if (StringUtils.isBlank(module) && StringUtils.isBlank(nonceParm)) {
			System.out.println("=========是手机移动端=======");
			moduleEntity.setModule(1);
		} else {
			//解密module参数
			moduleEntity = EncryptUtils.decryptModule(module);
			if (moduleEntity == null || moduleEntity.getModule() == null || moduleEntity.getTimeStamp() == null) {
				return DefaultResult.failResult();
			}
			String nonce = EncryptUtils.decryptDES(nonceParm);
			//对比时间戳是否一致s
			if (!StringUtils.equals(nonce, moduleEntity.getTimeStamp())) {
				return DefaultResult.failResult();
			}
		}
		return DefaultResult.successResult(moduleEntity);
	}


	public static void main(String[] args) {
		String a = "abc";
		System.out.println(StringUtils.containsAny(a,"b"));

	}

	
}
