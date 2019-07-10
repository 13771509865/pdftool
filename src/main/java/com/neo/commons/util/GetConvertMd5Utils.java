package com.neo.commons.util;

import java.io.File;
import java.lang.reflect.Field;

import com.neo.model.bo.ConvertParameterBO;

/**
 * 根据文件和参数构建唯一key
 * @author zhouf
 * @create 2018-12-14 11:42
 */
public class GetConvertMd5Utils {
	
	public static String getConvertMd5(ConvertParameterBO param){
		String result =null;
		String input = param.getSrcPath();
		if(input == null ||"".equals(input)){
			return null;
		}
		File file =new File(input);
		String fileMd5 = GetFileMd5Utils.getFileMD5(file);
		//通过反射拿ConvertParamBO的值
		Class<ConvertParameterBO> paramBOClass = (Class) param.getClass();
		Field[] fs = paramBOClass.getDeclaredFields(); 
	    String paramHashcode = getValidParam(fs,param);
	    if(paramHashcode!=null &&fileMd5!=null){
	    	result = fileMd5+paramHashcode;
	    	return result;
	    }else{
	    	return null;
	    }
	}
	
	//哪些参数特殊处理
	private static String getValidParam(Field[] fs,ConvertParameterBO param){
		String paramMd5 = null; 
		try{
			for(int i = 0 ; i < fs.length; i++){ 
				Field f = fs[i]; 
				f.setAccessible(true); //设置属性是可以访问的  
				switch(f.getName()){
				case "srcPath" :
				case "destPath" :
				case "srcRelativePath" :
				case "destRelativePath" :
				case "callBack" :
				case "fileName" :
				case "fileHash" :
					break; 
				default :
					Object val = f.get(param); 
					paramMd5 +=val;
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			SysLog4JUtils.error("获取"+param.toString()+"对象HashCode失败");
			return null;
		}
		return paramMd5.hashCode()+"";
	}
	
//	private static String getParamValue(String name){
//		switch(name){
//		case "page" :
//		case "bookMark" :
//			return null;
//		default :
//			return true;
//		}
//	}
}
