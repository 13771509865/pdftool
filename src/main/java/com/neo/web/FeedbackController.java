package com.neo.web;

import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.FeedbackConsts;
import com.neo.commons.cons.constants.UaaConsts;
import com.neo.commons.cons.entity.FeedbackEntity;
import com.neo.commons.cons.entity.HttpResultEntity;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.BindingResultUtils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.model.vo.FeedbackVO;
import com.neo.service.file.SaveBadFileService;
import com.neo.service.httpclient.HttpAPIService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户反馈controller
 *
 * @authore xujun
 * @create 2018-7-15
 */
@Api(value = "用户反馈Controller", tags = {"用户反馈Controller"})
@RestController
public class FeedbackController {

    @Autowired
    private PtsProperty ptsProperty;

    @Autowired
    private HttpAPIService httpAPIService;

    @Autowired
    private SaveBadFileService saveBadFileService;

    @ApiOperation(value = "用户反馈")
    @PostMapping(value = "/feedback")
    public Map<String, Object> convertPdf2word(@RequestBody @Valid FeedbackEntity feedbackEntity, BindingResult bindingResult, HttpServletRequest request) {
        String erroMessage = BindingResultUtils.getMessage(bindingResult);
        if (StringUtils.isNotBlank(erroMessage)) {
            return JsonResultUtils.failMapResult(erroMessage);
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(FeedbackConsts.CONTACT, feedbackEntity.getContactMode());
        params.put(FeedbackConsts.TITLE, feedbackEntity.getContent());
        params.put(FeedbackConsts.APP, FeedbackConsts.PDF);

        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put(UaaConsts.COOKIE, request.getHeader(UaaConsts.COOKIE));
        IResult<HttpResultEntity> result = httpAPIService.doPost(ptsProperty.getFeedback_url(), params, headers);
        if (!result.isSuccess()) {
            return JsonResultUtils.failMapResult(EnumResultCode.E_SERVER_BUSY.getInfo());
        }
        FeedbackVO feedbackVO = JsonUtils.json2obj(result.getData().getBody(), FeedbackVO.class);
        return JsonResultUtils.buildMapResult(feedbackVO.getCode(), feedbackVO.getAskId(), feedbackVO.getMsg());
    }

    @ApiOperation(value = "报错")
    @PostMapping(value = "/report")
    public Map<String, Object> reportError(@RequestParam(value = "errorPath") String errorPath) {
		IResult<Integer> saveResult = saveBadFileService.saveBadFile(ptsProperty.getConvert_fail_dir(), ptsProperty.getReport_error_dir(), errorPath);
		if(saveResult.isSuccess()){
			return JsonResultUtils.successMapResult(null, "已报错!感谢您对永中PDF工具集的支持!给您带来的不便,我们深表歉意!");
		}else{
			return JsonResultUtils.failMapResult(saveResult.getMessage());
		}
    }
}
