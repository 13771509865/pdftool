package com.neo.commons.util;


import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.springframework.http.HttpStatus;

import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.ConstantCookie;
import com.neo.commons.cons.entity.HttpResultEntity;

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
                    filename = StrEncodingUtils.TranEncode2CN(resultName);
                } else if (param != null) {
                    try {
                        String temp = URLDecoder.decode(param.getValue(), "utf-8");
                        filename = StrEncodingUtils.TranEmilHeader(temp);
                        filename = StrEncodingUtils.TranEncode2CN(filename);
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
}
