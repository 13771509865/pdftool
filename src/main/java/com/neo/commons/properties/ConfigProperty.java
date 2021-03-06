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


	@Value(value = "${ticketMaster}")
	private Boolean ticketMaster;
	
	@Value(value = "${convertPoolSize}")
	private Integer convertPoolSize;

	@Value(value = "${convertTimeout}")
	private Long convertTimeout;
	
	@Value(value = "${ticketLimiter}")
	private Integer ticketLimiter;
	
	@Value(value = "${ticketWaitTime}")
	private Integer ticketWaitTime;
	
	@Value(value = "${convertModule}")
	private String convertModule;
	
	@Value(value = "${reConvertModule}")
	private String reConvertModule;
	
	@Value(value = "${clearDay}")
	private Integer clearDay;
	
	@Value(value = "${clearMaster}")
	private Boolean clearMaster;
	
	@Value(value = "${convertTicketWaitTime}")
	private Integer convertTicketWaitTime;

	@Value(value = "${convertLimiter}")
	private Integer convertLimiter;

	@Value(value = "${UnlimitedUsers}")
	private String UnlimitedUsers;

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
