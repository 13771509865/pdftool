package com.neo.service.accessTimes;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.constants.RedisConsts;
import com.neo.commons.util.DateViewUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.model.po.PtsConvertRecordPO;
import com.neo.service.cache.CacheManager;
import com.neo.service.cache.CacheService;
import com.neo.service.convertRecord.IConvertRecordService;

/**
 * 
 * @authore xujun
 * @create 2019-07-22
 */
@Service("accessTimesService")
//多例
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AccessTimesService {

	@Autowired
	private CacheService<String> cacheService;
	
	@Autowired
	private IConvertRecordService iConvertRecordService;


	/**
	 * Description:ip每日数量重置
	 * @return
	 */
	public void clearIpTimes() {
		CacheManager<String> cacheManager = cacheService.getCacheManager();
		cacheManager.delete( RedisConsts.IP_CONVERT_TIME_KEY);
		cacheManager.delete( RedisConsts.ID_CONVERT_TIME_KEY);
	}
	
	
	/**
	 * 清理dayTime天数前的convertRecord
	 * @param dayTime 
	 */
	public void clearConvertRecord(Integer dayTime) {
		String d = DateViewUtils.getDayBefore(dayTime);
		Date modifiedDate = DateViewUtils.parseSimpleDate(d);
		if(modifiedDate!=null) {
			PtsConvertRecordPO ptsConvertRecordPO = new PtsConvertRecordPO();
			ptsConvertRecordPO.setModifiedDate(modifiedDate);
			int deleteNum = iConvertRecordService.deletePtsConvertRecord(ptsConvertRecordPO);
			SysLogUtils.info("========================删除转换记录一共："+deleteNum + "条，日期为："+d+"========================");
		}
	}
	

}
