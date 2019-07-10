package com.neo.commons.cons.entity;



public class HttpResultEntity {
    // 响应码
    private Integer code;

    // 响应体
    private String body;
    
    
    

	public HttpResultEntity() {
		super();
	}

	public HttpResultEntity(Integer code, String body) {
		super();
		this.code = code;
		this.body = body;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "HttpResultEntity [code=" + code + ", body=" + body + "]";
	}
    
    
    
    
}
