package com.neo.web.upload;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.commons.cons.IResult;
import com.neo.commons.util.JsonResultUtils;
import com.neo.model.bo.FileUploadBO;
import com.neo.service.upload.UploadService;

/**
 * 上传的控制器
 *
 * @authore xujun
 * @create 2018-07-17
 */
@Controller
public class UploadController{

	@Autowired
	private UploadService uploadService;

    @PostMapping(value = "/defaultUpload")
    @ResponseBody
    public Map<String, Object> fileUpload(HttpServletRequest request){
    	IResult<FileUploadBO> result  =uploadService.upload(request);
    	if(result.isSuccess()) {
			return JsonResultUtils.successMapResult(result.getData());
		}else {
			return JsonResultUtils.failMapResult(result.getMessage());
		}

    }
}
