package com.neo.service.convert;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumConvertType;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.ResultCode;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.util.SysLog4JUtils;
import com.neo.config.ConvertConfig;
import com.neo.service.convert.convertCenter.ConvertFileService;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * ${DESCRIPTION} 转码服务
 * 
 * @authore sumnear
 * @create 2018-12-10 20:40
 */
@Service("convertService")
public class ConvertService  {
	@Autowired
	private ConvertConfig convertConfig;

	@Autowired
	private ConfigProperty sysConfig;

	@Autowired
	private ConvertFileService convertFileService;
	public static final String DEST_FILE_PATH_KEY = "destFilePath";
	public static final String IS_CONVERT_TIMEOUT = "isConvertTimeout";
	public static final String CODE = "code";

	/**
	 * Description:转码服务
	 * 
	 * @param parame
	 * @return
	 */
	public IResult<Map<String, Object>> convert(String parame) {
//		Long start=System.currentTimeMillis();

		Map<String, Object> resultMap = convertResult(parame);
		IResult<Map<String, Object>> checkConvertResult = checkConvert(resultMap);
//	      System.out.println("csc convert time: "+(System.currentTimeMillis()-start)+"ms" );

		return checkConvertResult;
	}

	/**
	 * Description:获取转码结果信息
	 * 
	 * @param parame
	 * @return
	 */
	public Map<String, Object> convertResult(String parame) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> convertMap = new HashMap<String, Object>();
		try {
			JSONObject info = JSONObject.parseObject(parame);
//		System.out.println(info.toString());
//		System.out.println(config.getInputDir());

//		String srcPath=config.getInputDir()+info.getString("path");
			Integer convertType = info.getInteger("convertType");
			String srcPath = info.getString("srcPath");
			String destPath = info.getString("destPath");
			String timeout = info.getString("convertTimeOut");
			String convertId = info.getString("convertId");
			String fileHash = info.getString("fileHash");
			String signatureImgPath = info.getString("signatureImgPath");
			Integer isSignature = info.getInteger("isSignature");

			String useWhichJar = EnumConvertType.useWhichJar(convertType);
			String ext = FilenameUtils.getExtension(srcPath).toLowerCase();

			Integer isDelSrc = info.getInteger("isDelSrc");

//			if(SysConstant.PDF2WORDJAR.equals(useWhichJar)) {
//				convertMap = convertFileService.convert(srcPath,destPath,ext,timeout,useWhichJar);
//			}else if(SysConstant.DCCJAR.equals(useWhichJar)) {
//				convertMap = convertFileService.convert(srcPath,destPath,ext,timeout,useWhichJar);
//			}
			Boolean isCheckDestFile = EnumConvertType.isCheckDestFile(convertType);
			if (isSignature != null && isSignature.equals(1) && signatureImgPath != null) {
				isCheckDestFile = false;
			}

			String srcParamPath = sysConfig.getInputDir() + File.separator + SysConstant.CONVERTPARAMETERDIR
					+ File.separator + fileHash;

			boolean checkJar = convertConfig.checkJar(useWhichJar);
			if (!checkJar) {
				resultMap.put(CODE, ResultCode.E_UNSUPPORT_FAIL.getValue().toString());
			}

			convertMap = convertFileService.convert(srcPath, destPath, ext, timeout, useWhichJar, isCheckDestFile,
					srcParamPath);

			if (convertMap.get(CODE) != null) {
				resultMap.put(CODE, convertMap.get(CODE).toString());
			}
			if (convertMap.get(DEST_FILE_PATH_KEY) != null) {
				resultMap.put(DEST_FILE_PATH_KEY, convertMap.get(DEST_FILE_PATH_KEY).toString());
			}
			if (convertMap.get(IS_CONVERT_TIMEOUT) != null) {
				resultMap.put(IS_CONVERT_TIMEOUT, convertMap.get(IS_CONVERT_TIMEOUT).toString());
			}
			if (convertMap.get(SysConstant.FILEATTRIBUTEVO) != null) {
				resultMap.put(SysConstant.FILEATTRIBUTEVO, convertMap.get(SysConstant.FILEATTRIBUTEVO).toString());
			}

			//isDelSrc为1时，源文件存在要删除源文件
			if (isDelSrc != null && isDelSrc.equals(1)) {
				if (srcPath != null && !"".equals(srcPath)) {
					File dir = new File(srcPath);
					if (dir.exists()) {
						deleteDir(dir);
					}
				}
			}
		} catch (Exception e) {
			SysLog4JUtils.error(e);
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * Description:判断转码结果信息
	 * 
	 * @param resultMap
	 * @return
	 */
	private IResult<Map<String, Object>> checkConvert(Map<String, Object> resultMap) {
		int code = ResultCode.E_CONVERSION_FAIL.getValue();
		if (resultMap.get(CODE) != null) {
			code = Integer.valueOf((String) resultMap.get(CODE));
		}
		if (code != ResultCode.E_SUCCES.getValue()) {
			return DefaultResult.failResult(ResultCode.getEnum(code).getInfo(), resultMap);
		} else {
			return DefaultResult.successResult(resultMap);
		}

	}

	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * 
	 * @param dir 将要删除的文件目录
	 * @return boolean Returns "true" if all deletions were successful. If a
	 *         deletion fails, the method stops attempting to delete and returns
	 *         "false".
	 * @throws IOException
	 */
	public static void deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				deleteDir(new File(dir, children[i]));
			}
		}
		// 目录此时为空，可以删除
		dir.delete();
	}
}
