package com.neo.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.EnumStatus;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.FeedbackConsts;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.cons.constants.UaaConsts;
import com.neo.commons.cons.entity.FeedbackEntity;
import com.neo.commons.cons.entity.HttpResultEntity;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.BindingResultUtils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.commons.util.StrUtils;
import com.neo.dao.PtsAuthPOMapper;
import com.neo.model.po.PtsAuthPO;
import com.neo.model.qo.PtsAuthQO;
import com.neo.model.vo.FeedbackVO;
import com.neo.service.httpclient.HttpAPIService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户反馈controller
 *
 * @authore xujun
 * @create 2018-7-15
 */
@Api(value = "用户反馈Controller", tags = {"用户反馈Controller"})
@Controller
public class FeedbackController{
	
	@Autowired
	private PtsProperty ptsProperty;
	
	@Autowired
	private HttpAPIService httpAPIService;
	
	@Autowired
	private PtsAuthPOMapper ptsAuthPOMapper;

	@ApiOperation(value = "用户反馈")
	@PostMapping(value = "/feedback")
	@ResponseBody
	public Map<String, Object> convertPdf2word(@RequestBody @Valid FeedbackEntity feedbackEntity ,BindingResult bindingResult, HttpServletRequest request){
		String erroMessage = BindingResultUtils.getMessage(bindingResult);
		if(StringUtils.isNotBlank(erroMessage)) {
			return JsonResultUtils.failMapResult(erroMessage);
		}
		Map<String, Object> params = new HashMap<String,Object>();
		params.put(FeedbackConsts.CONTACT, feedbackEntity.getContactMode());
		params.put(FeedbackConsts.TITLE, feedbackEntity.getContent());
		params.put(FeedbackConsts.APP, FeedbackConsts.PDF);
		
		Map<String, Object> headers = new HashMap<String,Object>();
		headers.put(UaaConsts.COOKIE, request.getHeader(UaaConsts.COOKIE));
		IResult<HttpResultEntity> result = httpAPIService.doPost(ptsProperty.getFeedback_url(), params, headers);
		if(!result.isSuccess()) {
			return JsonResultUtils.failMapResult(EnumResultCode.E_SERVER_BUSY.getInfo());
		}
		FeedbackVO feedbackVO = JsonUtils.json2obj(result.getData().getBody(), FeedbackVO.class);
		return JsonResultUtils.buildMapResult(feedbackVO.getCode(), feedbackVO.getAskId(), feedbackVO.getMsg());
	}
	
	
	
	@ApiOperation(value = "用户反馈")
	@PostMapping(value = "/feed")
	@ResponseBody
	public Map<String, Object> test(String str){ 
		if(!StringUtils.equals(str, "000")) {
			return JsonResultUtils.failMapResult("走开");
		}
		
		List<PtsAuthPO>list = ptsAuthPOMapper.selectPtsAuthPO(new PtsAuthQO(null, EnumStatus.ENABLE.getValue(), null));
		for(PtsAuthPO ptsAuthPO : list) {
			String auth = ptsAuthPO.getAuthCode();
			Map<String,Object> authMap = StrUtils.strToMap(auth, SysConstant.COMMA, SysConstant.COLON);
			for (Map.Entry<String, Object> m : authMap.entrySet()) {
				PtsAuthPO po = new PtsAuthPO();
				po.setAuthCode(m.getKey());
				po.setAuthValue(m.getValue().toString());
				po.setGmtCreate(ptsAuthPO.getGmtCreate());
				po.setGmtExpire(ptsAuthPO.getGmtExpire());
				po.setGmtModified(ptsAuthPO.getGmtModified());
				po.setPriority(ptsAuthPO.getPriority());
				po.setStatus(ptsAuthPO.getStatus());
				po.setUserid(ptsAuthPO.getUserid());
				ptsAuthPOMapper.insertPtsAuthPO(po);
			}
			
		}
		
		return JsonResultUtils.successMapResult();
	}


	
	
	
	
}
