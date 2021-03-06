package com.neo.commons.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;


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
	
	@NotBlank
	private String fcs_http_download_url;

	@NotBlank
	private String features_version;

	@NotBlank
	private String product_hmac_key;

	@NotBlank
	private String features_insert_url;
	
	@NotBlank
	private String domain;

	@NotBlank
	private String membership_url;

	@NotBlank
	private String member_hamc_key;
	
	@NotBlank
	private String feedback_url;

	@NotBlank
	private String fcs_srcfile_dir;

	@NotBlank
	private String fcs_targetfile_dir;

	@NotBlank
	private String convert_fail_dir;

	@NotBlank
	private String report_error_dir;

	@NotBlank
	private String yzcloud_domain;

	@NotBlank
	private String retryFlag;

	@NotBlank
	private String message_url;

}
