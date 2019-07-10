package com.neo.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.commons.util.JsonResultUtils;

@Controller
public class TestController {
	
	
	@RequestMapping(value = "/test1")
	@ResponseBody
	public Map<String, Object> startDownload(@RequestBody Object obj) throws InterruptedException {
			Thread.sleep(2000);
			System.out.println(obj);
			return JsonResultUtils.successMapResult();
	}
	@RequestMapping(value = "/test2/{openId}")
	@ResponseBody
	public Map<String, Object> startDownload(@PathVariable("openId") String openId ) throws InterruptedException {
			Thread.sleep(2000);
			System.out.println(openId+"用户喝水");
			return JsonResultUtils.successMapResult();
	}
}
