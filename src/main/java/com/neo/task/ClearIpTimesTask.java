package com.neo.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.neo.commons.util.SysLogUtils;
import com.neo.service.accessTimes.AccessTimesService;

/**
 * 定期清理redis中的记录
 * @author xujun
 * @create 2019-07-26
 */
@Component
public class ClearIpTimesTask {
	
	@Autowired
	private AccessTimesService accessTimesService;

	
	@Scheduled(cron = "0 0 0 * * ?")
	public void clearIpTimes() {
		SysLogUtils.info("=======================================开始清理ip=======================================");
		try {
			accessTimesService.clearIpTimes();
		} catch (Exception e) {
			SysLogUtils.info("清理ip线程异常");
		}
	}

}