package com.neo.web.clear;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.util.SysLogUtils;
import com.neo.model.dto.UserClearRequestDto;
import com.neo.service.clear.IClearService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;



/**
 * 注销用户
 * @author xujun
 * @description
 * @create 2020年2月28日
 */
@Api(value = "注销Controller", tags = {"注销Controller"})
@RestController
@RequestMapping(value = "/api")
public class ClearController {


	@Autowired
	private IClearService iClearService;


	/**
	 * 1.检查签名是否一致
	 * 2.返回约束结果
	 * @param nonce
	 * @param sign
	 * @param userClearRequestDto
	 * @return
	 */
	@ApiOperation(value = "删除约束检查接口")
	@PostMapping(value = "/user/clearCheck")
	public ResponseEntity clearCheck(@RequestParam String nonce, @RequestParam String sign, @RequestBody UserClearRequestDto userClearRequestDto) {
		IResult<Integer> checkSignResult = iClearService.checkClearSign(nonce, sign);
		if(!checkSignResult.isSuccess()) {
			return ResponseEntity.ok(buildClearCheckResult(checkSignResult.getData(), checkSignResult.getMessage()));
		}

		return ResponseEntity.ok(buildClearCheckResult(EnumResultCode.E_SUCCES.getValue(), EnumResultCode.E_SUCCES.getInfo()));
	}

	
	
	
	
	/**
	 * 1.检查签名
	 * 2.删除用户转换和权限数据
	 * 3.返回删除结果
	 * @param nonce
	 * @param sign
	 * @param userClearRequestDto
	 * @return
	 */
	@ApiOperation(value = "删除接口")
	@PostMapping(value = "/user/clearData")
	public ResponseEntity clearData(@RequestParam String nonce, @RequestParam String sign, @RequestBody UserClearRequestDto userClearRequestDto) {
		IResult<Integer> checkSignResult = iClearService.checkClearSign(nonce, sign);
		if(!checkSignResult.isSuccess()) {
			return ResponseEntity.ok(buildClearResult(checkSignResult.getData(), checkSignResult.getMessage()));
		}

		try {
			IResult<Integer>  clearResult= iClearService.clearUserData(userClearRequestDto.getId());
			return ResponseEntity.ok(buildClearResult(EnumResultCode.E_SUCCES.getValue(), EnumResultCode.E_SUCCES.getInfo()));

		} catch (Exception e) {
			e.printStackTrace();
			SysLogUtils.error("用户删除接口异常,用户id为:" + userClearRequestDto.getId(), e);
			return ResponseEntity.ok(buildClearResult(EnumResultCode.E_USER_CLEAR_FAIL.getValue(), EnumResultCode.E_USER_CLEAR_FAIL.getInfo()));
		}

	}





	/**
	 * 构建删除检查结果返回值
	 *
	 * @author zhoufeng
	 * @date 2020/2/28
	 */
	private Map<String, Object> buildClearCheckResult(Integer code, String message) {
		Map<String, Object> result = new HashMap<>();
		code = code == null ? EnumResultCode.E_USER_CLEAR_ILLEGAL.getValue() : code;
		Boolean passed = (code == 0);
		result.put("code", code);
		result.put("msg", message);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("passed", passed);
		resultMap.put("details", null);
		result.put("result", resultMap);
		return result;
	}

	/**
	 * 构建删除结果返回值
	 *
	 * @author zhoufeng
	 * @date 2020/2/28
	 */
	private Map<String, Object> buildClearResult(Integer code, String message) {
		Map<String, Object> result = new HashMap<>();
		code = code == null ? EnumResultCode.E_USER_CLEAR_FAIL.getValue() : code;
		Boolean isSucceed = (code == 0);
		result.put("code", code);
		result.put("msg", message);
		result.put("result", isSucceed);
		return result;
	}






}
