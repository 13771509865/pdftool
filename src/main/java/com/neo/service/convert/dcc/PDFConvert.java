package com.neo.service.convert.dcc;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.ResultCode;
import com.neo.commons.util.SysLog4JUtils;
import com.neo.config.ConvertConfig;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author yozo_dh
 *
 */
@Service("pDFConvert")
@Scope("prototype")
public class PDFConvert {
	@Autowired
	private ConvertConfig convertConfig;

	private static final String SPACE = " ";

	private String handlerProcessBlock(InputStream inputStream) throws IOException {
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		try {
			String buff;
			reader = new BufferedReader(new InputStreamReader(inputStream));
			while ((buff = reader.readLine()) != null) {
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

	private String createCmd(String config, String jarPath, String srcPath) {
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
		String cmd = sb.toString().replaceAll("\\\\", "\\\\\\\\");
		System.out.println(cmd);
		return cmd;
	}

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
//    8：无效参数
//    9：jar命令中参数错误
//    10: jar运行异常

	/**
	 * 文件到PDF文件的转换
	 *
	 * @param srcFilePath  源文件地址
	 * @param destFilePath 转换文件地址
	 * @param timeout      超时时间
	 */

	public int convertPdfToWord(String srcFilePath, String destFilePath, String timeout) throws Exception {
		int result = ResultCode.E_CONVERSION_FAIL.getValue();
		Map<String, Object> resultMap = new HashMap();
		resultMap.put("code", result);

		String cmd = createCmd(convertConfig.getPdf2WordConfig(), convertConfig.getP2wJarPath(), srcFilePath);

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
				result = process.exitValue();
				System.out.println("转码进程hashCode" + ProcessManager.getPid(process) + ":" + srcFilePath
						+ ",pdf convert :" + result);

			} else {
				// 超时
				throw new TimeoutException();
			}
		} catch (Exception e) {

			System.out.println(
					"转码进程hashcode" + ProcessManager.getPid(process) + ":" + srcFilePath + ":" + e.getMessage());
			e.printStackTrace();
			// 异常处理的方法

			if (e instanceof TimeoutException) {
				System.err.println("pdf convert  timeout: " + timeout + " seconds");
				resultMap.put("code", ResultCode.E_CONVERSION_TIMEOUT.getValue());
			} else {
				System.err.println("pdf convert  failed: " + e.getMessage());
				resultMap.put("code", ResultCode.E_CONVERSION_FAIL.getValue());
			}

		} finally {
			executorService.shutdownNow();
			if (process != null) {
				process.destroyForcibly();
			}
		}
		return result;
	}
}
