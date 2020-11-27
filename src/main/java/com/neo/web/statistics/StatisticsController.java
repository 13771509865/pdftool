package com.neo.web.statistics;

import com.github.pagehelper.PageInfo;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.UaaConsts;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.json.JSON;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.model.po.FcsFileInfoPO;
import com.neo.model.qo.FcsFileInfoQO;
import com.neo.service.convert.PtsConvertService;
import com.neo.service.statistics.StatisticsService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(value = "查询记录相关Controller", tags = {"查询记录相关Controller"})
@Controller
@RequestMapping(value = "/statistics")
public class StatisticsController {
	
	@Autowired
	private StatisticsService statisticsService;

	@Autowired
	private PtsConvertService ptsConvertService;

	/**
	 * 根据userID，查询登录用户三天的转换记录
	 * @return
	 */
	@ApiOperation(value = "查询登录用户三天的转换记录")
	@ApiResponses({
			@ApiResponse(code=200 ,response=Map.class, message="固定返回模型，json字符串表现形式,data:主要字段内容，code：返回结果码，message：返回结果信息")})
	@PostMapping(value = "/idConvert")
	@ResponseBody
	public Map<String, Object> userConvert(@RequestBody FcsFileInfoQO fcsFileInfoQO,HttpServletRequest request){
		IResult<Map<String, Object>> result = statisticsService.selectConvertByUserID(fcsFileInfoQO,HttpUtils.getSessionUserID(request));
		if(result.isSuccess()) {
			return JsonResultUtils.successMapResult(result.getData());
		}else {
			return JsonResultUtils.failMapResult(result.getMessage());
		}
	}
	
	
	/**
	 * 查询当天剩余转换次数
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "查询当天剩余转换次数")
	@ApiResponses({
			@ApiResponse(code=200 ,response=Map.class, message="固定返回模型，json字符串表现形式,data:主要字段内容，code：返回结果码，message：返回结果信息")})
	@GetMapping(value = "/convertTimes")
	@ResponseBody
	public Map<String,Object> getConvertTimes(HttpServletRequest request){
		IResult<Map<String,Object>>  result = statisticsService.getConvertTimes(HttpUtils.getUaaToken(request));
		if(result.isSuccess()) {
			return JsonResultUtils.successMapResult(result.getData());
		}else {
			return JsonResultUtils.failMapResult(result.getMessage());
		}
	}



	/**
	 * 查询当天剩余转换次数
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "查询用户剩余的所有权益")
	@ApiResponses({
			@ApiResponse(code=200 ,response=Map.class, message="固定返回模型，json字符串表现形式,data:主要字段内容，code：返回结果码，message：返回结果信息")})
	@GetMapping(value = "/auth")
	@ResponseBody
	public Map<String,Object> getAuth(HttpServletRequest request){
		IResult<Map<String,Object[]>>  result = statisticsService.getAuth(HttpUtils.getUaaToken(request));
		if(result.isSuccess()) {
			return JsonResultUtils.successMapResult(result.getData());
		}else {
			return JsonResultUtils.failMapResult(result.getMessage());
		}
	}

	
	/**
	 * 根据fileHash查询UCloudFileId
	 * @param
	 * @return
	 */
	@ApiOperation(value = "查询UCloudFileId")
	@ApiImplicitParams({
			@ApiImplicitParam(name="fileHash" ,value="转换成功后返回的文件MD5值" ,required=true ,dataType="string",paramType="query")})
	@ApiResponses({
			@ApiResponse(code=200 ,response=Map.class, message="固定返回模型，json字符串表现形式,data:主要字段内容，code：返回结果码，message：返回结果信息")})
	@PostMapping(value = "/findUCloudFileId")
	@ResponseBody
	public Map<String,Object> findUCloudFileId(@RequestParam String fileHash,HttpServletRequest request){
	
		IResult<String> result = ptsConvertService.selectFcsFileInfoPOByFileHash(fileHash,HttpUtils.getSessionUserID(request));
		if(result.isSuccess()) {
			return JsonResultUtils.successMapResult(result.getData());
		}else {
			return JsonResultUtils.failMapResult(result.getMessage());
		}
	}
	
	
	
	
	
	@ApiOperation(value = "查询跳转优云文件夹的Id")
	@ApiResponses({
			@ApiResponse(code=200 ,response=Map.class, message="固定返回模型，json字符串表现形式,data:主要字段内容，code：返回结果码，message：返回结果信息")})
	@PostMapping(value = "/findUCloudFolderId")
	@ResponseBody
	public Map<String,Object> findUCloudFolderId(HttpServletRequest request){
		String cookie = request.getHeader(UaaConsts.COOKIE);
		IResult<String> result =  statisticsService.findUCloudFolderId(cookie);
		if(result.isSuccess()) {
			return JsonResultUtils.successMapResult(result.getData());
		}else {
			return JsonResultUtils.failMapResult(result.getMessage());
		}
	}
	
	
	@ApiOperation(value = "根据fileHash查询转换结果")
	@ApiImplicitParams({
			@ApiImplicitParam(name="fileHash" ,value="异步转换接口返回的MD5值" ,required=true ,dataType="string",paramType="query"),
			@ApiImplicitParam(name="mergeYc" ,value="是否合并上传优云的结果，true表示需要合并，false表示不需要合并" ,required=false ,dataType="Boolean",paramType="query")})
	@ApiResponses({
			@ApiResponse(code=200 ,response=Map.class, message="固定返回模型，json字符串表现形式,data:主要字段内容，code：返回结果码，message：返回结果信息")})
	@PostMapping(value = "/fileInfo")
	@ResponseBody
	public Map<String,Object> getFileInfoByFileHash(@RequestParam String fileHash,
													@RequestParam(required = false, defaultValue = "false")Boolean mergeYc,
													HttpServletRequest request){
		String header = request.getHeader("User-Agent");
//		Boolean ycApp =StringUtils.containsOnly("优云APP",header) || StringUtils.containsOnly("babeliphone",header);
		Boolean ycApp = false;
		IResult<FcsFileInfoBO> result =  statisticsService.getFileInfoByFileHash(fileHash,ycApp,mergeYc,HttpUtils.getSessionUserID(request));
		if(result.isSuccess()) {
			return JsonResultUtils.successMapResult(result.getData());
		}else {
			return JsonResultUtils.failMapResult(result.getMessage());
		}
	}
	


	
	@ApiOperation(value = "获取PDF工具集当前运行的模块")
	@ApiResponses({
			@ApiResponse(code=200 ,response=Map.class, message="固定返回模型，json字符串表现形式,data:主要字段内容，code：返回结果码，message：返回结果信息")})
	@GetMapping(value = "/modules")
	@ResponseBody
	public Map<String,Object> getFileInfoByFileHash(){
		return JsonResultUtils.successMapResult(statisticsService.getPdfMudules());
	}



	@ApiOperation(value = "统计注册来源")
	@ApiImplicitParams({
			@ApiImplicitParam(name="sourceId" ,value="注册来源" ,required=true ,dataType="String",paramType="query")})
	@ApiResponses({
			@ApiResponse(code=200 ,response=Map.class, message="固定返回模型，json字符串表现形式,data:主要字段内容，code：返回结果码，message：返回结果信息")})
	@PostMapping(value = "/register")
	@ResponseBody
	public Map<String,Object> statisticsRegister(@RequestParam("url")String url,HttpServletRequest request){
		IResult<String> result = statisticsService.statisticsRegister(HttpUtils.getSessionUserID(request),url);
		if(result.isSuccess()) {
			return JsonResultUtils.successMapResult(result.getData());
		}else {
			return JsonResultUtils.failMapResult(result.getMessage());
		}
	}


	@ApiOperation(value = "获取首页展示数量")
	@ApiResponses({
			@ApiResponse(code=200 ,response=Map.class, message="固定返回模型，json字符串表现形式,data:主要字段内容，code：返回结果码，message：返回结果信息")})
	@GetMapping(value = "/show")
	@ResponseBody
	public Map<String,Object> getShowNum(){
		IResult<Long> result = statisticsService.getShowNum();
		if(result.isSuccess()) {
			return JsonResultUtils.successMapResult(result.getData());
		}else {
			return JsonResultUtils.failMapResult(result.getMessage());
		}
	}


	@ApiOperation(value = "查询用户资源包次数消费记录")
	@ApiImplicitParams({
			@ApiImplicitParam(name="page" ,value="页数" ,required=false ,dataType="int",paramType="query",example = "1"),
			@ApiImplicitParam(name="rows" ,value="行数" ,required=false ,dataType="int",paramType="query",example = "10")})
	@ApiResponses({
			@ApiResponse(code=200 ,response=Map.class, message="固定返回模型，json字符串表现形式,data:主要字段内容，code：返回结果码，message：返回结果信息")})
	@GetMapping(value = "/consume")
	@JSON(type = FcsFileInfoPO.class, include = "id,destFileName,srcFileName,destFileSize,srcFileSize,gmtCreate,gmtModified,module,isRPT")
	public Map<String,Object> getConsumeRecord(HttpServletRequest request,
											   @RequestParam(required = false, defaultValue = "1") int page,
											   @RequestParam(required = false, defaultValue = "10") int rows){
		IResult<PageInfo<FcsFileInfoPO>> result = statisticsService.getConsumeRecord(HttpUtils.getSessionUserID(request),page,rows);
		if(result.isSuccess()) {
			return JsonResultUtils.successMapResult(result.getData());
		}else {
			return JsonResultUtils.failMapResult(result.getMessage());
		}
	}




	@ApiOperation(value = "查询PDF工具集当前版本")
	@GetMapping(value = "/version")
	@ResponseBody
	public Map<String,Object> getVersion(){
		return JsonResultUtils.successMapResult("PTS_1.0.01_20081801_Release");
	}


}
