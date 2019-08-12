package com.neo.web.file;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.commons.cons.IResult;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.util.JsonResultUtils;
import com.neo.model.qo.FcsFileInfoQO;
import com.neo.service.file.DeleteFileService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 文件删除接口
 * @author xujun
 * @create 2019-08-12
 */
@Api(value = "删除相关Controller", tags = {"删除相关Controller"})
@Controller
@RequestMapping(value = "/file")
public class DeleteFileController {
	
	@Autowired
	private DeleteFileService deleteFileService;
	
	
	/**
	 * 删除用户的转换记录
	 * @param filehash
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "删除用户的转换记录")
	@PostMapping(value = "/delete")
	@ResponseBody
	public Map<String, Object> deleteConvert(@RequestBody FcsFileInfoQO fcsFileInfoQO ,HttpServletRequest request){
		IResult<String> result = deleteFileService.deleteConvert(fcsFileInfoQO, request);
		if(result.isSuccess()) {
			return JsonResultUtils.successMapResult();
		}else {
			return JsonResultUtils.failMapResult(result.getMessage());
		}
	}


}
