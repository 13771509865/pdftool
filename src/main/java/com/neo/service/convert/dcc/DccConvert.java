package com.neo.service.convert.dcc;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.ResultCode;
import com.neo.commons.cons.SysConstant;
import com.neo.commons.util.MyFileUtils;
import com.neo.commons.util.SysLog4JUtils;
import com.neo.commons.util.UUIDHelper;
import com.neo.service.ConvertConfig;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author yozo_dh
 *
 */
@Service("dccConvert")
@Scope("prototype")
public class DccConvert {
	@Autowired
	private ConvertConfig convertConfig;

	private static final String SPACE = " ";

	private static String CheckDays = "checkdays";
	private static String CheckTimes = "checktimes";
	private static String CheckAvailable = "checkavailable";
	private static String CheckRegisterCode = "checkregistercode";
	public static final String COLON = ":";

	private String handlerProcessBlock(InputStream inputStream) throws IOException {
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		try {
			String buff;
			reader = new BufferedReader(new InputStreamReader(inputStream));
			while ((buff = reader.readLine()) != null) {
				System.out.println(buff);
				sb.append(buff.trim()).append("\n");
			}
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					SysLog4JUtils.error("关闭转码流时异常", e);
				}
			}
		}
		return sb.toString();
	}

	private String createCmd(String config, String jarPath, String srcPath, String tempPath) {
		StringBuffer sb = new StringBuffer("java ");
		if (StringUtils.isNotBlank(convertConfig.getJavaPath())) {
			sb = new StringBuffer(convertConfig.getJavaPath() + "/java ");
		}
		sb.append(SPACE);
		sb.append(config);
		sb.append(SPACE);
		sb.append("-jar");
		sb.append(SPACE);
		sb.append(jarPath);
		sb.append(SPACE);
		sb.append("\"");
		sb.append(srcPath);
		sb.append("\"");
		sb.append(SPACE);
		if (tempPath != null) {
			sb.append("\"");
			sb.append(tempPath);
			sb.append("\"");
			sb.append(SPACE);
		}
		String cmd = sb.toString().replaceAll("\\\\", "\\\\\\\\");
		System.out.println(cmd);
		return cmd;
	}

//	 功能：该接口实现指定文件到PDF文件的转换
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
//  8：无效参数
//  9：jar命令中参数错误
//  10: jar运行异常

	/**
	 * 文件到PDF文件的转换
	 *
	 * @param srcParamPath 源文件地址
	 * @param destFilePath 转换文件地址
	 * @param timeout      超时时间
	 */
	public Map<String, Object> convertFile(String srcParamPath, String destFilePath, String timeout,
			String srcFilePath) {
		Integer result = ResultCode.E_CONVERSION_FAIL.getValue();
		Map<String, Object> resultMap = new HashMap();
		resultMap.put("code", result);

		String uuid = UUIDHelper.generateUUID();
		String srcFolderPath = srcParamPath;
		File srcFile = new File(srcParamPath);
		if (srcFile.isFile() && !srcFile.isDirectory()) {
			srcFolderPath = srcFile.getParent();
		}
		String tempPath = srcFolderPath + File.separator + uuid + File.separator;
		String cmd = createCmd(convertConfig.getDccConfig(), convertConfig.getDccJarPath(), srcParamPath, tempPath);

		Process process = null;
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		try {
			process = Runtime.getRuntime().exec(cmd);
			System.out.println("转码进程hashCode" + ProcessManager.getPid(process) + ":" + srcFilePath);
			InputStream in = process.getInputStream();
			InputStream err = process.getErrorStream();
			Future<String> future1 = executorService.submit(() -> {
				return handlerProcessBlock(in);
			});
			Future<String> future2 = executorService.submit(() -> {
				return handlerProcessBlock(err);
			});
			Long timeOutValue = Long.valueOf(timeout);
			if (process.waitFor(timeOutValue, TimeUnit.SECONDS)) {
				// 执行成功
				int code = process.exitValue();
				System.out.println(
						"转码进程hashCode" + ProcessManager.getPid(process) + ":" + srcFilePath + ",dccConvert:" + code);
				String outData = future1.get();
				String fileAttributeVO = "";
				if (outData.contains(SysConstant.FILEATTRIBUTEVO + COLON)) {
//                    fileAttributeVO = outData.replace(SysConstant.FILEATTRIBUTEVO + ":", "");
					fileAttributeVO = outData.substring(outData.indexOf(SysConstant.FILEATTRIBUTEVO + COLON)
							+ SysConstant.FILEATTRIBUTEVO.length());
					fileAttributeVO = fileAttributeVO.substring(1, fileAttributeVO.indexOf("\n"));
				}
				resultMap.put("code", code);
				resultMap.put(SysConstant.FILEATTRIBUTEVO, fileAttributeVO);
			} else {
				// 超时
				throw new TimeoutException();
			}
		} catch (Exception e) {
			// 异常处理的方法
			System.out.println(
					"转码进程hashcode" + ProcessManager.getPid(process) + ":" + srcParamPath + ":" + e.getMessage());
			e.printStackTrace();
			if (e instanceof TimeoutException) {
				System.err.println("dcc convert timeout: " + timeout + " seconds");
				resultMap.put("code", ResultCode.E_CONVERSION_TIMEOUT.getValue());
			} else {
				System.err.println("dcc convert failed: " + e.getMessage());
				resultMap.put("code", ResultCode.E_CONVERSION_FAIL.getValue());
			}
		} finally {
			executorService.shutdownNow();
			if (process != null) {
				process.destroyForcibly();
			}
			// 杀死进程后删除生成的带“$”的隐藏文件
			File file = new File(srcFilePath);
			if (!file.isDirectory()) {
				String filename = file.getName();
				String folder = file.getParent();
				String tempFilePath = folder + File.separator + "$" + filename;
				if (!ProcessManager.iSwindows()) {
					tempFilePath = folder + File.separator + ".$" + filename;
				}
				File tempFile = new File(tempFilePath);
				if (tempFile.exists()) {
					tempFile.delete();
				}
			}
			MyFileUtils.deleteDir(new File(tempPath));
		}
		return resultMap;
	}

	public String getDays() {
		return getCheckValue(CheckDays);
	}

	public String getTimes() {
		return getCheckValue(CheckTimes);
	}

	public String getAvailable() {
		return getCheckValue(CheckAvailable);
	}

	public String getCheckValue(String checkValue) {
		Integer result = ResultCode.E_CONVERSION_FAIL.getValue();
		String value = "";

		String cmd = createCmd(convertConfig.getDccConfig(), convertConfig.getDccJarPath(), checkValue,null);

		Process process = null;
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		try {
			process = Runtime.getRuntime().exec(cmd);
			System.out.println("转码进程hashCode" + ProcessManager.getPid(process) + ":" + checkValue);
			InputStream in = process.getInputStream();
			InputStream err = process.getErrorStream();
			Future<String> future1 = executorService.submit(() -> {
				return handlerProcessBlock(in);
			});
			Future<String> future2 = executorService.submit(() -> {
				return handlerProcessBlock(err);
			});
			Long timeOutValue = Long.valueOf(300);
			if (process.waitFor(timeOutValue, TimeUnit.SECONDS)) {
				// 执行成功
				result = process.exitValue();
				String outData = future1.get();
				String fileAttributeVO = "";

				if (outData.contains(checkValue + COLON)) {
//                    registerCode = outData.replace(CheckRegisterCode + ":", "");
					value = outData.substring(outData.indexOf(checkValue + COLON) + checkValue.length(),
							outData.length());
					value = value.substring(1, value.indexOf("\n")).trim();

				}
				System.out.println(checkValue + ":" + value);
			} else {
				// 超时
				throw new TimeoutException();
			}
		} catch (Exception e) {
			// 异常处理的方法
			System.out.println("转码进程hashcode" + ProcessManager.getPid(process) + ":" + e.getMessage());
			e.printStackTrace();
		} finally {
			executorService.shutdownNow();
			if (process != null) {
				process.destroyForcibly();
			}
		}
		return value;
	}

	public String getRegisterCode() {
		Integer result = ResultCode.E_CONVERSION_FAIL.getValue();
		String registerCode = null;
		String cmd = createCmd(convertConfig.getDccConfig(), convertConfig.getDccJarPath(), CheckRegisterCode,null);

		Process process = null;
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		try {
			process = Runtime.getRuntime().exec(cmd);
			System.out.println("转码进程hashCode" + ProcessManager.getPid(process) + ":" + CheckRegisterCode);
			InputStream in = process.getInputStream();
			InputStream err = process.getErrorStream();
			Future<String> future1 = executorService.submit(() -> {
				return handlerProcessBlock(in);
			});
			Future<String> future2 = executorService.submit(() -> {
				return handlerProcessBlock(err);
			});
			Long timeOutValue = Long.valueOf(300);
			if (process.waitFor(timeOutValue, TimeUnit.SECONDS)) {
				// 执行成功
				result = process.exitValue();
				String outData = future1.get();
				String fileAttributeVO = "";

				if (outData.contains(CheckRegisterCode + COLON)) {
//                    registerCode = outData.replace(CheckRegisterCode + ":", "");
					registerCode = outData.substring(
							outData.indexOf(CheckRegisterCode + COLON) + CheckRegisterCode.length(), outData.length());
					registerCode = registerCode.substring(1, registerCode.indexOf("\n"));

				}
				System.out.println(CheckRegisterCode + ":" + registerCode);

			} else {
				// 超时
				throw new TimeoutException();
			}
		} catch (Exception e) {
			System.out.println("转码进程hashcode" + ProcessManager.getPid(process) + ":" + e.getMessage());
			e.printStackTrace();
			// 异常处理的方法
		} finally {
			executorService.shutdownNow();
			if (process != null) {
				process.destroyForcibly();
			}
		}
		return registerCode;
	}
}
