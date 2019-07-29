package com.neo.web.statistics;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.commons.cons.IResult;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.model.po.FcsFileInfoPO;
import com.neo.service.accessTimes.AccessTimesService;
import com.neo.service.statistics.StatisticsService;

@Controller
@RequestMapping(value = "statistics")
public class StatisticsController {
	@Autowired
	private AccessTimesService accessTimesService;
	
	@Autowired
	private StatisticsService statisticsService;


	/**
	 * 根据userID，查询登录用户三天的转换记录
	 * @return
	 */
	@RequestMapping(value = "/idConvert")
	@ResponseBody
	public Map<String, Object> userConvert(HttpServletRequest request){
		IResult<List<FcsFileInfoPO>> result = statisticsService.selectConvertByUserID(request);
		if(result.isSuccess()) {
			return JsonResultUtils.successMapResult(result.getData());
		}else {
			return JsonResultUtils.failMapResult(result.getMessage());
		}
		
	}

	
	/**
	 * 用户转换删除记录
	 * @param filehash
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Map<String, Object> deleteConvert(FcsFileInfoPO fcsFileInfoPO ,HttpServletRequest request){
		IResult<String> reuslt = statisticsService.deleteByFileHash(fcsFileInfoPO, request);
		if(reuslt.isSuccess()) {
			return JsonResultUtils.successMapResult();
		}else {
			return JsonResultUtils.failMapResult(reuslt.getMessage());
		}
	}
	
	
	
	
	/**
	 * 根据文档大小，查询转换成功失败的数量
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/convertBySize")
	@ResponseBody
	public Map<String, Object> convertBySize(HttpServletRequest request){
		
		return null;
	}
	

	/**
	 *	查询每个ip每天的转换量
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ipConvert")
	@ResponseBody
	public Map<String, Object> ipConvert(HttpServletRequest request){
		
		return null;
	}
	
	
	


}
