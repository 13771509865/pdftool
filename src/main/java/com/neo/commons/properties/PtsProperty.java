package com.neo.commons.properties;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 自定义配置项
 * @author xujun
 * @date 2019-07-19
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "pts")
public class PtsProperty {
	
	@NotBlank
	private String uaa_logout_url;
	  
	@NotBlank
	private	String uaa_userinfo_url;

	@NotBlank
	private	String	fcs_upload_url;
	
	@NotBlank
	private	String	fcs_convert_url;

	@NotBlank
	private String fcs_vToken_url;
	
	@NotBlank
	private String fcs_downLoad_url;
	
	
	
}
