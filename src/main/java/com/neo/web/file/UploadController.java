package com.neo.web.file;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.neo.commons.cons.IResult;
import com.neo.commons.util.JsonResultUtils;
import com.neo.model.bo.FileUploadBO;
import com.neo.service.file.UploadService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
    @PostMapping(value = "/defaultUpload")
    @ResponseBody
    public Map<String, Object> fileUpload(@RequestParam("file") MultipartFile  file,HttpServletRequest request){
    	IResult<FileUploadBO> result  =uploadService.upload(file,request);
    	uploadService.insertPtsApply(request, file);
    	if(result.isSuccess()) {
			return JsonResultUtils.successMapResult(result.getData());
		}else {
			return JsonResultUtils.failMapResult(result.getMessage());
		}

    }
	
	
	
//	@ApiOperation(value = "优云文件上传接口")
//    @PostMapping(value = "/uploadYc")
//    @ResponseBody
//	public Map<String, Object> fileUploadFromYc(String ycFileId){
//		
//	}
	
	
	
	
	
	
	
}
