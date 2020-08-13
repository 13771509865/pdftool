package com.neo.web.file;

import com.neo.commons.cons.IResult;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.service.file.DeleteFileService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "删除用户的转换记录")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id" ,value="每一条转换记录对应的id" ,required=false ,dataType="int",paramType="query",example = "0"),
			@ApiImplicitParam(name="fileHash" ,value="每一条转换记录对应的MD5哈希值" ,required=false ,dataType="string",paramType="query"),
		@ApiImplicitParam(name="uCloudFileId" ,value="每一条转换记录对应的优云fileId" ,required=false ,dataType="string",paramType="query")})
	@ApiResponses({
			@ApiResponse(code=200 ,response=Map.class, message="固定返回模型，json字符串表现形式,data:主要字段内容，code：返回结果码，message：返回结果信息")})
	@PostMapping(value = "/delete")
	@ResponseBody
	public Map<String, Object> deleteConvert(Integer id,String fileHash,String uCloudFileId, HttpServletRequest request){
		IResult<String> result = deleteFileService.deleteConvert(id,fileHash,uCloudFileId,HttpUtils.getSessionUserID(request));
		if(result.isSuccess()) {
			return JsonResultUtils.successMapResult();
		}else {
			return JsonResultUtils.failMapResult(result.getMessage());
		}
	}


}
