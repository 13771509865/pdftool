package com.neo.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.model.bo.FileInfoBO;

/**
 * 取消文件转换接口,并杀掉转换进程
 * @author zhouf
 * @create 2018-12-20 09:49
 *
 */
@Controller
public class CancelConvertController{

	@RequestMapping(value ="/cancelConvert")
	@ResponseBody
	public Map<String, Object> cancelConvert(HttpSession httpSession){
		return null;
	}
}
