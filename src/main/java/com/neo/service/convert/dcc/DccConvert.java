//package com.neo.service.convert.dcc;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.commons.exec.CommandLine;
//import org.apache.commons.exec.DefaultExecuteResultHandler;
//import org.apache.commons.exec.DefaultExecutor;
//import org.apache.commons.exec.ExecuteWatchdog;
//import org.apache.commons.lang.StringUtils;
//
//import com.neo.commons.cons.TimeConsts;
//import com.neo.commons.helper.FilePathHelper;
//import com.neo.commons.util.ErrorCodeUtil;
//
//
//
//public class DccConvert {
//	private static final String SPACE = " ";
////	 功能：该接口实现指定MS文件到PDF文件的转换
////	 参数：sourceFileName 源文件绝对路径(包含文件名和后缀名)
////	 targetFileName目标文件绝对路径
////	 返回值：
////	 0 转换成功
////	 1：传入的文件，找不到
////	 2：传入的文件，打开失败
////	 3：转换过程异常失败
////	 4：传入的文件有密码
////	 5：targetFileName的后缀名错误
////	 6: 授权过期
////	 7：转换超时(因为默认转换时间是60s，可以通过convert.setTimeout设置)
//	public static int convertMsToPdf(String srcFilePath, String destFilePath) {
//		int code =3;
//		String os = System.getenv("os");
//		String dccConfig= ConfigHelper.getStringOption("dccConfig");
//		
//		CommandLine cmdLine = null;
//	
//		String dccPath=ConfigHelper.getStringOption("dccPath")+File.separator;
//		StringBuffer sb = new StringBuffer("java -Djava.ext.dirs=");
//		sb.append(dccPath+"ext");
//		sb.append(SPACE);
////		sb.append("-Xmx1024m");
////		sb.append(SPACE);
////		sb.append("-XX:MaxPermSize=256m");
//		sb.append(dccConfig);
//		sb.append(SPACE);
//		sb.append("-jar");
//		sb.append(SPACE);
//		sb.append(dccPath+"yocloudConvert.jar");
//		sb.append(SPACE);
//		sb.append(srcFilePath);
//		sb.append(SPACE);
//		sb.append(destFilePath);
//		String cmd = sb.toString().replaceAll("\\\\", "\\\\\\\\");
//
//		System.out.println(cmd);
//		if (os!=null&&os.toLowerCase().contains("win")) {
//			cmdLine = new CommandLine("cmd.exe");
//			cmdLine.addArgument("/C");
//			cmdLine.addArgument(cmd);
//		} else {
//			String shTempFile = createScriptFileForLinux(cmd);
//			cmdLine = new CommandLine("bash");
//			cmdLine.addArgument(shTempFile);
//		}
//		
//		DefaultExecuteResultHandler resultHandler=new DefaultExecuteResultHandler();
//		DefaultExecutor executor = new DefaultExecutor();  
//		ExecuteWatchdog watchdog = new ExecuteWatchdog(TimeConsts.Convert_Execute_Timeout*1000);  
//		executor.setWatchdog(watchdog); 
//		int[] evalues=new int[8];
//		for(int i=0;i<8;i++) {
//			evalues[i]=i;
//		}
//		executor.setExitValues(evalues);
//		try {
//			code = executor.execute(cmdLine);
//			System.out.println("dccMsToPdf:"+code);
//		} catch (IOException e) {
//			e.printStackTrace();
//			return code;
//		}
//		
//		File file = new File(destFilePath);
//		return (file.exists() && file.length() > 0) ? ErrorCodeUtil.OK : code;
//	}
//	private static String createScriptFileForLinux(String cmd) {
//		String cmdFilePath = FilePathHelper.getTempFilePath("sh");
//		PrintWriter writer;
//		try {
//			writer = new PrintWriter(new FileOutputStream(cmdFilePath));
//			writer.println("#!/bin/bash");
//			writer.println(cmd);
//			writer.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		
//		return cmdFilePath;
//	}
//}
