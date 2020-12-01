package com.neo.filter;


import com.neo.interceptor.BodyReaderHttpServletRequestWrapper;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Order(12)
@WebFilter(filterName = "httpServletRequestWrapperFilter", urlPatterns = { "/composite/*" })
public class HttpServletRequestWrapperFilter implements Filter{

//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//
//	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		ServletRequest requestWrapper = null;
		if (request instanceof HttpServletRequest) {
			requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
		}
		
		if (null == requestWrapper) {
			chain.doFilter(request, response);
		} else {
			chain.doFilter(requestWrapper, response);
		}

	}

//	@Override
//	public void destroy() {
//
//	}


}
