package com.neo.service.convert;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumConvertType;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.ResultCode;
import com.neo.commons.cons.SysConstant;
import com.neo.commons.util.SysLog4JUtils;
import com.neo.service.IConvertService;
import com.neo.service.convert.pdfToWord.ConvertPdfToWordService;


/**
 * ${DESCRIPTION}
 *转码服务
 * @authore sumnear
 * @create 2018-12-10 20:40
 */
@Service("convertService")
public class ConvertService implements IConvertService{
	@Autowired
	private ConvertPdfToWordService convertPdfToWordService;
	public static final String DestFilePathKey = "destFilePath";
	public static final String IsConvertTimeout = "isConvertTimeout";
	
	/**
	 * Description:转码服务
	 * @param parame
	 * @return
	 */
	@Override
	public IResult<Map<String, Object>> convert(String parame) {

		Map<String, Object> resultMap =  convertResult(parame);
		IResult<Map<String, Object>> checkConvertResult = checkConvert(resultMap);

		return checkConvertResult;
	}
	
	/**
	 * Description:获取转码结果信息
	 * @param parame
	 * @return
	 */
	@Override
	public Map<String, Object> convertResult(String parame) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> convertMap = new HashMap<String, Object>();
		try {
			JSONObject info = JSONObject.parseObject(parame);
//		System.out.println(info.toString());
//		System.out.println(config.getInputDir());

//		String srcPath=config.getInputDir()+info.getString("path");
			Integer convertType=info.getInteger("convertType");
			String srcPath=info.getString("srcPath");
			String destPath=info.getString("destPath");
			String timeout=info.getString("convertTimeOut");
			
			String useWhichJar = EnumConvertType.useWhichJar(convertType);
			if(SysConstant.PDF2WORDJAR.equals(useWhichJar)) {
				String ext = FilenameUtils.getExtension(srcPath).toLowerCase();
				convertMap = convertPdfToWordService.convert(srcPath,destPath,ext,timeout);
			}else if(SysConstant.DCCJAR.equals(useWhichJar)) {
				//TODO DCC convert
			}

			if( convertMap.get("code")!=null) {
				resultMap.put("code", convertMap.get("code").toString());
			}
			if( convertMap.get(DestFilePathKey)!=null) {
				resultMap.put(DestFilePathKey, convertMap.get(DestFilePathKey).toString());
			}
			if( convertMap.get(IsConvertTimeout)!=null) {
				resultMap.put(IsConvertTimeout, convertMap.get(IsConvertTimeout).toString());
			}
		} catch (Exception e) {
			SysLog4JUtils.error(e);
			e.printStackTrace();
		}
		return resultMap;
	}

	
	private IResult<Map<String, Object>> checkConvert(Map<String, Object> resultMap) {
		int code =ResultCode.E_CONVERSION_FAIL.getValue();
		if(resultMap.get("code")!=null) {
			code = Integer.valueOf((String) resultMap.get("code")) ;
		}
		if(code!=ResultCode.E_SUCCES.getValue()) {
			return DefaultResult.failResult(ResultCode.getEnum(code).getInfo(),resultMap);
		}
		else {
			return DefaultResult.successResult(resultMap);
		}

	}

}
