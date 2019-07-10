package com.neo.model.bo;

import com.alibaba.fastjson.JSON;

/**
 * 转码参数
 *
 * @authore sumnear
 * @create 2018-12-11 19:31
 * 加参数的时候注意GetConvertMd5Utils类
 */
public class ConvertParameterBO
{
	private String convertTimeOut; //转换超时时间
	private String fileName; //真实的生成的文件名
	private String srcPath; //源文件全路径
	private String destPath; //目标文件全路径
	private Integer convertType; //转换类型
	private String srcRelativePath; //源文件相对路径
	private String destRelativePath; //目标文件相对路径
	private String callBack; //回调地址
	private String fileHash; //文件md5
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSrcPath() {
		return srcPath;
	}
	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}
	public String getDestPath() {
		return destPath;
	}
	public void setDestPath(String destPath) {
		this.destPath = destPath;
	}
	public Integer getConvertType() {
		return convertType;
	}
	public void setConvertType(Integer convertType) {
		this.convertType = convertType;
	}
	public String getConvertTimeOut() {
		return convertTimeOut;
	}
	public void setConvertTimeOut(String convertTimeOut) {
		this.convertTimeOut = convertTimeOut;
	}
	public String getSrcRelativePath() {
		return srcRelativePath;
	}
	public void setSrcRelativePath(String srcRelativePath) {
		this.srcRelativePath = srcRelativePath;
	}
	public String getDestRelativePath() {
		return destRelativePath;
	}
	public void setDestRelativePath(String destRelativePath) {
		this.destRelativePath = destRelativePath;
	}
	public String getCallBack() {
		return callBack;
	}
	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}
	public String getFileHash() {
		return fileHash;
	}
	public void setFileHash(String fileHash) {
		this.fileHash = fileHash;
	}
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	
}
