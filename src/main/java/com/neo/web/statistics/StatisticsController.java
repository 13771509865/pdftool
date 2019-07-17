package com.neo.web.statistics;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.commons.cons.IResult;
import com.neo.commons.cons.ResultCode;
import com.neo.commons.util.JsonResultUtils;
import com.neo.service.accessTimes.AccessTimesService;

@Controller
@RequestMapping(value = "statistics")
public class StatisticsController {
	@Autowired
	private AccessTimesService accessTimesService;

//	 @RequestMapping(value = "/ipCount")
//	 @ResponseBody
//	 public Map<String, Object> ipCount() {
//		 IResult<Long> ipCountResult = accessTimesService.getTotalIpCount();
//		 if(ipCountResult.isSuccess()){
//			 return JsonResultUtils.successMapResult(ipCountResult.getData());
//		 }else{
//			 return JsonResultUtils.failMapResultByCode(ResultCode.E_REDIS_FAIL);
//		 }
//	 }
//	 
//	 @RequestMapping(value = "/convertCount")
//	 @ResponseBody
//	 public Map<String, Object> convertCount() {
//		 IResult<Integer> convertCountResult = accessTimesService.getTotalConvertTimesCount();
//		 if(convertCountResult.isSuccess()){
//			 return JsonResultUtils.successMapResult(convertCountResult.getData());
//		 }else{
//			 return JsonResultUtils.failMapResultByCode(ResultCode.E_REDIS_FAIL);
//		 }
//	 }
}
