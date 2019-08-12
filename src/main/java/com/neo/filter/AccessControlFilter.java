package com.neo.filter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@Order(10)//顺序
@WebFilter(filterName = "accessControlFilter", urlPatterns = "/*")
public class AccessControlFilter implements Filter {

	@Override
	public void init(FilterConfig config) throws ServletException {
		Filter.super.init(config);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.addHeader("Access-Control-Allow-Origin", "*");
		resp.addHeader("Access-Control-Allow-Credentials", "true");
		resp.addHeader("Access-Control-Allow-Methods", "*");
		resp.addHeader("Access-Control-Allow-Headers", "Content-Type, X-Requested-With");
		chain.doFilter(request, response);// 继续执行下一个Filter
	}

	@Override
	public void destroy() {
	}
}
