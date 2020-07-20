package com.neo.web.statistics;

import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.UaaConsts;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.model.qo.FcsFileInfoQO;
import com.neo.service.convert.PtsConvertService;
import com.neo.service.statistics.StatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
	@GetMapping(value = "/convertTimes")
	@ResponseBody
	public Map<String,Object> getConvertTimes(HttpServletRequest request){
		IResult<Map<String,Object>>  result = statisticsService.getConvertTimes(HttpUtils.getSessionUserID(request));
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
	@GetMapping(value = "/auth")
	@ResponseBody
	public Map<String,Object> getAuth(HttpServletRequest request){
		IResult<Map<String,Object[]>>  result = statisticsService.getAuth(HttpUtils.getSessionUserID(request));
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
	@PostMapping(value = "/fileInfo")
	@ResponseBody
	public Map<String,Object> getFileInfoByFileHash(@RequestParam String fileHash){
		IResult<FcsFileInfoBO> result =  statisticsService.getFileInfoByFileHash(fileHash);
		if(result.isSuccess()) {
			return JsonResultUtils.successMapResult(result.getData());
		}else {
			return JsonResultUtils.failMapResult(result.getMessage());
		}
	}
	


	
	@ApiOperation(value = "获取PDF工具集当前运行的模块")
	@GetMapping(value = "/modules")
	@ResponseBody
	public Map<String,Object> getFileInfoByFileHash(){
		return JsonResultUtils.successMapResult(statisticsService.getPdfMudules());
	}



	@ApiOperation(value = "统计注册来源")
	@PostMapping(value = "/register")
	@ResponseBody
	public Map<String,Object> statisticsRegister(@RequestParam("sourceId")String sourceId,HttpServletRequest request){
		IResult<String> result = statisticsService.statisticsRegister(sourceId,HttpUtils.getSessionUserID(request));
		if(result.isSuccess()) {
			return JsonResultUtils.successMapResult(result.getData());
		}else {
			return JsonResultUtils.failMapResult(result.getMessage());
		}
	}


	@ApiOperation(value = "获取首页展示数量")
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



}
