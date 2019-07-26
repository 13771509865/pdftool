package com.neo.commons.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;

/**
 * 对应config.properties文件的配置类
 *
 * @authore xujun
 * @create 2019-07-19
 */
@Component
@PropertySource(value={"classpath:config.properties"},ignoreResourceNotFound=true)
@Getter
public class ConfigProperty{


	@Value(value = "${convertPoolSize}")
	private Integer convertPoolSize;

	@Value(value = "${convertTimeout}")
	private Long convertTimeout;

	@Value(value = "${mUploadSize}")
	private Integer mUploadSize;

	@Value(value = "${vUploadSize}")
	private Integer vUploadSize;

	@Value(value = "${mConvertTimes}")
	private Integer mConvertTimes;

	@Value(value = "${vConvertTimes}")
	private Integer vConvertTimes;
	
	
	@Value(value = "${clearDay}")
	private Integer clearDay;
	
	@Value(value = "${clearMaster}")
	private Boolean clearMaster;
	
	@Value(value = "${clearInputDir}")
	private Boolean clearInputDir;
	
	@Value(value = "${clearOutputDir}")
	private Boolean clearOutputDir;
	
	@Value(value = "${folderFormat}")
    private String folderFormat = "yyyy/MM";
	
	@Value(value = "${convertTicketWaitTime}")
	private Integer convertTicketWaitTime;
	
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
