package com.neo.service.convert.convertCenter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.ConvertConsts;
import com.neo.commons.cons.ResultCode;
import com.neo.commons.cons.SysConstant;
import com.neo.commons.util.SysLog4JUtils;
import com.neo.service.convert.dcc.DccConvert;
import com.neo.service.convert.dcc.PDFConvert;



/**
 * @author yozo_dh
 *
 */
@Service("convertFileService")
public class ConvertFileService {
	@Autowired
	private PDFConvert pdfConvert;

	@Autowired
	private DccConvert dccConvert;



	public static final String DEST_FILE_PATH_KEY = "destFilePath";
	public static final String IS_CONVERT_TIMEOUT = "isConvertTimeout";

	/**
	 * Description: 转换文件
	 * 
	 * @param srcFilePath     源文件路径
	 * @param destFilePath    目标文件路径
	 * @param ext             文件后缀
	 * @param timeout         超时时间
	 * @param useWhichJar     使用jar名称 Date: 2018年11月1日 Author: dh
	 * @param isCheckDestFile
	 * @throws Exception
	 */
	public Map<String, Object> convert(String srcFilePath, String destFilePath, final String ext, String timeout,
			String useWhichJar, Boolean isCheckDestFile, String paramFolder) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			long time = System.currentTimeMillis();
			SysLog4JUtils.info("开始转换文件, srcFilePath:" + srcFilePath + ", ext:" + ext);

			convertFile(srcFilePath, destFilePath, resultMap, timeout, useWhichJar,paramFolder);

			int code = (Integer) resultMap.get("code");
			if (isCheckDestFile) {
				File destFile = new File((String) resultMap.get(DEST_FILE_PATH_KEY));
				boolean noLength=true;
				if(destFile.isDirectory()) {
					if(destFile.list().length>0) {
						noLength=false;
					}
				}
				else {
					if(destFile.length() > 0) {
						noLength=false;
					}
				}
				if (code != ResultCode.E_SUCCES.getValue() || !destFile.exists() || noLength) {
					resultMap.remove(DEST_FILE_PATH_KEY);
					if (code == ResultCode.E_SUCCES.getValue()) {
						resultMap.put("code", ResultCode.E_CONVERSION_FAIL.getValue());
					}
				}
			}
			SysLog4JUtils.info("文件转换的结果, srcFilePath:" + srcFilePath + ", code:" + code + ", 耗时:"
					+ (System.currentTimeMillis() - time) + "ms");
		} catch (Exception e) {
			SysLog4JUtils.error(e);
			e.printStackTrace();
		}
		return resultMap;

	}

	/**
	 * Description: 转换文件
	 * 
	 * @param srcFilePath  源文件路径
	 * @param destFilePath 目标文件路径
	 * @param resultMap    返回值map
	 * @param timeout      超时时间
	 * @param useWhichJar  使用jar名称 Date: 2018年11月1日 Author: dh
	 * @throws Exception
	 */
	private void convertFile(String srcFilePath, String destFilePath, Map<String, Object> resultMap, String timeout,
			String useWhichJar, String srcParamPath) throws Exception {


		String fileAttributeVO = null;

		boolean isConvertTimeout = false;

		System.out.println("destFilePath:" + destFilePath);

		int code = ResultCode.E_CONVERSION_FAIL.getValue();
		int yozoCode = ResultCode.E_CONVERSION_FAIL.getValue();
//		 功能：该接口实现指定文件的转换
//		 参数：sourceFileName 源文件绝对路径(包含文件名和后缀名)
//		 targetFileName目标文件绝对路径
//		 返回值：
//		 0 转换成功
//		 1：传入的文件，找不到
//		 2：传入的文件，打开失败
//		 3：转换过程异常失败
//		 4：传入的文件有密码
//		 5：targetFileName的后缀名错误
//		 6: 授权过期
//		 7：转换超时(因为默认转换时间是60s，可以通过convert.setTimeout设置)
//  	 8：无效参数
//  	 9：jar命令中参数错误
//  	 10: jar运行异常
		if (ConvertConsts.PDF2WORDJAR.equals(useWhichJar)) {
			yozoCode = convertPdfToWord(srcParamPath, destFilePath, timeout);
		} else if (ConvertConsts.DCCJAR.equals(useWhichJar)) {
			Map<String, Object> result = convertFile(srcParamPath, destFilePath, timeout,srcFilePath);
			yozoCode = (Integer) result.get("code");
			if (result.get(SysConstant.FILEATTRIBUTEVO) != null) {
				fileAttributeVO = (String) result.get(SysConstant.FILEATTRIBUTEVO);
			}
		} 

		System.out.println("convert by " +useWhichJar+" :" + yozoCode);

		switch (yozoCode) {
		case 0:
			code = ResultCode.E_SUCCES.getValue();
			break;
		case 1:
			code = ResultCode.E_INPUT_FILE_NOTFOUND.getValue();
			break;
		case 2:
			code = ResultCode.E_INPUT_FILE_OPENFAILED.getValue();
			break;
		case 3:
			code = ResultCode.E_CONVERSION_FAIL.getValue();
			break;
		case 4:
			code = ResultCode.E_INPUT_FILE_ENCRPTED.getValue();
			break;
		case 5:
			code = ResultCode.E_OUTPUT_FILE_WRONG_EXT.getValue();
			break;
		case 6:
			code = ResultCode.E_DCC_EXPIRE.getValue();
			break;
		case 7:
			code = ResultCode.E_CONVERSION_TIMEOUT.getValue();
			isConvertTimeout = true;
			break;
		case 8:
			code = ResultCode.E_INVALID_PARAM.getValue();
			break;

		default:
			code = ResultCode.E_CONVERSION_FAIL.getValue();
		}

		System.out.println("code:" + code);

		resultMap.put("code", code);
		resultMap.put(DEST_FILE_PATH_KEY, destFilePath);
		resultMap.put(IS_CONVERT_TIMEOUT, isConvertTimeout);
		resultMap.put(SysConstant.FILEATTRIBUTEVO, fileAttributeVO);
	}

	/**
	 * Description: 转换为word
	 * 
	 * @param srcFilePath  源文件路径
	 * @param destFilePath 目标文件路径
	 * @param timeout      超时时间 Date: 2018年11月1日 Author: dh
	 * @throws Exception
	 */
	private int convertPdfToWord(String srcFilePath, String destFilePath, String timeout) throws Exception {
		int yozoCode = ResultCode.E_CONVERSION_FAIL.getValue();
		yozoCode = pdfConvert.convertPdfToWord(srcFilePath, destFilePath, timeout);
		return yozoCode;
	}

	/**
	 * Description: 转换为pdf
	 * 
	 * @param srcParamPath 参数文件路径
	 * @param srcFilePath  源文件路径
	 * @param destFilePath 目标文件路径
	 * @param timeout      超时时间 Date: 2018年11月1日 Author: dh
	 * @throws Exception
	 */
	private Map<String, Object> convertFile(String srcParamPath, String destFilePath, String timeout,String srcFilePath) throws Exception {
		Map<String, Object> result = dccConvert.convertFile(srcParamPath, destFilePath, timeout,srcFilePath);
		return result;
	}

}
