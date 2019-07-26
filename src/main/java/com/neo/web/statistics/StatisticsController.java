package com.neo.web.statistics;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.commons.cons.IResult;
import com.neo.commons.cons.ResultCode;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.service.accessTimes.AccessTimesService;

@Controller
@RequestMapping(value = "statistics")
public class StatisticsController {
	@Autowired
	private AccessTimesService accessTimesService;


	/**
	 * 查询会员三天的转换记录
	 * @return
	 */
	@RequestMapping(value = "/idConvert")
	@ResponseBody
	public Map<String, Object> userConvert(HttpServletRequest request){
		Long userID = HttpUtils.getSessionUserID(request);
		return null;
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
