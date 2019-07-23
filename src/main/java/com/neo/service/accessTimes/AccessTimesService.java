package com.neo.service.accessTimes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.RedisConsts;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.util.SysLogUtils;
import com.neo.service.cache.CacheManager;
import com.neo.service.cache.CacheService;

/**
 * 
 * @authore xujun
 * @create 2019-07-22
 */
@Service("accessTimesService")
public class AccessTimesService {

	@Autowired
	private CacheService<String> cacheService;


	/**
	 * Description:ip每日数量重置
	 * @return
	 */
	public void clearIpTimes() {
		CacheManager<String> cacheManager = cacheService.getCacheManager();
		cacheManager.delete( RedisConsts.IP_CONVERT_TIME_KEY);
		cacheManager.delete( RedisConsts.ID_CONVERT_TIME_KEY);
	}

}
