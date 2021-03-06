package com.neo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.neo.commons.properties.ConfigProperty;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;


@Configuration
@EnableAsync(proxyTargetClass=true)
public class TaskPoolConfig {
	
	
    @Autowired
    private ConfigProperty sysConfig;

    @Bean("asynConvertExecutor")
    public Executor asynConvertExecutor(){
        return buildExecutor(sysConfig.getCorePoolSize(),sysConfig.getMaxPoolSize(),sysConfig.getQueueCapacity(),sysConfig.getKeepAliveSeconds(),"asynConvertExecutor-",sysConfig.getWaitForTasksToCompleteOnShutdown(),sysConfig.getAwaitTerminationSeconds(),new ThreadPoolExecutor.AbortPolicy());
    }

    @Bean("addMemberEventExecutor")
    public Executor addMemberEventExecutor(){
        return buildExecutor(sysConfig.getCorePoolSize(),sysConfig.getMaxPoolSize(),sysConfig.getQueueCapacity(),sysConfig.getKeepAliveSeconds(),"addMemberEventExecutor-",sysConfig.getWaitForTasksToCompleteOnShutdown(),sysConfig.getAwaitTerminationSeconds(),new ThreadPoolExecutor.AbortPolicy());
    }
    
    @Bean("updatePtsSummayExecutor")
    public Executor updatePtsSummayExecutor(){
        return buildExecutor(sysConfig.getCorePoolSize(),sysConfig.getMaxPoolSize(),sysConfig.getQueueCapacity(),sysConfig.getKeepAliveSeconds(),"updatePtsSummayExecutor-",sysConfig.getWaitForTasksToCompleteOnShutdown(),sysConfig.getAwaitTerminationSeconds(),new ThreadPoolExecutor.AbortPolicy());
    }


    @Bean("uploadYcFileExecutor")
    public Executor uploadYzFileExecutor(){
        return buildExecutor(sysConfig.getCorePoolSize(), sysConfig.getMaxPoolSize(), Integer.MAX_VALUE, sysConfig.getKeepAliveSeconds(), "uploadYcFileExecutor-",sysConfig.getWaitForTasksToCompleteOnShutdown(),sysConfig.getAwaitTerminationSeconds(),new ThreadPoolExecutor.AbortPolicy());
    }

    private ThreadPoolTaskExecutor buildExecutor(Integer corePoolSize, Integer maxPoolSize, Integer queueCapacity, Integer keepAliveSeconds, String threadNamePrefix, Boolean waitForTasksToCompleteOnShutdown, Integer awaitTerminationSeconds, RejectedExecutionHandler rejectedExecutionHandler){
    	 ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
         //线程池创建时候初始化的线程数
         executor.setCorePoolSize(corePoolSize);
         //线程池最大的线程数
         executor.setMaxPoolSize(maxPoolSize);
         //缓冲队列
         executor.setQueueCapacity(queueCapacity);
         //允许线程等待处理的时间,超过24小时还没有被执行,直接销毁
         executor.setKeepAliveSeconds(keepAliveSeconds);
         //线程池名的前缀
         executor.setThreadNamePrefix(threadNamePrefix);
         //关闭的时候等待所有任务执行完成再销毁
         executor.setWaitForTasksToCompleteOnShutdown(waitForTasksToCompleteOnShutdown);
         //强制销毁时间,超过设置值60s强制销毁
         executor.setAwaitTerminationSeconds(awaitTerminationSeconds);
         //线程池对拒绝任务的处理策略
         executor.setRejectedExecutionHandler(rejectedExecutionHandler);
         executor.initialize();
         return executor;
    }
}
