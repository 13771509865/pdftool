package com.neo.web.statistics;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.RedisConsts;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.model.po.FcsFileInfoPO;
import com.neo.model.po.PtsSummaryPO;
import com.neo.model.qo.FcsFileInfoQO;
import com.neo.model.qo.PtsSummaryQO;
import com.neo.service.cache.CacheManager;
import com.neo.service.cache.CacheService;
import com.neo.service.cache.impl.RedisCacheManager;
import com.neo.service.statistics.StatisticsService;

@Controller
@RequestMapping(value = "/statistics")
public class StatisticsController {
	
	@Autowired
	private StatisticsService statisticsService;
	


	/**
	 * 根据userID，查询登录用户三天的转换记录
	 * @return
	 */
	@RequestMapping(value = "/idConvert")
	@ResponseBody
	public Map<String, Object> userConvert(FcsFileInfoQO fcsFileInfoQO,HttpServletRequest request){
		IResult<List<FcsFileInfoPO>> result = statisticsService.selectConvertByUserID(fcsFileInfoQO,request);
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
	@RequestMapping(value = "/convertTimes")
	@ResponseBody
	public Map<String,Object> getConvertTimes(HttpServletRequest request){
		IResult<String>  result = statisticsService.getConvertTimes(request);
		if(result.isSuccess()) {
			return JsonResultUtils.successMapResult();
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
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Map<String, Object> deleteConvert(FcsFileInfoQO fcsFileInfoQO ,HttpServletRequest request){
		IResult<String> result = statisticsService.deleteConvert(fcsFileInfoQO, request);
		if(result.isSuccess()) {
			return JsonResultUtils.successMapResult();
		}else {
			return JsonResultUtils.failMapResult(result.getMessage());
		}
	}
	
	
	
	/**
	 * 根据文档大小，查询转换的数量
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/convertBySize")
	@ResponseBody
	public Map<String, Object> convertBySize(HttpServletRequest request){
		IResult<PtsSummaryPO> result =  statisticsService.selectCountBySize();
		if(result.isSuccess()) {
			return JsonResultUtils.successMapResult();
		}else {
			return JsonResultUtils.failMapResult(result.getMessage());
		}
	}
	

	/**
	 *	查询每个ip每天的转换量
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ipConvert")
	@ResponseBody
	public Map<String, Object> ipConvert(PtsSummaryQO ptsSummaryQO,HttpServletRequest request){
		IResult<List<PtsSummaryPO>> result = statisticsService.selectCountByIpAndDate(ptsSummaryQO);
		if(result.isSuccess()) {
			return JsonResultUtils.successMapResult();
		}else {
			return JsonResultUtils.failMapResult(result.getMessage());
		}
	}
	
	
	


}
