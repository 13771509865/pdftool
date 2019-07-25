package com.neo.web.file;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.entity.FileHeaderEntity;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.util.GetFileMd5Utils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.commons.util.MD5Utils;
import com.neo.commons.util.MyFileUtils;
import com.neo.service.httpclient.HttpAPIService;



/**
 * @author xujun 文件下载接口 session中有值才允许下载
 */
@Controller
@RequestMapping(value = "/file")
public class DownloadController {

	@Autowired
	private ConfigProperty config;
	
	@Autowired
	private HttpAPIService httpAPIService;

//	@RequestMapping(value = "/download")
//	public void downloadFile(@RequestParam(value = "fileHash", required = true) String fileHash,
//			HttpServletRequest request, HttpServletResponse response) throws Exception {
//		HttpSession httpSession = request.getSession();
//		Object obj = httpSession.getAttribute(fileHash);
//		FileInfoBO fileInfoBO = (FileInfoBO) obj;
//		String filePath = fileInfoBO.getWordStoragePath();
//		String fileName = fileInfoBO.getFileName();
//		long pos = 0, end = 0, size = 0, overwrite = 0; // 开始位置、结束位置、大小、已写大小、每次的长度
//		int len = 0;
//		String root = config.getOutputDir();
//		String desFilePath = root + File.separator + filePath;
//		File file = new File(desFilePath);
//		if (file.exists()) {
//			// 以流的形式下载文件。
//			try (InputStream fis = new BufferedInputStream(new FileInputStream(file));
//					OutputStream os = new BufferedOutputStream(response.getOutputStream())) {
//				long fSize = file.length();
//				byte buffer[] = new byte[4096];
//				// nginx 测试失败
//				response.setHeader("Accept-Ranges", "bytes");
//				response.setHeader("Content-Length", fSize + "");
//				response.setHeader("Connection", "keep-alive");
//				response.setHeader("Content-Disposition",
//						"attachment;filename=" + URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20"));
//				if (request.getHeader("Range") != null) {
//					// 若客户端传来Range，说明之前下载了一部分，设置206状态(SC_PARTIAL_CONTENT)
//					response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
//					String num[] = request.getHeader("Range").replaceAll("bytes=", "").split("-");
//
//					pos = Integer.parseInt(num[0]);
//					if (num[1] == null || "".equals(num[1])) {
//						end = fSize;
//					} else {
//						end = Integer.parseInt(num[1]);
//					}
//					if (end > pos && fSize >= end) {
//						// 确保有end值 且 end 小于 文件大小
//						size = end - pos;
//						String contentRange = new StringBuffer("bytes ").append(pos).append("-").append(end).append("/")
//								.append(fSize).toString();
//						response.setHeader("Content-Range", contentRange);
//						response.setHeader("Content-Length", size + 1 + "");
//						// System.out.println("Content-Range=" +
//						// contentRange);
//						// 略过已经传输过的字节
//						fis.skip(pos);
//					}
//				}
//				boolean ok = false;
//				while (!ok) {
//					// 输出缓冲区的内容到浏览器，实现文件下载
//					len = fis.read(buffer, 0, buffer.length);
//					if (len > -1) {
//						overwrite += len;
//						if (size > 0 && overwrite >= (size + 1)) {
//							len = (int) (len - (overwrite - size - 1));
//							ok = true;
//						}
//						os.write(buffer, 0, len);
//					} else {
//						ok = true;
//					}
//				}
//				os.flush();
//			}
//		}
//	}
	
	
	
	  @RequestMapping(value = "/download")
	  @ResponseBody
	  public Map<String, Object> getFileByHttp(String fileUrl, HttpServletRequest request) {
	        IResult<String> storageResult = storageFileByHttp(fileUrl, request);
	        if (storageResult.isSuccess()) {
	            return JsonResultUtils.successMapResult(storageResult.getData());
	        } else {
	            return JsonResultUtils.buildMapResult(Integer.parseInt(storageResult.getData()), null, storageResult.getMessage());
	        }
	    }
	
	
	 public IResult<String> storageFileByHttp(String fileUrl, HttpServletRequest request) {
	        String filePath = null;
	        String fileName = null;
	        Map<String, Object> headers = getFcsCustomHeaders(request);
	        IResult<FileHeaderEntity> fileHeaderBOByHead = httpAPIService.getFileHeaderBOByHead(fileUrl, headers);
	        if (fileHeaderBOByHead.isSuccess()) {     //判断是否需要重新下载
	        	FileHeaderEntity fileHeaderBO = fileHeaderBOByHead.getData();
	            String fileMD5 = getFileMD5(fileHeaderBO);
	            if (StringUtils.isNotEmpty(fileMD5)) {
	                String relativePath = fileMD5 + File.separator + fileHeaderBO.getFileName();
	                boolean exists = MyFileUtils.isExists(config.getInputDir(), relativePath);
	                if (exists) {
	                    return DefaultResult.successResult(relativePath);
	                }
	                filePath = config.getInputDir()+fileMD5;
	                fileName = fileHeaderBO.getFileName();
	            }
	        }
	        IResult<String> downloadResult = httpAPIService.download(fileUrl, null, headers, filePath, fileName);
	        return downloadResult;
	    }
	
	 
	 public  Map<String, Object> getFcsCustomHeaders(HttpServletRequest request){
	        Map<String, Object> headers = new HashMap<>();
	        Enumeration<String> headerNames = request.getHeaderNames();
	        while (headerNames.hasMoreElements()) {
	            String headerName = headerNames.nextElement();
	            if (headerName.startsWith("fcs")) { //如果是fcs开头就是自定义头信息
	                String realHeaderName = headerName.substring("fcs".length());
	                headers.put(realHeaderName, request.getHeader(headerName));
	            }
	        }
	        return headers;
	    }
	
	 
	 
	  public  String getFileMD5(FileHeaderEntity fileHeaderBO) {
	        String fileName = fileHeaderBO.getFileName();
	        Long contentLength = fileHeaderBO.getContentLength();
	        String lastModified = fileHeaderBO.getLastModified();
	        String url = fileHeaderBO.getUrl();
	        if (contentLength!=null && StringUtils.isNotEmpty(lastModified) && StringUtils.isNotEmpty(fileName) && StringUtils.isNotEmpty(url)) {
	            String fileHashStr = fileHeaderBO.toString();
	            String md5 = MD5Utils.getMD5(fileHashStr);
	            if (StringUtils.isNotEmpty(md5)) {
	                return "f"+md5;
	            }
	        }
	        return null;
	    }
	

	  
	  
}
