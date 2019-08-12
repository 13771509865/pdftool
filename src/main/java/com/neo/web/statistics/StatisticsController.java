package com.neo.web.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.util.JsonResultUtils;
import com.neo.model.bo.FileUploadBO;
import com.neo.model.po.FcsFileInfoPO;
import com.neo.model.po.PtsSummaryPO;
import com.neo.model.qo.FcsFileInfoQO;
import com.neo.model.qo.PtsSummaryQO;
import com.neo.service.statistics.StatisticsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "查询记录相关Controller", tags = {"查询记录相关Controller"})
@Controller
@RequestMapping(value = "/statistics")
public class StatisticsController {
	
	@Autowired
	private StatisticsService statisticsService;
	


	/**
	 * 根据userID，查询登录用户三天的转换记录
	 * @return
	 */
	@ApiOperation(value = "查询登录用户三天的转换记录")
	@PostMapping(value = "/idConvert")
	@ResponseBody
	public Map<String, Object> userConvert(@RequestBody FcsFileInfoQO fcsFileInfoQO,HttpServletRequest request){
		IResult<Map<String, Object>> result = statisticsService.selectConvertByUserID(fcsFileInfoQO,request);
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
	public Map<String,Object> getConvertTimes( HttpServletRequest request){
		IResult<String>  result = statisticsService.getConvertTimes(request);
		if(result.isSuccess()) {
			return JsonResultUtils.successMapResult(result.getData());
		}else {
			return JsonResultUtils.failMapResult(result.getMessage());
		}
	}
	
	

	
	
	/**
	 * 删除用户的转换记录
	 * @param filehash
	 * @param request
	 * @return
	 */
//	@ApiOperation(value = "删除用户的转换记录")
//	@PostMapping(value = "/delete")
//	@ResponseBody
//	public Map<String, Object> deleteConvert(@RequestBody FcsFileInfoQO fcsFileInfoQO ,HttpServletRequest request){
//		IResult<String> result = statisticsService.deleteConvert(fcsFileInfoQO, request);
//		if(result.isSuccess()) {
//			return JsonResultUtils.successMapResult();
//		}else {
//			return JsonResultUtils.failMapResult(result.getMessage());
//		}
//	}
	
	
/**==================================运营统计数据============================================ */	
	
	
	/**
	 * 根据文档大小，查询转换的数量
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "根据文档大小查询转换的数量")
	@GetMapping(value = "/convertBySize")
	@ResponseBody
	public Map<String, Object> convertBySize(HttpServletRequest request){
		IResult<PtsSummaryPO> result =  statisticsService.selectCountBySize();
		if(result.isSuccess()) {
			return JsonResultUtils.successMapResult(result.getData());
		}else {
			return JsonResultUtils.failMapResult(result.getMessage());
		}
	}
	

	/**
	 * 查询每个ip每天的转换量
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "查询每个ip每天的转换量")
	@PostMapping(value = "/ipConvert")
	@ResponseBody
	public Map<String, Object> ipConvert( PtsSummaryQO ptsSummaryQO,HttpServletRequest request){
		IResult<List<PtsSummaryPO>> result = statisticsService.selectCountByIpAndDate(ptsSummaryQO);
		if(result.isSuccess()) {
			return JsonResultUtils.successMapResult(result.getData());
		}else {
			return JsonResultUtils.failMapResult(result.getMessage());
		}
	}
	
	
	/**
	 * 查询每天的转换量
	 * @param ptsSummaryQO
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "查询每天的转换量")
	@PostMapping(value = "/convertByDay")
	@ResponseBody
	public Map<String, Object> ConvertByDay( PtsSummaryQO ptsSummaryQO,HttpServletRequest request){
		IResult<List<PtsSummaryPO>> result = statisticsService.selectConvertByDay(ptsSummaryQO);
		if(result.isSuccess()) {
			return JsonResultUtils.successMapResult(result.getData());
		}else {
			return JsonResultUtils.failMapResult(result.getMessage());
		}
	}
	
	
	
	/**
	 * 查询上传记录
	 * @param ptsSummaryQO
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "查询上传记录")
	@PostMapping(value = "/uploadTimes")
	@ResponseBody
	public Map<String,Object> uploadTimes(){
		IResult<FileUploadBO> result = statisticsService.getUploadTimes();
		if(result.isSuccess()) {
			return JsonResultUtils.successMapResult(result.getData());
		}else {
			return JsonResultUtils.failMapResult(result.getMessage());
		}
	}
	


}
