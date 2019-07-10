package com.neo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.neo.commons.SysConfig;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;


@Configuration
@EnableAsync(proxyTargetClass=true)
public class TaskPoolConfig {
	
	
    @Autowired
    private SysConfig sysConfig;

    @Bean("asynConvertExecutor")
    public Executor asynConvertExecutor(){
        return buildExecutor(sysConfig.getCorePoolSize(),sysConfig.getMaxPoolSize(),sysConfig.getQueueCapacity(),sysConfig.getKeepAliveSeconds(),"asynConvertExecutor-",sysConfig.getWaitForTasksToCompleteOnShutdown(),sysConfig.getAwaitTerminationSeconds(),new ThreadPoolExecutor.AbortPolicy());
    }

    /**
    * @description http请求发送线程池 等待队列长度为Interger.Max
    * @author zhoufeng
    * @date 2019/2/14
    */
    @Bean("callbackHttpclientExecutor")
    public Executor callbackHttpclientExecutor(){
        return buildExecutor(sysConfig.getCorePoolSize(),sysConfig.getMaxPoolSize(),Integer.MAX_VALUE,sysConfig.getKeepAliveSeconds(),"callbackHttpclientExecutor-",sysConfig.getWaitForTasksToCompleteOnShutdown(),sysConfig.getAwaitTerminationSeconds(),new ThreadPoolExecutor.AbortPolicy());
//        return buildExecutor(sysConfig.getCorePoolSize(),sysConfig.getMaxPoolSize(),sysConfig.getQueueCapacity(),sysConfig.getKeepAliveSeconds(),"httpclientExecutor-",sysConfig.getWaitForTasksToCompleteOnShutdown(),sysConfig.getAwaitTerminationSeconds(),new ThreadPoolExecutor.AbortPolicy());
    }

    private ThreadPoolTaskExecutor buildExecutor(Integer corePoolSize, Integer maxPoolSize, Integer queueCapacity, Integer keepAliveSeconds, String threadNamePrefix, Boolean waitForTasksToCompleteOnShutdown, Integer awaitTerminationSeconds, RejectedExecutionHandler rejectedExecutionHandler){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize); //线程池创建时候初始化的线程数
        executor.setMaxPoolSize(maxPoolSize); //线程池最大的线程数
        executor.setQueueCapacity(queueCapacity); //缓冲队列
        executor.setKeepAliveSeconds(keepAliveSeconds); //允许线程等待处理的时间,超过24小时还没有被执行,直接销毁
        executor.setThreadNamePrefix(threadNamePrefix); //线程池名的前缀
        executor.setWaitForTasksToCompleteOnShutdown(waitForTasksToCompleteOnShutdown); //关闭的时候等待所有任务执行完成再销毁
        executor.setAwaitTerminationSeconds(awaitTerminationSeconds); //强制销毁时间,超过设置值60s强制销毁
        executor.setRejectedExecutionHandler(rejectedExecutionHandler); //线程池对拒绝任务的处理策略
        return executor;
    }
}
