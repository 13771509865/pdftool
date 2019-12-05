package com.neo.commons.util;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.ConstantCookie;
import com.neo.commons.cons.entity.HttpResultEntity;
import com.neo.model.bo.UserBO;

/**
 * @author zhoufeng
 * @description http工具类
 * @create 2019-03-22 17:06
 **/
public class HttpUtils {
    public static final String SUFFIXES = "docx|doc|pptx|ppt|xlsx|xls|rtf|eio|uof|uos|xml|pdf|ofd|jpeg|gif|jpg|png|txt|zip|rar|tif|properties|html|"
            + "DOCX|DOC|PPTX|PPT|XLSX|XLS|RTF|EIO|UOF|UOS|XML|PDF|OFD|JPEG|GIF|JPG|PNG|TXT|ZIP|RAR|TIF|PROPERTIES|HTML";

    /**
     * @param request 用户请求request
     * @return 协议+ip+端口号+项目名
     * @description 获取当前请求的baseUrl
     * @author zhoufeng
     * @date 2019/3/22
     */
    public static String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();//协议
        String serverName = request.getServerName();//ip
        int serverPort = request.getServerPort();//端口
        String contextPath = request.getContextPath();//项目名
        if (StringUtils.isEmpty(contextPath)) {
            return scheme + "://" + serverName + ":" + serverPort + "/" + contextPath;
        } else {
            return scheme + "://" + serverName + ":" + serverPort + contextPath + "/";
        }
    }

    public static String getScheme(HttpServletRequest request) {
        return request.getScheme();
    }

    /**
     * @param request 请求
     * @return 项目名
     * @description 获取项目名
     * @author zhoufeng
     * @date 2019/4/10
     */
    public static String getContextPath(HttpServletRequest request) {
        return request.getContextPath();
    }

 
    /**
     * @param code 返回状态码
     * @return 成功-true
     * @description 判断请求是否成功
     * @author zhoufeng
     * @date 2019/5/5
     */
    public static Boolean isHttpSuccess(Integer code) {
        if (code != null && code >= HttpStatus.OK.value() && code < HttpStatus.MULTIPLE_CHOICES.value()) {
            return true;
        }
        return false;
    }

    public static Boolean isHttpSuccess(IResult<HttpResultEntity> httpResultEntityIResult) {
        if(httpResultEntityIResult.isSuccess() && isHttpSuccess(httpResultEntityIResult.getData().getCode())){
            return true;
        }
        return false;
    }

    /**
     * @param request 用户请求request
     * @return 用户ip
     * @description 获取request真实ip地址
     * @author zhoufeng
     * @date 2019/3/22
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Real-IP");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("x-forwarded-for");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
            // = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    public static String getFileNameByDownload(HttpResponse response, String url) {
        String fileName = getFileName(response, url);
        if (StringUtils.isEmpty(fileName)) {
            fileName = System.currentTimeMillis() + "";
        }
        return fileName;
    }

    public static String getFileName(HttpResponse response, String url) {
        // 先从content-disposition取，取不到在从URL中获取，取不到就从ETAG中取。。
        String filename = "";
        Header contentHeader = response.getFirstHeader("Content-Disposition");
        if (contentHeader != null) {
            HeaderElement[] values = contentHeader.getElements();
            if (values.length >= 1) {
                String resultName = values[0].getValue();
                NameValuePair param = values[0].getParameterByName("filename");
                if (resultName != null && !"".equals(resultName)) {
                    filename = StrUtils.TranEncode2CN(resultName);
                } else if (param != null) {
                    try {
                        String temp = URLDecoder.decode(param.getValue(), "utf-8");
                        filename = StrUtils.TranEmilHeader(temp);
                        filename = StrUtils.TranEncode2CN(filename);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if ("".equals(filename)) {
//			Pattern pat = Pattern.compile("[/]{1}[^/]+[\\.](" + SUFFIXES + ")$");// 正则判断
            Pattern pat = Pattern.compile("[/]{1}[^/]+(\\.)(" + SUFFIXES + ")");// 正则判断
            try {
                Matcher mc = pat.matcher(new URLDecoder().decode(url, "UTF-8"));// 条件匹配
                while (mc.find()) {
                    filename = URLDecoder.decode(mc.group().substring(1), "utf-8");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        // TODO这个方法有点问题
        if ("".equals(filename)) {
            String name = response.getFirstHeader("ETag") + "";
            String type = response.getFirstHeader("Content-Type").toString();
            if (!"".equals(name) && !"null".equals(name) && name != null && type != null && !"null".equals(type)) {
                try {
                    String sname = name.substring(name.indexOf("\"") + 1, name.lastIndexOf("\""));
                    String stype = type.split("/")[1];
                    filename = sname + "." + stype;
                } catch (Exception e) {
                    filename = name.replaceAll(" ", "");
                }
            }
        }
        Pattern FilePattern = Pattern.compile("[\\\\/:*?\"<>|]");
        filename = FilePattern.matcher(filename).replaceAll("");
        if (filename.length() > 200) {
            int endindex = filename.lastIndexOf(".");
            if (endindex > 0) {
                String type = filename.substring(endindex);
                int length = 200 - type.length();
                filename = filename.substring(0, length) + type;
            } else {
                filename.substring(0, 200);
            }
        }
        return filename;
    }
    
    
    
	
    /**
     * 获取session中的userID
     * @param request
     * @return
     */
	public static Long getSessionUserID(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userInfo = (String)session.getAttribute(ConstantCookie.SESSION_USER);
		if(StringUtils.isNotBlank(userInfo)) {
			UserBO userBO = JsonUtils.json2obj(userInfo, UserBO.class);
			return userBO.getUserId();
		}
		return null;
	}
	
	
	public static UserBO getUserBO(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userInfo = (String)session.getAttribute(ConstantCookie.SESSION_USER);
		if(StringUtils.isNotBlank(userInfo)) {
			UserBO userBO = JsonUtils.json2obj(userInfo, UserBO.class);
			return userBO;
		}
		return null;
	}

	
	
    
	/**
	 * 根据不同的文件后缀名，塞入不同的ContentType
	 * @param httpResponse
	 * @param fileName
	 */
	public static boolean setContentType(HttpServletResponse httpResponse ,String fileName) {
		if(!StringUtils.isBlank(fileName)) {
			String[] split = fileName.split("\\.");
			int length = split.length;
			if(length > 0){
				String fileType = split[length-1];
				if(fileType != null && !"".equals(fileType)){
					if(fileType.equals("doc") ){
						httpResponse.setContentType("application/msword");
					}else if(fileType.equals("docx")){
						httpResponse.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
					}else if(fileType.equals("xls")){
						httpResponse.setContentType("application/vnd.ms-excel");
					}else if(fileType.equals("xlsx")){
						httpResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
					}else if(fileType.equals("ppt")){
						httpResponse.setContentType("application/vnd.ms-powerpoint");
					}else if(fileType.equals("pptx")){
						httpResponse.setContentType("application/vnd.openxmlformats-officedocument.presentationml.presentation");
					}else if(fileType.equals("pdf")){
						httpResponse.setContentType("application/pdf");
					}else{
						httpResponse.setContentType("application/octet-stream");//octet-stream
					}
				}else{
					httpResponse.setContentType("application/octet-stream");//octet-stream
				}
			}else{
				httpResponse.setContentType("application/octet-stream");//octet-stream
			}
			return true;
		}
		return false;
	}


	/**
	 * 根据不同的浏览器类型，返回不同编码的文件名
	 * @param request
	 * @param fileNames
	 * @return
	 */
	public static String processFileName(HttpServletRequest request, String fileNames) {
		String codedfilename = null;
		try {
			String agent = request.getHeader("USER-AGENT");
			if (null != agent && -1 != agent.indexOf("MSIE") || null != agent
					&& -1 != agent.indexOf("Trident")) {// ie
				String name = URLEncoder.encode(fileNames, "UTF8");
				codedfilename = name;
			} else if (null != agent && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等
				codedfilename = new String(fileNames.getBytes("UTF-8"), "iso-8859-1");
			}
			return codedfilename;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}



	
    
}
