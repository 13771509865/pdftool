package com.neo.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 针对 ItemsException 全局异常处理
 */
public class GlobalItemsExceptionResolver implements HandlerExceptionResolver {

	public ModelAndView resolveException(HttpServletRequest request,
										 HttpServletResponse response,
										 Object handler, Exception ex) {

		String message;
		// 如果是自定义异常，给出具体的异常信息

		if (ex instanceof SysException) {
			message = ex.getMessage();
		} else {
			message = "服务器未知错误";
		}
        ex.printStackTrace();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", message);
		modelAndView.setViewName("error");
		return modelAndView;
	}
}
