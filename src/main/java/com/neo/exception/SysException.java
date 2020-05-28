package com.neo.exception;

import com.neo.commons.cons.EnumResultCode;

public class SysException extends CustomException {

	public SysException(Integer code, String message) {
		super(code, message);
	}

	public SysException(Integer httpCode,Integer code, String message, Object data){
		super(httpCode,code,message,data);
	}

	public SysException(EnumResultCode enumResultCode) {
		super(enumResultCode);
	}

	public SysException(Integer code, String message, Object data) {
		super(code, message, data);
	}
}
