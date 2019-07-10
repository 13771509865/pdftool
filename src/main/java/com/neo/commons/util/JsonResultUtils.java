package com.neo.commons.util;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.ResultCode;


public class JsonResultUtils {
	private static ThreadLocal<ObjectMapper> mapperHolder = new ThreadLocal<ObjectMapper>();

	private static ObjectMapper getObjectMapper() {
		ObjectMapper mapper = mapperHolder.get();
		if (mapper == null) {
			mapper = new ObjectMapper();
			mapperHolder.set(mapper);
			return mapper;
		}
		return mapper;
	}

	public static String success() {
		return success(null, null);
	}

	public static String success(Object data) {
		return success(data, null);
	}

	public static String success(Object data, String message) {
		return buildJsonResult(EnumResultCode.E_SUCCES.getValue(), data,
				message == null ? EnumResultCode.E_SUCCES.getInfo() : message);
	}

	public static String fail() {
		return fail(null);
	}

	public static String fail(String message) {
		return buildJsonResult(EnumResultCode.E_FAIL.getValue(), null,
				message == null ? EnumResultCode.E_FAIL.getInfo() : message);
	}

	public static String buildJsonResult(Integer result, Object data, String message) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("errorcode", result);// 本次请求是否成功
		params.put("message", message);// 用户封装信息，典型的是检验出错信息
		params.put("data", data == null ? "" : data);// 本次请求需要返回的数据
		try {
			return getObjectMapper().writeValueAsString(params);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}
	public static String buildSuccessJsonResult(Map<String,Object> map) {
		map.put("result", EnumResultCode.E_SUCCES.getValue());// 本次请求是否成功
		map.put("message", "操作成功");// 用户封装信息，典型的是检验出错信息
		try {
			return getObjectMapper().writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}
	public static String buildFailJsonResult(Map<String,Object> map) {
		map.put("result", EnumResultCode.E_FAIL.getValue());// 本次请求是否成功
		map.put("message", "操作失败");// 用户封装信息，典型的是检验出错信息
		try {
			return getObjectMapper().writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static String buildFailJsonResultByResultCode(ResultCode code){
		return buildJsonResult(code.getValue(),null,code.getInfo());
	}
	
	public static Map<String, Object> successMapResult() {
		return successMapResult(null, EnumResultCode.E_SUCCES.getInfo());
	}

	public static Map<String, Object> successMapResult(Object data) {
		return successMapResult(data, EnumResultCode.E_SUCCES.getInfo());
	}

	public static Map<String, Object> successMapResult(Object data, String message) {
		return buildMapResult(EnumResultCode.E_SUCCES.getValue(),data,message);
	}
	public static Map<String, Object> successAppendMapResult(Map<String,Object> resultMap) {
		resultMap.put("errorcode", EnumResultCode.E_SUCCES.getValue());// 本次请求是否成功
		resultMap.put("message", EnumResultCode.E_SUCCES.getInfo());// 用户封装信息，典型的是检验出错信息
		return resultMap;
	}
	public static Map<String, Object> failMapResult() {
		return failMapResult(null);
	}

	public static Map<String, Object> failMapResult(String message) {
		return buildMapResult(EnumResultCode.E_FAIL.getValue(),null, message);
	}
	public static Map<String, Object> failMapResult(Object data ,String message) {
		return buildMapResult(EnumResultCode.E_FAIL.getValue(),data, message);
	}
	
	public static Map<String, Object> buildMapResult(Integer result, Object data, String message) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("errorcode", result);// 本次请求是否成功
		params.put("message", message);// 用户封装信息，典型的是检验出错信息
		params.put("data", data == null ? "" : data);// 本次请求需要返回的数据
		return params;
	}
	
	public static Map<String, Object> failMapResultByCode(ResultCode code){
		return buildMapResult(code.getValue(),null, code.getInfo());
	}

	public static void main(String[] args) {
		System.out.println(success("dddd"));
	}
}
