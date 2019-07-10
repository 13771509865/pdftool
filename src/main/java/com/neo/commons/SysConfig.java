package com.neo.commons;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;

/**
 * 对应config.properties文件的配置类
 *
 * @authore sumnear
 * @create 2018-12-11 18:35
 */
@Component
@PropertySource(value={"classpath:config.properties"},ignoreResourceNotFound=true)
@Getter
public class SysConfig{

	@Value(value = "${inputDir}")
	private String inputDir;

	@Value(value = "${outputDir}")
	private String outputDir;

	@Value(value = "${convertPoolSize}")
	private Integer convertPoolSize;

	@Value(value = "${convertTimeout}")
	private String convertTimeout;

	@Value(value = "${uploadSize}")
	private Integer uploadSize;

	@Value(value = "${uploadTimes}")
	private Integer uploadTimes;

	@Value(value = "${convertTimes}")
	private Integer convertTimes;

	@Value(value = "${pdf2WordPath}")
	private String pdf2WordPath;

	@Value(value = "${pdf2WordExt}")
	private String pdf2WordExt;

	@Value(value = "${pdf2WordConfig}")
	private String pdf2WordConfig;

	@Value(value = "${clearDay}")
	private Integer clearDay;

	@Value(value= "${corePoolSize}")
	private Integer corePoolSize = 5;

	@Value(value= "${maxPoolSize}")
	private Integer maxPoolSize = 50;

	@Value(value= "${queueCapacity}")
	private Integer queueCapacity = 200;

	@Value(value= "${keepAliveSeconds}")
	private Integer keepAliveSeconds = 86400;

	@Value(value= "${waitForTasksToCompleteOnShutdown}")
	private Boolean waitForTasksToCompleteOnShutdown = true;

	@Value(value= "${awaitTerminationSeconds}")
	private Integer awaitTerminationSeconds = 60;

}
