package com.neo.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "accessControllerFilter", urlPatterns = "/*")
public class AccessControllerFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpServletRequest  req = (HttpServletRequest )request;
		String referer = this.getReferer(req);
		resp.setHeader("Access-Control-Allow-Origin", referer == null ? "*" : referer);
		resp.setHeader("Access-Control-Allow-Credentials", "true");
		resp.setHeader("Access-Control-Allow-Methods", "GET,OPTIONS,PUT,DELETE,POST");
		//resp.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Origin, Content-Type, Accept");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type, X-Requested-With");
		if (req.getMethod().equals(RequestMethod.OPTIONS.name())) {
			resp.setStatus(HttpStatus.OK.value());
		    return;
		}
		chain.doFilter(request, response);// 继续执行下一个Filter
		
	}

//	@Override
//	public void destroy() {
//	}
//
	
	  public String getReferer(HttpServletRequest request) {
	        String referer = request.getHeader("Referer");
	        String origin = request.getHeader("Origin");
	        
	        if (StringUtils.isBlank(referer) && StringUtils.isBlank(origin)) {
	            return null;
	        }
	        
	        //解决国内浏览器无法获取referer
	        referer = StringUtils.isBlank(referer)?origin:referer;
	        
	        int idx = referer.indexOf("://");
	        if (idx > 0) {
	            int end = referer.indexOf("/", idx + 3);
	            if (end <= 0) {
	                return referer;
	            } else {
	                return referer.substring(0, end);
	            }
	        }
	        return null;
	    }


}
