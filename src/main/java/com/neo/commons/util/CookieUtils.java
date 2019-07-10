package com.neo.commons.util;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neo.commons.cons.ConstantCookie;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class CookieUtils {

    /**
     * @param request 请求
     * @param name 名字
     * @return
     * @description 根据cookie的名称获取cookie
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie cookies[] = request.getCookies();
        if (cookies == null || name == null || name.length() == 0) {
            return null;
        }
        for (int i = 0; i < cookies.length; i++) {
            if (name.equals(cookies[i].getName())) {
                // && request.getServerName().equals(cookies[i].getDomain())) {
                return cookies[i];
            }
        }
        return null;
    }

    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie ck = getCookie(request, name);
        if (ck != null) {
            try {
                return URLDecoder.decode(ck.getValue(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 删除cookie
     *
     * @param request
     * @param response
     * @param cookie
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, Cookie cookie) {
        if (cookie != null) {
            cookie.setPath(getPath(request));
            cookie.setValue("");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    /**
     * 删除cookie
     *
     * @param request
     * @param response
     * @param cookie
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        Cookie cookie = getCookie(request, cookieName);
        if (cookie != null) {
            cookie.setPath(getPath(request));
            cookie.setValue("");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    /**
     * 设置cookie
     *
     * @param request
     * @param response
     * @param name
     * @param value    如果不设置时间，默认永久
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) throws UnsupportedEncodingException {
        setCookie(request, response, name, value, null);
    }

    /**
     * @param request
     * @param response
     * @param name
     * @param value
     * @param maxAge   设置cookie，设定时间
     * @throws UnsupportedEncodingException
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
                                 Integer maxAge) throws UnsupportedEncodingException {
        String cookieValue = value;
        Cookie cookie = new Cookie(name, value == null ? "" : URLEncoder.encode(cookieValue.replaceAll("\r\n", ""), "UTF-8"));
        cookie.setMaxAge(maxAge == null ? ConstantCookie.COOKIE_TIME_MAX : maxAge);
        cookie.setPath(getPath(request));
        response.addCookie(cookie);
    }
    
    
    /**
     * 删除cookie
     *
     * @param request
     * @param response
     * @param cookieName
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String domain) {
        Cookie cookie = getCookie(request, cookieName);
        if (cookie != null) {
            cookie.setPath(getPath(request));
            cookie.setValue("");
            cookie.setMaxAge(0);
            //删除该域名下的cookie
            cookie.setDomain(domain);
            response.addCookie(cookie);
        }
    }
    

    private static String getPath(HttpServletRequest request) {
        String path = request.getContextPath();
        return (path == null || path.length() == 0) ? "/" : path;
    }

}
