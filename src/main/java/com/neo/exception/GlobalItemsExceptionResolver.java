package com.neo.exception;

import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.util.JsonResultUtils;
import com.neo.commons.util.SysLogUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 针对 ItemsException 全局异常处理
 */
@ControllerAdvice
public class GlobalItemsExceptionResolver{



	@ExceptionHandler(SysException.class)
	@ResponseBody
	public ResponseEntity SysExceptionHandler(SysException e) {
		Integer httpCode = e.getHttpCode() == null ? 200 : e.getHttpCode();
		return new ResponseEntity<>(JsonResultUtils.buildMapResult(e.getCode(), e.getData(), e.getMessage()), HttpStatus.valueOf(httpCode));
	}

	/**
	 * 全局捕获异常，返回服务器正忙
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity defaultExcepitonHandler(Exception ex) {
		if (ex instanceof HttpRequestMethodNotSupportedException) {
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), HttpStatus.METHOD_NOT_ALLOWED);
		} else if (ex instanceof HttpMediaTypeNotSupportedException) {
			return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
		} else if (ex instanceof HttpMessageNotReadableException || ex instanceof MissingServletRequestParameterException) {
			return new ResponseEntity<>(EnumResultCode.E_SERVER_BUSY.getInfo(), HttpStatus.BAD_REQUEST);
		}
		SysLogUtils.error("全局异常处理器捕获异常,请检查", ex);
		return ResponseEntity.ok(JsonResultUtils.buildFailJsonResultByResultCode(EnumResultCode.E_SERVER_BUSY));
	}

}
