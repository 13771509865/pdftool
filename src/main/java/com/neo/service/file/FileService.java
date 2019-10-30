package com.neo.service.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.IResult;
import com.neo.commons.util.StrUtils;
import com.neo.commons.util.SysLogUtils;

@Service("fileService")
public class FileService {

	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * 
	 * @param dir
	 *            将要删除的文件目录
	 * @return boolean Returns "true" if all deletions were successful. If a
	 *         deletion fails, the method stops attempting to delete and returns
	 *         "false".
	 * @throws IOException
	 */
	public void deleteDir(File dir) {
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

	/**
	 * 删除源文档用
	 * @author zhouf
	 * @param srcPath
	 * @create 2018-12-113 21:07
	 */
	public void deleteSrcFile(String srcPath) {
		File srcFile = new File(srcPath);
		if (srcFile.exists()) {
			if (srcFile.isDirectory()) { //如果是目录直接删目录
				deleteDir(srcFile);
			} else if (srcFile.isFile()) {
				if(srcFile.getParentFile().list().length==1){ //如果父目录下只有这一个文件，就删除父目录
					deleteDir(srcFile.getParentFile());
				}
			}
		}
	}
	
	/**
	 * 创建文件夹
	 * @author zhouf
	 * @create 2018-12-20 08:56
	 * @param path
	 * @return
	 */
	public IResult<String> mkdirs(String path){
		if(StringUtils.isEmpty(path)){
			return DefaultResult.failResult("创建文件夹失败");
		}
		File pathFile = new File(path);
		if(!pathFile.exists()){
			boolean mkdirsResult = pathFile.mkdirs();
			if(!mkdirsResult){
				SysLogUtils.error("创建"+pathFile+"文件夹失败,请检查");
				return DefaultResult.failResult("创建文件夹失败");
			}
		}
		return DefaultResult.successResult();
	}
	
	/**
	 * 创建父文件夹
	 * @param path
	 * @return
	 */
	public IResult<String> mkParentdirs(String path){
		if(StringUtils.isEmpty(path)){
			return DefaultResult.failResult("创建文件夹失败");
		}
		File childFile = new File(path);
		IResult<String> mkdirs = mkdirs(childFile.getParent());
		if(mkdirs.isSuccess()){
			return DefaultResult.successResult();
		}else{
			return DefaultResult.failResult(mkdirs.getMessage());
		}
	}
	
	public boolean isExists(File file){
		return file.exists();
	}
	
	public boolean isExists(String root,String path){
		if(StringUtils.isEmpty(root) || StringUtils.isEmpty(path)){
			return false;
		}else{
			File file =new File(root,path);
			return isExists(file);
		}
	}
	
	/**
	 * 拿文件后缀名
	 * @param fileName
	 * @return
	 */
	public String getFileExt(String fileName){
		if(StringUtils.isEmpty(fileName)){
			return "";
		}
		return FilenameUtils.getExtension(fileName);
	}
	
	public File reNameFile(File oldFile,String fileName){
		String srcpath = oldFile.getAbsolutePath();
		String srcroot = srcpath.substring(0,srcpath.lastIndexOf(File.separator));
		String ext = getFileExt(srcpath);
		File newFile =new File(srcroot,fileName+"."+ext);
		boolean result = oldFile.renameTo(newFile);
		if(result) {
			return newFile;
		}else {
			return null;
		}
	}
	
	public Boolean writeStringToFile(String str,String filePath) {
		try {
			File file =new File(filePath);
			if(!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(filePath, true);
			BufferedWriter bw = new BufferedWriter(fw);
			String cnStr = StrUtils.TranEncode2CN(str);
			bw.write(cnStr);
			bw.close();
			fw.close();
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
