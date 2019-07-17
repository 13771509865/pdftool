package com.neo.service.accessTimes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.RedisConsts;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.util.SysLog4JUtils;
import com.neo.service.cache.CacheManager;
import com.neo.service.cache.CacheService;

/**
 * ${DESCRIPTION} ip上传转码次数控制服务
 * 
 * @authore dh
 * @create 2018-12-13 20:40
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
		cacheManager.delete( RedisConsts.IpConvertTimesKey);
		cacheManager.delete( RedisConsts.IdConvertTimesKey);
	}

}
