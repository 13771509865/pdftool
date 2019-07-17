package com.neo.service.convert.pdfToWord;

import com.neo.commons.cons.ResultCode;
import com.neo.commons.cons.constants.PathConsts;
import com.neo.commons.util.SysLog4JUtils;
import com.neo.service.convert.dcc.PDFConvert;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("convertPdfToWordService")
public class ConvertPdfToWordService {
	public static final String DestFilePathKey = "destFilePath";
	public static final String IsConvertTimeout = "isConvertTimeout";
	@Autowired
	private PDFConvert pdfConvert;


	
	public  Map<String, Object> convert(String srcFilePath,String destFilePath,final String ext,String timeout) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			long time = System.currentTimeMillis();
			SysLog4JUtils.info("开始转换文件, srcFilePath:" + srcFilePath + ", ext:" + ext);
			
			if (ext.equals(PathConsts.PDF)) {	
			convertToWord(srcFilePath, destFilePath,resultMap,timeout);
			}
			
			int code = (Integer)resultMap.get("code");
			File destFile = new File((String)resultMap.get(DestFilePathKey));
			if (code != ResultCode.E_SUCCES.getValue() ||
				!destFile.exists() || destFile.length() <= 0) {
				resultMap.remove(DestFilePathKey);
				if(code==ResultCode.E_SUCCES.getValue()) {
					resultMap.put("code", ResultCode.E_CONVERSION_FAIL.getValue());
				}
			}
			SysLog4JUtils.info("文件转换的结果, srcFilePath:" + srcFilePath + 
					", code:" + code + ", 耗时:" + (System.currentTimeMillis() - time) + "ms");
		} catch (Exception e) {
			SysLog4JUtils.error(e);
			e.printStackTrace();
		}
		return resultMap;
		
	}
	
	
	/**
	 * Description: 转换为Word
	 * @param srcFilePath
	 * @param resultMap
	 * Date: 2018年11月1日
	 * Author: dh
	 * @throws Exception 
	 */
	private  void convertToWord(String srcFilePath,String destFilePath,Map<String, Object> resultMap,String timeout) throws Exception {

//		String destFilePath = FilePathHelper.getTempFilePath(PathConsts.DOCX);
//		String wordExt = PathConsts.DOC;
//		if(wordExt!=null&&wordExt.equals(PathConsts.DOC)) {
//			destFilePath= FilePathHelper.getTempFilePath(PathConsts.DOC);
//		}
		boolean isConvertTimeout = false;
		
		 System.out.println("destFilePath:" + destFilePath);

		 int code=ResultCode.E_CONVERSION_FAIL.getValue();
		 int yozoCode=ResultCode.E_CONVERSION_FAIL.getValue();
//		 功能：该接口实现指定PDF文件到Word文件的转换
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

		yozoCode =convertPdfToWord(srcFilePath, destFilePath,timeout);
		 System.out.println("convertPdfToWord:" + yozoCode);

			switch(yozoCode) {
			case 0:code=ResultCode.E_SUCCES.getValue();break;
			case 1:code=ResultCode.E_INPUT_FILE_NOTFOUND.getValue();break;
			case 2:code=ResultCode.E_INPUT_FILE_OPENFAILED.getValue();break;
			case 3:code=ResultCode.E_CONVERSION_FAIL.getValue();break;
			case 4:code=ResultCode.E_INPUT_FILE_ENCRPTED.getValue();break;
			case 5:code=ResultCode.E_OUTPUT_FILE_WRONG_EXT.getValue();break;
			case 6:code=ResultCode.E_DCC_EXPIRE.getValue();break;
			case 7:code=ResultCode.E_CONVERSION_TIMEOUT.getValue();isConvertTimeout = true;break;
			case 8:code=ResultCode.E_INVALID_PARAM.getValue();break;

			default:code = ResultCode.E_CONVERSION_FAIL.getValue();
			}

		
		System.out.println("code:" + code);

		resultMap.put("code", code);
		resultMap.put(DestFilePathKey, destFilePath);
		resultMap.put(IsConvertTimeout, isConvertTimeout);
	}
	
	private   int  convertPdfToWord(String srcFilePath,String destFilePath,String timeout) throws Exception {
		int yozoCode=ResultCode.E_CONVERSION_FAIL.getValue();
		yozoCode =pdfConvert.convertPdfToWord(srcFilePath, destFilePath,timeout);
		return yozoCode;
	}
}
