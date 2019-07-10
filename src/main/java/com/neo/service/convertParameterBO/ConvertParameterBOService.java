package com.neo.service.convertParameterBO;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumConvertType;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.ResultCode;
import com.neo.commons.cons.SysConstant;
import com.neo.commons.util.GetConvertMd5Utils;
import com.neo.commons.util.UUIDHelper;
import com.neo.config.SysConfig;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.service.file.FileService;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service("convertParameterBOService")
public class ConvertParameterBOService {

	@Autowired
	private SysConfig config;
	@Autowired
	private FileService fileService;
	
	public static void main(String[] args) {
		String a ="E:\\a\\b.files\\c.docx";
		String b ="E:\\a\\b.files\\c.docx";
		System.out.println(a.hashCode());
		System.out.println(b.hashCode());
//		File file =new File(a).getParentFile();
//		System.out.println(file.getName());
//		System.out.println(FilenameUtils.getExtension(a));
//		System.out.println(FilenameUtils.getBaseName(a));
//		System.out.println(FilenameUtils.getName(a));
	}
	/**
	 * 构建ConvertParameterBO对象
	 * @author zhouf
	 * @param convertBO
	 */
	public void buildConvertParameterBO(ConvertParameterBO convertBO){
		String srcRelative =convertBO.getSrcRelativePath();
		if(StringUtils.isNotEmpty(srcRelative)){
			String srcRoot = config.getInputDir();
			convertBO.setSrcPath(srcRoot + File.separator + srcRelative);
			convertBO.setFileName(FilenameUtils.getBaseName(srcRelative));
			String convertTimeOut = convertBO.getConvertTimeOut();
			if(StringUtils.isEmpty(convertTimeOut)){ //默认配置转换超时时间
				convertBO.setConvertTimeOut(config.getConvertTimeout());
			}
		}
	}
	
	/**
	 * 根据转换类型设置输出相关数据
	 * @author zhouf
	 * @param convertBO
	 */
	private void setDestFileByType(ConvertParameterBO convertBO){
		EnumConvertType enumType = null;
		enumType = EnumConvertType.getEnum(convertBO.getConvertType());
		if(enumType!=null){
			String desRoot = config.getOutputDir();
			String destFileName;
//			String tempName = UUIDHelper.generateUUID();
			String tempName = convertBO.getFileHash();
			String targetType = enumType.getExt(); // 根据转换类型获取文件后缀
			if (enumType.isImageType()) {
				// 如果是多文件类型
				convertBO.setDestRelativePath(tempName);
				convertBO.setDestPath(desRoot + File.separator + tempName);
			} else {
				destFileName = SysConstant.FILENAME+"."+targetType;
				String fileName = convertBO.getFileName();
				convertBO.setFileName(fileName+"."+targetType);
				convertBO.setDestRelativePath(tempName + File.separator + destFileName);
				convertBO.setDestPath(desRoot + File.separator + tempName + File.separator + destFileName);
			}
		}
	}
	
	/**
	 * 检查ConvertParameterBO参数合法性
	 * @author zhouf
	 * @param convertBO
	 * @return
	 */
	public IResult<ResultCode> checkParam(ConvertParameterBO convertBO){
		String srcPath = convertBO.getSrcPath();
		String fileName = convertBO.getFileName();
		Integer convertType = convertBO.getConvertType();
		boolean inputDir = srcPath != null && !"".equals(srcPath);
		boolean checkInput=inputDir? new File(srcPath).exists():false;
		boolean checkFileName = fileName != null && !"".equals(fileName);
		boolean checkType = EnumConvertType.getEnum(convertType)!=null;
		if (checkInput && checkFileName && checkType) {
			File srcFile =new File(srcPath);
			File newSrcFile = fileService.reNameFile(srcFile, SysConstant.FILENAME);
			if(newSrcFile==null) {
				return DefaultResult.failResult(ResultCode.E_FILESERVICE_FAIL.getInfo(),ResultCode.E_FILESERVICE_FAIL);
			}
			String newSrcPath = newSrcFile.getAbsolutePath();
			convertBO.setSrcPath(newSrcPath);
			convertBO.setSrcRelativePath(newSrcPath.substring(config.getInputDir().length()));
			String convertMd5 = GetConvertMd5Utils.getConvertMd5(convertBO);
			convertBO.setFileHash(convertMd5);
			setDestFileByType(convertBO); //这个里面的操作要不影响生成文件md5
			return DefaultResult.successResult();
		}else{
			return DefaultResult.failResult(ResultCode.E_INVALID_PARAM.getInfo(),ResultCode.E_INVALID_PARAM);
		}
	}
}
