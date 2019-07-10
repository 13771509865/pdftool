package com.neo.service.convert.dcc;

import com.neo.commons.SysConfig;
import com.neo.commons.cons.ResultCode;
import com.neo.commons.helper.FilePathHelper;
import com.neo.commons.util.SysLog4JUtils;
import com.sun.jna.Library;
import com.sun.jna.Native;

import org.apache.commons.exec.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Service("pDFConvert")
public class PDFConvert {
	private static final String SPACE = " ";
	@Autowired
	private SysConfig config;
	@Autowired
	private FilePathHelper filePathHelper;
	
	private  int pid = 0;
//	 功能：该接口实现指定PDF文件到Word文件的转换
//	 参数：sourceFileName 源文件绝对路径(包含文件名和后缀名)
//	 targetFileName目标文件绝对路径
//	 返回值：
//	 0 转换成功
//	 1：传入的文件，找不到
//	 2：传入的文件，打开失败
//	 3：转换过程异常失败
//	 4：传入的文件有密码
//	 5：targetFileName的后缀名错误
//	 6: 授权过期
//	 7：转换超时(因为默认转换时间是60s，可以通过convert.setTimeout设置)
	public  int PDF2Word(String srcFilePath, String destFilePath) {
		int code =ResultCode.E_CONVERSION_FAIL.getValue();
		
//		String timeout = config.getConvertTimeout();
//		String p2wConfig= config.getPdf2WordConfig();
		String p2wConfig=config.getPdf2WordConfig();
//		String p2wConfig= ConfigHelper.getStringOption("pdf2WordConfig");
		
		
		CommandLine cmdLine = null;
		
		String p2wPath=config.getPdf2WordPath()+File.separator;
//		String p2wPath="D:/gitLab/pdf2word2/";
//		String p2wPath=ConfigHelper.getStringOption("pdf2WordPath")+File.separator;
		
//		StringBuffer sb = new StringBuffer("java -Djava.ext.dirs=");
//		sb.append(p2wPath+"ext");
		
//		
		StringBuffer sb = new StringBuffer("java ");
		sb.append(SPACE);
		sb.append(p2wConfig);
		sb.append(SPACE);
		sb.append("-jar");
		sb.append(SPACE);
		sb.append(p2wPath+"yocloudPDF2Word.jar");
		sb.append(SPACE);
		sb.append("\"");
		sb.append(srcFilePath);
		sb.append("\"");
		sb.append(SPACE);
		sb.append("\"");
		sb.append(destFilePath);
		sb.append("\"");
//		sb.append(SPACE);
//		sb.append(timeout);
		
		String cmd = sb.toString().replaceAll("\\\\", "\\\\\\\\");
		
		Map map = new HashMap();
		map.put("p2wPath", p2wPath+"yocloudPDF2Word.jar");
		map.put("srcFilePath", new File(srcFilePath));
		map.put("destFilePath", new File(destFilePath));

		if (iSwindows()) {
			cmdLine = new CommandLine("cmd.exe");
			cmdLine.addArgument("/C");
			cmdLine.addArgument("java ");
			cmdLine.addArgument(p2wConfig);
			cmdLine.addArgument(" -jar ");

			cmdLine.addArgument("${p2wPath}");
			cmdLine.addArgument("${srcFilePath}");
			cmdLine.addArgument("${destFilePath}");
//			cmdLine.addArgument(timeout);

			cmdLine.setSubstitutionMap(map);
		} else {
			String shTempFile = createScriptFileForLinux(cmd);
			cmdLine = new CommandLine("bash");
			cmdLine.addArgument(shTempFile);
		}
		System.out.println(cmd);

//		DefaultExecutor executor = new DefaultExecutor();  
//		ExecuteWatchdog watchdog = new ExecuteWatchdog(TimeConsts.Convert_Execute_Timeout*1000);  
//		executor.setWatchdog(watchdog); 
//		int[] evalues=new int[11];
//		for(int i=0;i<11;i++) {
//			evalues[i]=i;
//		}
//		executor.setExitValues(evalues);
		try {
			Runtime rt =Runtime.getRuntime();
//			Process proc = rt.exec("cmd.exe /c "+cmd);
			Process proc = rt.exec(cmd);
			 pid = getPid(proc);

//			System.out.println("pid:"+pid);
//			int kill=killProcess(pid);
//			System.out.println("kill:"+kill);

			code=proc.waitFor();
			
			String s;
			BufferedReader bf = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedReader bfe = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
			
			while ((s=bf.readLine())!=null)System.out.println(s);
			while ((s=bfe.readLine())!=null)System.out.println(s);
			
//			code = executor.execute(cmdLine);
			System.out.println("dccPdfToWord:"+code);

		} 		
		catch (Exception e) {
			e.printStackTrace();
			SysLog4JUtils.info(cmdLine.toString());
			SysLog4JUtils.error(e);
			code=ResultCode.E_CONVERSION_FAIL.getValue();
			return code;
		}
		catch (OutOfMemoryError e) {
			e.printStackTrace();
			code=ResultCode.E_CONVERSION_FAIL.getValue();
			SysLog4JUtils.error(e);
			System.out.println("OutOfMemoryError1");
			return code;
		}
		
		File file = new File(destFilePath);
		return (file.exists() && file.length() > 0) ? ResultCode.E_SUCCES.getValue() : code;
	}
	
	
	private String createScriptFileForLinux(String cmd) {
		String cmdFilePath = filePathHelper.getTempFilePath("sh");
		PrintWriter writer;
		try {
			writer = new PrintWriter(new FileOutputStream(cmdFilePath));
			writer.println("#!/bin/bash");
			writer.println(cmd);
			writer.close();
		} catch (FileNotFoundException e) {
			SysLog4JUtils.error(e);
			e.printStackTrace();
		}
		
		return cmdFilePath;
	}
	

    static interface Kernel32 extends Library {

    public static Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class);

    public int GetProcessId(Long hProcess);
    }
    
    public static int getPid(Process p) {
    Field f;

    if (iSwindows()) {
        try {
            f = p.getClass().getDeclaredField("handle");
            f.setAccessible(true);
            int pid = Kernel32.INSTANCE.GetProcessId((Long) f.get(p));
            return pid;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    } else {
        try {
            f = p.getClass().getDeclaredField("pid");
            f.setAccessible(true);
            int pid = Integer.valueOf(f.get(p).toString());
            return pid;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    return 0;
    }
    
    public static boolean iSwindows() {
    	String os = System.getenv("os");
    	if(os!=null&&os.toLowerCase().contains("win")) {
    		return true;
    	}
    	return false;
    }
    
    public int killProcess(int pid) throws Exception {
    	String cmd = "";
    	if(iSwindows()) {
    		cmd = "taskkill -f -pid "+pid;
    	}
    	else {
    		cmd = "kill -9 "+pid;
    	}
		Runtime rt =Runtime.getRuntime();
		System.out.println("pdf convert jar :"+cmd);
		Process proc = rt.exec(cmd);
		return proc.waitFor();
    }
    
	public int convertPdfToWord(String srcFilePath, String destFilePath, String timeout) throws Exception {
		Integer result = ResultCode.E_CONVERSION_FAIL.getValue();
		Callable<Integer> task =new Callable<Integer>() {
			@Override
			public Integer call() throws Exception{
	             //设置执行响应时间的方法体
                int code = PDF2Word(srcFilePath,destFilePath);
//                System.out.println("code : "+code);
                return code;
                }
            };
            ExecutorService exeservices = Executors.newSingleThreadExecutor();
            Future<Integer> future = exeservices.submit(task);
            try {
                //设置方法
            	 result = future.get(Long.valueOf(timeout), TimeUnit.SECONDS);
//            System.out.println("打印result"+result);
            } catch (Exception e) {
                e.printStackTrace();
                //异常处理的方法
                System.err.println("pdf convert timeout: "+timeout+" seconds");
                exeservices.shutdown();
                killProcess(pid);
                future.cancel(true);
    			return 7;
			}
			return result;
	}
}
