package com.neo.web.file;

import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.PtsConsts;
import com.neo.commons.cons.constants.SessionConstant;
import com.neo.commons.cons.entity.FileHeaderEntity;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.model.bo.FileUploadBO;
import com.neo.service.file.UploadService;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 上传的控制器
 *
 * @authore xujun
 * @create 2018-07-17
 */
@Api(value = "上传相关Controller", tags = {"上传相关Controller"})
@Controller
@RequestMapping(value = "/file")
public class UploadController{

	@Autowired
	private UploadService uploadService;


	@ApiOperation(value = "文件上传")
	@ApiImplicitParams({
			@ApiImplicitParam(name="originalFilename" ,value="微信小程序真实的文件名" ,required=false ,dataType="string",paramType="query")})
	@ApiResponses({
			@ApiResponse(code=200 ,response=Map.class, message="固定返回模型，json字符串表现形式,data:主要字段内容，code：返回结果码，message：返回结果信息")})
	@PostMapping(value = "/defaultUpload")
	@ResponseBody
	public Map<String, Object> fileUpload(@RequestParam("file") MultipartFile  file,
			String originalFilename ,HttpServletRequest request){

		//处理微信小程序临时文件名问题
		originalFilename = StringUtils.isBlank(originalFilename)?file.getOriginalFilename():originalFilename;

		IResult<FileUploadBO> result  =uploadService.upload(file,originalFilename,request);//
//		uploadService.insertPtsApply(HttpUtils.getSessionUserID(request),HttpUtils.getIpAddr(request),originalFilename,file.getSize(),request.getParameter(PtsConsts.MODULE));
		if(result.isSuccess()) {
			return JsonResultUtils.successMapResult(result.getData());
		}else {
			return JsonResultUtils.failMapResult(result.getMessage());
		}

	}



	@ApiOperation(value = "优云文件上传接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name="ycFileId" ,value="优云的fileId" ,required=false ,dataType="string",paramType="query")})
	@ApiResponses({
			@ApiResponse(code=200 ,response=Map.class, message="固定返回模型，json字符串表现形式,data:主要字段内容，code：返回结果码，message：返回结果信息")})
	@PostMapping(value = "/uploadYc")
	@ResponseBody
	public Map<String, Object> fileUploadFromYc(String ycFileId ,HttpServletRequest request){
		FileHeaderEntity fileHeaderEntity = (FileHeaderEntity)request.getAttribute(SessionConstant.FILE_HEADER_ENTITY);
		if(fileHeaderEntity == null) {
			return  JsonResultUtils.failMapResult(EnumResultCode.E_YCUPLOAD_ERROR.getInfo());
		}
		IResult<FileUploadBO> result  = uploadService.fileUploadFromYc(ycFileId,fileHeaderEntity);
		uploadService.insertPtsApply(HttpUtils.getSessionUserID(request),HttpUtils.getIpAddr(request),fileHeaderEntity.getFileName(),fileHeaderEntity.getContentLength(),request.getParameter(PtsConsts.MODULE));
		if(result.isSuccess()) {
			return JsonResultUtils.successMapResult(result.getData());
		}else {
			return JsonResultUtils.failMapResult(result.getMessage());
		}
	}







}
