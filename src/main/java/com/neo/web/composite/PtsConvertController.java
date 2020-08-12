package com.neo.web.composite;

import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.entity.ConvertEntity;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.service.auth.impl.AuthManager;
import com.neo.service.convert.PtsConvertParamService;
import com.neo.service.convert.PtsConvertService;
import com.neo.service.convert.async.AsyncConvertManager;
import com.yozosoft.auth.client.security.UaaToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 转码控制器
 *
 * @authore xujun
 * @create 2019-07-23
 */

@Api(value = "转换相关Controller", tags = {"转换相关Controller"})
@Controller
@RequestMapping(value = "/composite")
public class PtsConvertController {

	@Autowired
	private AuthManager authManager;

	@Autowired
	private PtsConvertService ptsConvertService;

	@Autowired
	private PtsConvertParamService ptsConvertParamService;

	@Autowired
	private AsyncConvertManager asyncConvertManager;


	@ApiOperation(value = "同步转换")
	@PostMapping(value = "/convert")
	@ResponseBody
	public Map<String, Object> convert(@RequestBody ConvertParameterBO convertBO, HttpServletRequest request) {
		if (convertBO.getSrcFileSize() == null) {
			return JsonResultUtils.failMapResult(EnumResultCode.E_NOTALL_PARAM.getInfo());
		}
		UaaToken uaaToken = HttpUtils.getUaaToken(request);

		ConvertEntity convertEntity = ptsConvertParamService.buildConvertEntity(convertBO, request);
		IResult<FcsFileInfoBO> result = ptsConvertService.dispatchConvert(convertBO, uaaToken, convertEntity);

		//必须放在转换失败是否记缓存判断前面，否则redis有了记录会导致首次转换失败也不算失败率
		ptsConvertService.updatePtsSummay(result, convertBO, convertEntity);

		//转换失败归还次数
		if (!result.isSuccess()) {
			ptsConvertService.returnConvertNum(convertEntity,authManager.getAuthCode(convertBO));
			return JsonResultUtils.buildMapResult(result.getData().getCode(), result.getData(), result.getMessage());
		}
		return JsonResultUtils.successMapResult(result.getData());
	}




	@ApiOperation(value = "异步转换")
	@PostMapping(value = "/asyncConvert")
	@ResponseBody
	public Map<String, Object> mqconvert(@RequestBody ConvertParameterBO convertBO, HttpServletRequest request)  {
		UaaToken uaaToken = HttpUtils.getUaaToken(request);
		ConvertEntity convertEntity = ptsConvertParamService.buildConvertEntity(convertBO, request);
		IResult<FcsFileInfoBO> result = asyncConvertManager.dispatchConvert(convertBO, uaaToken, convertEntity);
		if (result.isSuccess()) {
			return JsonResultUtils.successMapResult(result.getData());
		} else {
			return JsonResultUtils.buildMapResult(result.getData().getCode(), result.getData(), result.getMessage());
		}
	}



}
