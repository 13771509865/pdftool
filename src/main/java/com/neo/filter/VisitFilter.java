package com.neo.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.util.JsonResultUtils;


/**
 * 暂时用这个做防盗链，但是有漏洞
 * @author xujun
 *
 */
@Order(11)//顺序,越小越优先
@WebFilter(filterName = "visitFilter", urlPatterns = "/composite/*")
public class VisitFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Filter.super.init(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String address=req.getHeader("referer"); //获取发起请求的页面地址
		String pathAdd = null; //定义空字符串
		if(address!=null){ //判断当前的页面的请求地址为空时
			URL urlOne=new URL(address);//实例化URL方法
			pathAdd=urlOne.getHost(); //获取请求页面的服务器主机
		}
		String address1=req.getRequestURL().toString(); //获取当前页面的地址
		String pathAdd1 = null;
		if(address1!=null){
			URL urlTwo=new URL(address1);
			pathAdd1=urlTwo.getHost(); //获取当前服务器的主机
		}

		if(!pathAdd1.equals(pathAdd)) {
			res.setContentType("text/html;charset=UTF-8");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(JsonResultUtils.buildFailJsonResultByResultCode(EnumResultCode.E_FILESERVICE_FAIL));
			out.flush();
			out.close();
			return ;
		}
		chain.doFilter(request, response);

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		Filter.super.destroy();
	}



}
