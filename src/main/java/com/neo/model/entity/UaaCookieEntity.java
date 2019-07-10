package com.neo.model.entity;

import org.apache.commons.lang3.StringUtils;

public class UaaCookieEntity {
	
	private static final String access_token = "access_token=";
	private static final String refresh_token = "refresh_token=";
	
	public   String accessTokenValue;
	public   String refreshTokenValue;
	
	

	
	public String getAccessTokenValue() {
		return accessTokenValue;
	}




	public String getRefreshTokenValue() {
		return refreshTokenValue;
	}




	public  void getUaaCookie(String requestCookie) {
		if(StringUtils.isNotBlank(requestCookie)) {
			String[] str = requestCookie.split(";");
	    	for(String cookie : str) {
	    		if(cookie.contains(UaaCookieEntity.access_token)) {
	    			this.accessTokenValue = cookie.split("=")[1];
				}
				if(cookie.contains(UaaCookieEntity.refresh_token)) {
					this.refreshTokenValue = cookie.split("=")[1];
				}		
	    	}
		}
	}
	
	

}
