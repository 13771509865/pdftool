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
	private Integer convert001Num;
	
	@NotBlank
	private Integer convert002Num;

	@NotBlank
	private Integer convert003Num;
	
	@NotBlank
	private Integer convert004Num;
	
	@NotBlank
	private Integer convert005Num;
	
	@NotBlank
	private Integer convert006Num;
	
	@NotBlank
	private Integer convert007Num;
	
	@NotBlank
	private Integer convert008Num;
	
	@NotBlank
	private Integer convert009Num;
	
	@NotBlank
	private Integer convert010Num;
	
	@NotBlank
	private Integer convert011Num;
	
	@NotBlank
	private Integer convert012Num;
	
	@NotBlank
	private Integer convert013Num;
	
	@NotBlank
	private Integer convert014Num;
	
	@NotBlank
	private Integer convert015Num;

	@NotBlank
	private Integer convert016Num;

	@NotBlank
	private Integer convert017Num;

	@NotBlank
	private Integer convert018Num;

	@NotBlank
	private Integer convert019Num;

	@NotBlank
	private Integer convert020Num;

	@NotBlank
	private Integer convert021Num;

	@NotBlank
	private Integer convert022Num;

	@NotBlank
	private Integer convert023Num;

	@NotBlank
	private Integer convert024Num;

	@NotBlank
	private Integer convert025Num;

	@NotBlank
	private Integer convert026Num;

	@NotBlank
	private Integer convert027Num;

	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
