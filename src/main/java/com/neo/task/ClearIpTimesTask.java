package com.neo.task;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.neo.commons.properties.ConfigProperty;
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

	@Autowired
	private ConfigProperty configProperty;
	
	
	 /*"0 0 12 * * ?" 每天中午十二点触发 "0 15 10 ? * *" 每天早上10：15触发 "0 15 10 * * ?"
    每天早上10：15触发 "0 15 10 * * ? *" 每天早上10：15触发 "0 15 10 * * ? 2005" 2005年的每天早上10：15触发
		"0 * 14 * * ?" 每天从下午2点开始到2点59分每分钟一次触发 "0 0/5 14 * * ?" 每天从下午2点开始到2：55分结束每5分钟一次触发
		"0 0/5 14,18 * * ?" 每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发 "0 0-5 14 * * ?"
    每天14:00至14:05每分钟一次触发 "0 10,44 14 ? 3 WED" 三月的每周三的14：10和14：44触发 "0 15 10 ?
            * MON-FRI" 每个周一、周二、周三、周四、周五的10：15触发 "0 15 10 15 * ?" 每月15号的10：15触发 "0 15
            10 L * ?" 每月的最后一天的10：15触发 "0 15 10 ? * 6L" 每月最后一个周五的10：15触发 "0 15 10 ? *
            6L" 每月最后一个周五的10：15触发 "0 15 10 ? * 6L 2002-2005" 2002年至2005年的每月最后一个周五的10：15触发
            "0 15 10 ? * 6#3" 每月的第三个周五的10：15触发*/


	@Scheduled(cron = "0 0 0 * * ?")
	public void clearReconvert() {
		SysLogUtils.info("=======================================开始清理重复转换失败记录=======================================");
		try {
			accessTimesService.clearReconvert();
		} catch (Exception e) {
			SysLogUtils.info("清理重复转换失败记录,原因："+e.getMessage());
		}
	}



	@Scheduled(cron = "0 0 0 * * ?")
	public void clearConvertRecord() {
		try {
			if(configProperty.getClearDay()>0 && configProperty.getClearMaster()) {
				SysLogUtils.info("=======================================开始清理转换记录=======================================");
				accessTimesService.clearConvertRecord(configProperty.getClearDay());
			}
		} catch (Exception e) {
			SysLogUtils.info("清理转换记录线程异常,原因："+e.getMessage());
		}
	}

}