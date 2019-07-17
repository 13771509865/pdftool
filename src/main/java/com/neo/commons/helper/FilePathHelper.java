/**
 * Description: 
 * Date: 2016年5月11日
 * Author: zhao_yuanchao
 */
package com.neo.commons.helper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neo.commons.cons.constants.PathConsts;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.util.UUIDHelper;

@Component
public class FilePathHelper {
	
	@Autowired
	private ConfigProperty config;
	
	public final String UploadFile = "UploadFile";
	public final String TempFolder = "UploadTemp";
	public final String ConvertFailFolder = "ConvertFail";
	public final String ConvertedFileCacheFolder = "ConvertedFileCache";
	
	public final String cs = PathConsts.Cloud_Separator;
	public final String fs = File.separator;
	
	/* 上传到阿里云的文件一律采用此文件名(不包括扩展名) */
	public final String ActualFileName = "yozo";
	public final String OldActualFileName = "0";//主要是一些转换的文件还在使用
	
	
	public String generateFilePath(String ext) {
		String prefix = PathConsts.DOCUMENT + cs + UUIDHelper.generateUUID() + cs + ActualFileName;
		if (StringUtils.isNotEmpty(ext)) {
			return prefix + "." + ext;
		} else {
			return prefix;
		}
	}
	
	/*
	 * 文件对应的文件夹前缀
	 * 由于阿里云上的文件夹只能以字母、数字或者中文开头, 
	 * 为了处理文件id是负数的情况, 所以所有的文件夹都带有了一个前缀.
	 * !!! 以后逐渐弃用该变量, 存储到云存储的文件都以无意义的uuid代替'b_fileId'路径
	 */
	public final String FileFolderPrefix = "b_";
	
	
	public final String makeFileStoragePath(long fileId, int version, String ext) {
		ext = ext.toLowerCase();
//		if (ConfigHelper.getStorageType() == StorageType.Local) {
			return makeLocalFileStoragePath(fileId, version, ext);
//		} else {
//			return makeCloudFileStoragePath(fileId, version, ext);
//		}
	}
	
	public final String makeFolderStoragePath(long fileId, int version) {
//		if (ConfigHelper.getStorageType() == StorageType.Local) {
			return makeLocalFolderStoragePath(fileId, version);
//		} else {
//			return makeCloudFolderStoragePath(fileId, version);
//		}
	}
	
	/**
	 * 采用云存储方案时使用
	 */
	private final String makeCloudFileStoragePath(long fileId, int version, String ext) {
		// document/b_123456/0/0.jpg	
		if (StringUtils.isNotEmpty(ext)) {
			return makeCloudFolderStoragePath(fileId, version) + cs + ActualFileName + "." + ext.toLowerCase();
		} else {
			return makeCloudFolderStoragePath(fileId, version) + cs + ActualFileName;
		}
	}
	
	private final String makeCloudFolderStoragePath(long fileId, int version) {
		// document/b_123456/0
		return PathConsts.DOCUMENT + cs + FileFolderPrefix + fileId + cs + version;
	}
	
	/**
	 * 采用本地存储方案时使用
	 */
	private final String makeLocalFileStoragePath(long fileId, int version, String ext) {
		/*
		 * d:/mola/BabelStorage/document/20160612/b_1536445015202426/0/0.jpg
		 * /home/mola/BabelStorage/document/20160612/b_1536445015202426/0/0.jpg
		 */
		if (StringUtils.isNotEmpty(ext)) {
			return makeLocalFolderStoragePath(fileId, version) + cs + ActualFileName + "." + ext.toLowerCase();
		} else {
			return makeLocalFolderStoragePath(fileId, version) + cs + ActualFileName;
		}
	}
	
	private final String makeLocalFolderStoragePath(long fileId, int version) {
		/*
		 * d:/mola/BabelStorage/document/20160612/b_1536445015202426/0
		 * /home/mola/BabelStorage/document/20160612/b_1536445015202426/0
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		return PathConsts.DOCUMENT + cs + 
				sdf.format(new Date()) + cs + 
				FileFolderPrefix + fileId + cs + version;
	}
	
	/**
	 * 从云存储文件夹路径中提取原始的文件id, 主要是针对拷贝的情况.
	 */
	public String extractFileId(String folderStoragePath) {
		/* 例如 "30226/document/b_7412488665299962/0" 返回7412488665299962 */
		return folderStoragePath.substring(folderStoragePath.indexOf("_") + 1, 
				folderStoragePath.lastIndexOf(cs));
	}
	
	public String getFileStoragePath(String folderStoragePath, String ext) {
		ext = ext.toLowerCase();
		if (folderStoragePath.startsWith(PathConsts.DOCUMENT) 
//				||ConfigHelper.getStorageType() == StorageType.Local
				) {
			/* 新的云存储路径 */
			if (StringUtils.isNotEmpty(ext)) {
				return folderStoragePath + cs + ActualFileName + "." + ext;
			} else {
				return folderStoragePath + cs + ActualFileName;
			}
		} else {
			/* 针对以前的数据, 提取原来的文件id, 主要是针对拷贝的情况. */
			String fileId = extractFileId(folderStoragePath);
			if (StringUtils.isNotEmpty(ext)) {
				return folderStoragePath + cs + fileId + "." + ext;
			} else {
				return folderStoragePath + cs + fileId;
			}
		}
	}
	
	public String getOldFileStoragePath(String folderStoragePath, String ext) {
		ext = ext.toLowerCase();
		if (folderStoragePath.startsWith(PathConsts.DOCUMENT) 
//				||ConfigHelper.getStorageType() == StorageType.Local
				) {
			/* 新的云存储路径 */
			if (StringUtils.isNotEmpty(ext)) {
				return folderStoragePath + cs + OldActualFileName + "." + ext;
			} else {
				return folderStoragePath + cs + OldActualFileName;
			}
		} else {
			/* 针对以前的数据, 提取原来的文件id, 主要是针对拷贝的情况. */
			String fileId = extractFileId(folderStoragePath);
			if (StringUtils.isNotEmpty(ext)) {
				return folderStoragePath + cs + fileId + "." + ext;
			} else {
				return folderStoragePath + cs + fileId;
			}
		}
	}
	
	public String getConvertPDFStoragePath(String folderStoragePath) {
		if (folderStoragePath.startsWith(PathConsts.DOCUMENT) 
//				||ConfigHelper.getStorageType() == StorageType.Local
				) {
			/* 新的云存储路径 */
			return folderStoragePath + cs + OldActualFileName + PathConsts.ConvertPDFSuffix;
		} else {
			/* 针对以前的数据, 提取原来的文件id, 主要是针对拷贝以前旧文件的情况. */
			String fileId = extractFileId(folderStoragePath);
			return folderStoragePath + cs + fileId + PathConsts.ConvertPDFSuffix;
		}
	}
	
	/*
	 * 使用此接口不用考虑以前的旧数据
	 * 主要是针对大图片转的pdf而新加的接口
	 */
	public String getImagePDFStoragePath(String folderStoragePath) {
		return folderStoragePath + cs + OldActualFileName + PathConsts.ConvertPDFSuffix;
	}
	
	public String getConvertPNGStoragePath(String folderStoragePath) {
		if (folderStoragePath.startsWith(PathConsts.DOCUMENT) 
//				||ConfigHelper.getStorageType() == StorageType.Local
				) {
			/* 新的云存储路径 */
			return folderStoragePath + cs + OldActualFileName + PathConsts.ConvertPNGSuffix;
		} else {
			/* 针对以前的数据, 提取原来的文件id, 主要是针对拷贝以前旧文件的情况. */
			String fileId = extractFileId(folderStoragePath);
			return folderStoragePath + cs + fileId + PathConsts.ConvertPNGSuffix;
		}
	}
	
	public String getConvertJpgStoragePath(String folderStoragePath) {
		return folderStoragePath + cs + OldActualFileName + ".jpg";
	}
	
	public String getConvertMP3StoragePath(String folderStoragePath) {
		return folderStoragePath + cs + OldActualFileName + PathConsts.ConvertAudioSuffix;
	}
	
	public String getConvertMP4StoragePath(String folderStoragePath) {
		return folderStoragePath + cs + OldActualFileName + PathConsts.ConvertVideoSuffix;
	}
	
	public String getConvertDaeStoragePath(String folderStoragePath) {
		return folderStoragePath + cs + OldActualFileName + PathConsts.ConvertThreeDSuffix;
	}
	
//	public static String getWatermarkImagePath(long enterpriseId) {
//		if (ConfigHelper.isPublicCloud()) {
//			return PathConsts.AliyunWatermarkFolder + cs + enterpriseId + ".png";
//		} else {
//			return ConfigHelper.getLocalWatermarkPath() + File.separator + enterpriseId + ".png";
//		}
//	}
	

	/*
	 * folderStoragePath 取值如下
	 * 旧路径: document/b_123123312/0 或者 29305/document/b_123145623312/1
	 * 新路径: document/79b0abcc5f34e9baf4a4b5064c8cc914
	 */
	public String getSliceImageFolderStoragePath(String folderStoragePath, int scale) {
		return folderStoragePath + cs + 
				PathConsts.SliceFolder + cs + scale;
	}
	
	public String getSliceImageFileStoragePath(String folderStoragePath, int scale, int row, int column) {
		String name = row + "_" + column + ".jpg";
		return getSliceImageFolderStoragePath(folderStoragePath, scale) + cs + name;
	}
	

	/*
	 * folderStoragePath 取值如下
	 * 旧路径: document/b_123123312/0 或者 29305/document/b_123145623312/1
	 * 新路径: document/79b0abcc5f34e9baf4a4b5064c8cc914
	 */
	public String getBigImageDispayPath(String folderStoragePath) {
		return folderStoragePath + cs + PathConsts.BigImageDisplaySuffix;
	}
	
	/*
	 * 语音批注等的附件跟随fileId
	 */
	public String getAttachmentStoragePath(long fileId, String fileName) {
		return PathConsts.DOCUMENT + cs + 
				FileFolderPrefix + fileId + cs + 
				PathConsts.AttachmentFolder + cs + fileName;
	}
	
	public String getThumbnailStoragePath(String storageFolderPath, int index) {
		return storageFolderPath + cs + PathConsts.ThumbFolder + cs + index + ".jpg";
	}

	public String getAvatarCloudPath(long userId) {
		return (userId + cs + PathConsts.USER + cs + "avatar" + userId + ".jpg");
	}
	
	public String getAliyunAvatarParentPath(long userId) {
		return (userId + cs + PathConsts.USER);
	}
	
	public String getUserHomeFolder() {
		return System.getProperty("user.home");
	}
	
	public String getConvertTempFolder() {
//		return System.getProperty("user.home");
//		return ConfigHelper.getStringOption("convertPath");
		return config.getOutputDir();
//		return "E:\\convertTempFolder";
	}
	
	
	/*
	 * 文档系统中固定的临时文件夹路径
	 */
	public String getTempFolder() {
		String tempFolder = getConvertTempFolder() + fs + UploadFile + fs + TempFolder;
		File folder = new File(tempFolder);
		if (!folder.exists()) {
			try {
				folder.mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tempFolder;
	}
	
	/*
	 * 获得一个随机的临时文件路径
	 */
	public String getTempFilePath(String ext) {
		String tempFolder = getTempFolder();
		if (StringUtils.isNotEmpty(ext)) {
			return  tempFolder + fs + UUIDHelper.generateUUID() + "." + ext.toLowerCase();
		} else {
			return  tempFolder + fs + UUIDHelper.generateUUID();
		}
	}
	
	/*
	 * 在文档系统中固定的临时文件夹路径下生成一个随机的文件夹路径
	 */
	public String getATempFolder() {
		String tempFolder = getTempFolder() + fs + UUIDHelper.generateUUID();
		File folder = new File(tempFolder);
		if (!folder.exists()) {
			try {
				folder.mkdir();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tempFolder;
	}
	
	/*
	 * 文件转换失败后，移动到该路径下去。
	 */
	public String getConvertFailFolder() {
		String failFolder = getConvertTempFolder() + fs + UploadFile + fs + ConvertFailFolder;
		File folder = new File(failFolder);
		if (!folder.exists()) {
			try {
				folder.mkdir();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return failFolder;
	}
	
	/*
	 * 获得一个随机的文件路径用于缓存转换的文件信息
	 */
	public String getAConvertedCacheFile() {
		String CacheFolder = getConvertedFileCacheFolder();
		return CacheFolder + fs + UUIDHelper.generateUUID() + ".txt";
	}
	
	public String getConvertedFileCacheFolder() {
		String cacheFolder = getConvertTempFolder() + fs + UploadFile + fs + ConvertedFileCacheFolder;
		File folder = new File(cacheFolder);
		if (!folder.exists()) {
			try {
				folder.mkdir();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cacheFolder;
	}
	
	
	/*
	 * 打包下载文件的绝对路径
	 */
	public String getZipFileStoragePath(String name) {
		return PathConsts.ZIP + cs + UUIDHelper.generateUUID() + cs + name;
	}
	
	/**
	 * 文件转换失败后要保存转换失败的文件, 以备将来研究失败的原因.
	 */
	public void saveFailConvertFile(String ext, long fileId, File file) {
		String failFolder = getConvertFailFolder();
		
		String path = "";
		if (StringUtils.isNotEmpty(ext)) {
			path = failFolder + fs + fileId + "." + ext;
		} else {
			path = failFolder + fs + fileId;
		}
		file.renameTo(new File(path));
	}
	
	/**
	 * 移动文件并返回目标文件的路径
	 */
	public String renameFile(File file, String ext) {
		String path = getTempFilePath(ext.toLowerCase());
		file.renameTo(new File(path));
		return path;
	}
	
}
