package com.neo.web.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.neo.service.convert.PtsConvertService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.cons.constants.UaaConsts;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.model.bo.FileUploadBO;
import com.neo.model.po.FcsFileInfoPO;
import com.neo.model.po.PtsSummaryPO;
import com.neo.model.qo.FcsFileInfoQO;
import com.neo.model.qo.PtsSummaryQO;
import com.neo.service.statistics.StatisticsService;

import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "查询记录相关Controller", tags = {"查询记录相关Controller"})
@Controller
@RequestMapping(value = "/api/statistics")
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
	
	

	




}
