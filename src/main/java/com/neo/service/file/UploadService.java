package com.neo.service.file;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.PtsConsts;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.cons.entity.HttpResultEntity;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.dao.PtsApplyPOMapper;
import com.neo.model.bo.FileUploadBO;
import com.neo.model.po.PtsApplyPO;
import com.neo.service.httpclient.HttpAPIService;

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
	public IResult<FileUploadBO> upload(MultipartFile  file,HttpServletRequest request) {
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
				//				request.setAttribute(SysConstant.UPLOAD_RESULT, EnumResultCode.E_SUCCES.getValue());
				return DefaultResult.successResult(message,fileUploadBO);

			}
			return DefaultResult.failResult(EnumResultCode.E_UPLOAD_FILE.getInfo());
		} catch (Exception e) {
			return DefaultResult.failResult(EnumResultCode.E_UPLOAD_FILE.getInfo());
		}
	}


	/**
	 * 插入功能应用表
	 * @param request
	 * @param file
	 * @return
	 */
	public IResult<String> insertPtsApply(HttpServletRequest request,MultipartFile  file){
		try {
			PtsApplyPO ptsApplyPO = buildPtsApplyPO(request,file);
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
	public PtsApplyPO buildPtsApplyPO(HttpServletRequest request,MultipartFile  file) {
		PtsApplyPO ptsApplyPO = new PtsApplyPO();
		ptsApplyPO.setFileName(file.getOriginalFilename());
		ptsApplyPO.setFileSize(file.getSize());

		Long userId = HttpUtils.getSessionUserID(request);
		if(userId ==null) {
			ptsApplyPO.setAddress(HttpUtils.getIpAddr(request));
		}else {
			ptsApplyPO.setAddress(String.valueOf(userId));
		}
		
		ptsApplyPO.setModule(Integer.valueOf(request.getParameter(PtsConsts.MODULE)));
		return ptsApplyPO;

	}


}
