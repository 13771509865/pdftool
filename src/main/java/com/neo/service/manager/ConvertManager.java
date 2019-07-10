package com.neo.service.manager;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.RedisConsts;
import com.neo.commons.cons.ResultCode;
import com.neo.commons.cons.SysConstant;
import com.neo.commons.util.SysLog4JUtils;
import com.neo.config.SysConfig;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.bo.FileInfoBO;
import com.neo.service.IConvertManager;
import com.neo.service.TicketManager;
import com.neo.service.cache.CacheManager;
import com.neo.service.cache.CacheService;
import com.neo.service.convert.ConvertService;
import com.neo.service.file.FileService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

/**
 * 转换实现类
 * 
 * @author zhouf
 * @create 2018-12-14 11:52
 */
@Service("convertManager")
public class ConvertManager implements IConvertManager {
	@Autowired
	private CacheService<String> cacheService;
	@Autowired
	private ConvertService convertService;
	@Autowired
	private TicketManager ticketManager;
	@Autowired
	private FileService fileService;
	@Autowired
	private SysConfig config;
	
	private final Integer pollTimeOut = 7; //取超时时间

	private final String fileInfoKey = RedisConsts.FileInfoKey;

	@Override
	public IResult<FileInfoBO> dispatchConvert(ConvertParameterBO paramBO) {
		FileInfoBO fileInfo =new FileInfoBO();
		try{
			String convertMd5 = paramBO.getFileHash();
			if (convertMd5 == null) {// 生成文件key失败
				fileInfo.setCode(ResultCode.E_GETCONVERTMD5_FAIL.getValue());
				return DefaultResult.failResult(ResultCode.E_GETCONVERTMD5_FAIL.getInfo(), fileInfo);
			}
			CacheManager<String> cacheManager = cacheService.getCacheManager();
			FileInfoBO cacheResult = selectFromCache(cacheManager,convertMd5);
			if (cacheResult != null) { // 读缓存
				return DefaultResult.successResult(cacheResult);
			} else {// 去转换
				IResult<FileInfoBO> convertByJar = convertByJar(cacheManager, paramBO,fileInfo);
				return convertByJar;
			}
		}catch(Exception e){
			e.printStackTrace();
			SysLog4JUtils.error("dispatchConvert方法转换未知错误了", e);
			fileInfo.setCode(ResultCode.E_SERVER_UNKNOW_ERROR.getValue());
			return DefaultResult.failResult(ResultCode.E_SERVER_UNKNOW_ERROR.getInfo(),fileInfo);
		}
	}

	private FileInfoBO selectFromCache(CacheManager<String> cacheManager, String convertMd5) {
		boolean fileExists = cacheManager.existHashKey(fileInfoKey, convertMd5);
		if (fileExists) { // 文件已转换过
			String fileInfoString = cacheManager.getHashValue(fileInfoKey, convertMd5);
			FileInfoBO fileInfo = FileInfoBO.deserializeData(fileInfoString);
			if (fileInfo != null) {
				String wordStoragePath = fileInfo.getWordStoragePath();
				boolean exists = fileService.isExists(config.getOutputDir(), wordStoragePath);
				if (exists) {
					return fileInfo;
				} else {
					boolean removeResult = cacheManager.deleteHashKey(fileInfoKey, convertMd5);
					if (!removeResult) {
						SysLog4JUtils.error("redis删除key为fileInfoMap中" + convertMd5 + "失败");
					}
					return null;
				}
			}
		}
		return null;
	}

	private IResult<FileInfoBO> convertByJar(CacheManager<String> cacheManager, ConvertParameterBO paramBO,FileInfoBO fileInfo) {
		// TODO 暂时写死了
		String ticket = ticketManager.poll(pollTimeOut);
		if (StringUtils.isEmpty(ticket)) {
			fileInfo.setCode(ResultCode.E_SERVER_BUSY.getValue());
			return DefaultResult.failResult(ResultCode.E_SERVER_BUSY.getInfo(),fileInfo);
		}
		try {
			IResult<String> preResult = preConvert(paramBO);
			if(!preResult.isSuccess()) {
				fileInfo.setCode(ResultCode.E_FILESERVICE_FAIL.getValue());
				return DefaultResult.failResult(preResult.getMessage(),fileInfo);
			}
			IResult<Map<String, Object>> convertResult = convertService.convert(paramBO.toString());
			// 将结果构件FileInfo对象
			if (convertResult.isSuccess()) { // 转换成功
				fileInfo = buildFileInfoBO(paramBO,fileInfo);
				boolean setResult = cacheManager.setHashValue(fileInfoKey, paramBO.getFileHash(), fileInfo.serializeData());
				if (setResult) {// 储存成功
					return DefaultResult.successResult(fileInfo);
				} else {
					fileInfo.setCode(ResultCode.E_REDIS_FAIL.getValue());
					return DefaultResult.failResult("转换成功后缓存fileInfo失败",fileInfo);
				}
			} else { // 转换失败
				fileInfo.setCode(Integer.valueOf((String)convertResult.getData().get("code")));
				return DefaultResult.failResult(convertResult.getMessage(),fileInfo);
			}
		} catch (Exception e) {
			fileInfo.setCode(ResultCode.E_SERVER_UNKNOW_ERROR.getValue());
			SysLog4JUtils.error(paramBO.getSrcPath() + "文件转换未知错误", e);
			return DefaultResult.failResult(ResultCode.E_SERVER_UNKNOW_ERROR.getInfo(),fileInfo);
		} finally {
			ticketManager.put(ticket);
		}
	}
	
	private boolean mkBOFile(ConvertParameterBO paramBO) {
		String parame = paramBO.toString();
		File file =new File(paramBO.getSrcPath()).getParentFile();
		String boFilePath = file.getAbsolutePath() + File.separator + SysConstant.CONVERTPARAMETERFILENAME;
		return fileService.writeStringToFile(parame, boFilePath);
	}
	
	/**
	 * 创建父文件夹,创建BO文件
	 * @param paramBO
	 * @return
	 */
	private IResult<String> preConvert(ConvertParameterBO paramBO){
		IResult<String> mkParentPath = mkParentPath(paramBO);
		if(mkParentPath.isSuccess()) {
			boolean mkBOFile = mkBOFile(paramBO);
			if(mkBOFile) {
				return DefaultResult.successResult();
			}else {
				return DefaultResult.failResult("创建转换BO文件失败");
			}
		}else {
			return DefaultResult.failResult(mkParentPath.getMessage());
		}
	}
	
	/**
	 * 创建父文件不然会报错
	 * 
	 * @param paramBO
	 */
	private IResult<String> mkParentPath(ConvertParameterBO paramBO) {
		String destPath = paramBO.getDestPath();
		IResult<String> mkParentdirs = fileService.mkParentdirs(destPath);
		if (mkParentdirs.isSuccess()) {
			return DefaultResult.successResult();
		} else {
			return DefaultResult.failResult(mkParentdirs.getMessage());
		}
	}

	private FileInfoBO buildFileInfoBO(ConvertParameterBO paramBO,FileInfoBO fileInfo) {
		fileInfo.setWordStoragePath(paramBO.getDestRelativePath());
		fileInfo.setFileHash(paramBO.getFileHash());
		fileInfo.setFileName(paramBO.getFileName());
		fileInfo.setStoragePath(paramBO.getSrcRelativePath());
		fileInfo.setCode(ResultCode.E_SUCCES.getValue());
		return fileInfo;
	}
}
