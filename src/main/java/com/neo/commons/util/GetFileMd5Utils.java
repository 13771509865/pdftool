package com.neo.commons.util;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 获取文件md5
 * @author zhouf
 * @create 2018-12-14 11:47
 *
 */
public class GetFileMd5Utils {
	//获取文件的md5
	public static String getFileMD5(File file) {
	    if (!file.isFile()) {
	        return null;
	    }
	    FileInputStream in = null;
	    try {
	    	in = new FileInputStream(file);
	    	String fileMd5 = DigestUtils.md5Hex(in);
			return fileMd5;
	    } catch (Exception e) {
	        e.printStackTrace();
	        SysLogUtils.error("获取"+file.getAbsolutePath()+"文件MD5失败");
	        return null;
	    } finally {
	        try {
	        	if(in!=null){
	        		in.close();
	        	}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	
}
