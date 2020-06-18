package com.neo.commons.properties;

import com.alibaba.fastjson.JSON;
import com.neo.config.YamlPropertySourceFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;


/**
 * 注册用户每日转换个数配置类
 * @author xujun
 * @description
 * @create 2020年3月9日
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@PropertySource(value = "classpath:auth.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "convert-num")
public class ConvertNumProperty {
	
	@NotBlank
	private Integer convert001Num = -1;
	
	@NotBlank
	private Integer convert002Num = -1;
	
	@NotBlank
	private Integer convert003Num = 5;
	
	@NotBlank
	private Integer convert004Num = -1;
	
	@NotBlank
	private Integer convert005Num = 5;
	
	@NotBlank
	private Integer convert006Num = -1;
	
	@NotBlank
	private Integer convert007Num = 5;
	
	@NotBlank
	private Integer convert008Num = 5;
	
	@NotBlank
	private Integer convert009Num = 5;
	
	@NotBlank
	private Integer convert010Num = 5;
	
	@NotBlank
	private Integer convert011Num = 5;
	
	@NotBlank
	private Integer convert012Num = 5;
	
	@NotBlank
	private Integer convert013Num = 5;
	
	@NotBlank
	private Integer convert014Num = 5;
	
	@NotBlank
	private Integer convert015Num = -1;

	@NotBlank
	private Integer convert016Num = -1;

	@NotBlank
	private Integer convert017Num = -1;

	@NotBlank
	private Integer convert018Num = -1;

	@NotBlank
	private Integer convert019Num = -1;

	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
