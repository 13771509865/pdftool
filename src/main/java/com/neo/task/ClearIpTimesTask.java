package com.neo.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neo.commons.util.SysLog4JUtils;
import com.neo.service.accessTimes.AccessTimesService;

/*
 * spring.xml 配置的 任务调度类
 */
/**
 * 定期清理文件
 * 
 * @author zhouf
 * @create 2018-12-10 21:20
 */
@Component
public class ClearIpTimesTask {
	@Autowired
	private AccessTimesService accessTimesService;

	public void clearIpTimes() {
		SysLog4JUtils.info("=======================================开始清理ip=======================================");
		try {
			accessTimesService.clearIpTimes();
		} catch (Exception e) {
			SysLog4JUtils.info("清理ip线程异常");
		}
	}

}