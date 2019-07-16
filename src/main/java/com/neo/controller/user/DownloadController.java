package com.neo.controller.user;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.neo.config.SysConfig;
import com.neo.model.bo.FileInfoBO;

/**
 * @author zhouf 文件下载接口 session中有值才允许下载
 */
@Controller
@RequestMapping(value = "/user")
public class DownloadController {

	@Autowired
    private SysConfig config;
	
	@PostMapping(value = "/download")
	public void downloadFile(@RequestParam(value = "fileHash", required = true) String fileHash,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession httpSession = request.getSession();
		Object obj = httpSession.getAttribute(fileHash);
		FileInfoBO fileInfoBO = (FileInfoBO) obj;
		String filePath = fileInfoBO.getWordStoragePath();
		String fileName = fileInfoBO.getFileName();
		long pos = 0, end = 0, size = 0, overwrite = 0; // 开始位置、结束位置、大小、已写大小、每次的长度
		int len = 0;
		String root = config.getOutputDir();
		String desFilePath = root + File.separator + filePath;
		File file = new File(desFilePath);
		if (file.exists()) {
			// 以流的形式下载文件。
			try (InputStream fis = new BufferedInputStream(new FileInputStream(file));
					OutputStream os = new BufferedOutputStream(response.getOutputStream())) {
				long fSize = file.length();
				byte buffer[] = new byte[4096];
				// nginx 测试失败
				response.setHeader("Accept-Ranges", "bytes");
				response.setHeader("Content-Length", fSize + "");
				response.setHeader("Connection", "keep-alive");
				response.setHeader("Content-Disposition",
						"attachment;filename=" + URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20"));
				if (request.getHeader("Range") != null) {
					// 若客户端传来Range，说明之前下载了一部分，设置206状态(SC_PARTIAL_CONTENT)
					response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
					String num[] = request.getHeader("Range").replaceAll("bytes=", "").split("-");

					pos = Integer.parseInt(num[0]);
					if (num[1] == null || "".equals(num[1])) {
						end = fSize;
					} else {
						end = Integer.parseInt(num[1]);
					}
					if (end > pos && fSize >= end) {
						// 确保有end值 且 end 小于 文件大小
						size = end - pos;
						String contentRange = new StringBuffer("bytes ").append(pos).append("-").append(end).append("/")
								.append(fSize).toString();
						response.setHeader("Content-Range", contentRange);
						response.setHeader("Content-Length", size + 1 + "");
						// System.out.println("Content-Range=" +
						// contentRange);
						// 略过已经传输过的字节
						fis.skip(pos);
					}
				}
				boolean ok = false;
				while (!ok) {
					// 输出缓冲区的内容到浏览器，实现文件下载
					len = fis.read(buffer, 0, buffer.length);
					if (len > -1) {
						overwrite += len;
						if (size > 0 && overwrite >= (size + 1)) {
							len = (int) (len - (overwrite - size - 1));
							ok = true;
						}
						os.write(buffer, 0, len);
					} else {
						ok = true;
					}
				}
				os.flush();
			}
		}
	}
}
