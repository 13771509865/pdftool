package com.neo.web;

import com.neo.commons.cons.IResult;
import com.neo.commons.cons.ResultCode;
import com.neo.commons.cons.SysConstant;
import com.neo.commons.util.JsonResultUtils;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.bo.FileInfoBO;
import com.neo.service.convertParameterBO.ConvertParameterBOService;
import com.neo.service.manager.ConvertManager;
import com.neo.service.manager.MqConvertManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *  转码接口
 *  分为同步，异步（asyn开头的方法），消息队列（mq开头的方法），
 * @authore sumnear
 * @create 2018-12-10 20:36
 */

@Controller
public class ConvertController
{
	@Autowired
	private ConvertManager convertManager;
	@Autowired
	private MqConvertManager mqConvertManager;
	
	@Autowired
	private ConvertParameterBOService convertParameterBOService;
	
    @RequestMapping(value = "/convert")
    @ResponseBody
    public Map<String, Object> convert(@RequestBody ConvertParameterBO convertBO,HttpServletRequest request)  {
//        SysLog4JUtils.info(parameter.toString());
    	convertParameterBOService.buildConvertParameterBO(convertBO);
        IResult<ResultCode> checkResult = convertParameterBOService.checkParam(convertBO);
        if(!checkResult.isSuccess()){
        	return JsonResultUtils.buildMapResult(checkResult.getData().getValue(), null, checkResult.getMessage());
        }
        IResult<FileInfoBO> result = convertManager.dispatchConvert(convertBO);
        if(result.isSuccess()){
        	FileInfoBO fileInfoBO = result.getData();
        	//session中插入值,用于下载接口认证
        	setSession(request,fileInfoBO);
        	request.setAttribute(SysConstant.CONVERT_RESULT, ResultCode.E_SUCCES.getValue()); //用于拦截器统计信息
        	return JsonResultUtils.successMapResult(fileInfoBO);
        }else{
        	return JsonResultUtils.buildMapResult(result.getData().getCode(), null, result.getMessage());
        }
    }
    
    @RequestMapping(value = "/mqconvert")
    @ResponseBody
    public Map<String, Object> mqconvert(@RequestBody ConvertParameterBO convertBO)  {
//        SysLog4JUtils.info(parameter.toString());
    	mqConvertManager.Producer(convertBO);
    	return JsonResultUtils.successMapResult();
    }
    
    private void setSession(HttpServletRequest request,FileInfoBO fileInfoBO){
    	HttpSession httpSession = request.getSession();
    	String fileHash = fileInfoBO.getFileHash();
    	httpSession.setAttribute(fileHash, fileInfoBO);
    }
}
