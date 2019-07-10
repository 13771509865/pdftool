package com.neo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yozosoft.auth.client.config.YozoCloudProperties;
import com.yozosoft.auth.client.security.JwtAuthenticator;
import com.yozosoft.auth.client.security.OAuth2CookieHelper;
import com.yozosoft.auth.client.security.OAuth2RequestTokenHelper;
import com.yozosoft.auth.client.security.UaaSignatureVerifierClient;
import com.yozosoft.auth.client.security.refresh.UaaTokenEndpointClient;
import com.yozosoft.auth.client.security.refresh.UaaTokenRefreshClient;

@Configuration
public class UaaConfig {
	
	 @Bean(name = "yozoCloudProperties")
	    public YozoCloudProperties initYozoCloudProperties(){
	        YozoCloudProperties properties = new YozoCloudProperties();
	        return properties;
	    }

	    @Bean(name = "jwtAuthenticator")
	    public JwtAuthenticator buildJwtAuthenticator(@Qualifier("yozoCloudProperties") YozoCloudProperties yozoCloudProperties){
	        JwtAuthenticator jwtAuthenticator = new JwtAuthenticator(new UaaSignatureVerifierClient(yozoCloudProperties));
	        return jwtAuthenticator;
	    }

	    @Bean(name = "uaaTokenRefreshClient")
	    public UaaTokenRefreshClient buildUaaTokenRefreshClient(@Qualifier("yozoCloudProperties") YozoCloudProperties yozoCloudProperties){
	        UaaTokenRefreshClient uaaTokenRefreshClient = new UaaTokenRefreshClient(yozoCloudProperties, new UaaTokenEndpointClient(yozoCloudProperties));
	        return uaaTokenRefreshClient;
	    }

	    @Bean(name = "oAuth2CookieHelper")
	    public OAuth2CookieHelper buildOAuth2CookieHelper(@Qualifier("yozoCloudProperties") YozoCloudProperties yozoCloudProperties){
	        OAuth2CookieHelper oAuth2CookieHelper = new OAuth2CookieHelper(yozoCloudProperties);
	        return oAuth2CookieHelper;
	    }

	    @Bean(name = "oAuth2RequestTokenHelper")
	    public OAuth2RequestTokenHelper buildOAuth2RequestTokenHelper(@Qualifier("yozoCloudProperties") YozoCloudProperties yozoCloudProperties, @Qualifier("oAuth2CookieHelper") OAuth2CookieHelper oAuth2CookieHelper){
	        OAuth2RequestTokenHelper oAuth2RequestTokenHelper = new OAuth2RequestTokenHelper(yozoCloudProperties, oAuth2CookieHelper);
	        return oAuth2RequestTokenHelper;
	    }

}
