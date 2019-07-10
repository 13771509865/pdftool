package com.neo.service.accessTimes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.RedisConsts;
import com.neo.commons.util.SysLog4JUtils;
import com.neo.config.SysConfig;
import com.neo.service.IAccessTimesService;
import com.neo.service.cache.CacheManager;
import com.neo.service.cache.CacheService;

/**
 * ${DESCRIPTION} ip上传转码次数控制服务
 * 
 * @authore dh
 * @create 2018-12-13 20:40
 */
@Service("accessTimesService")
public class AccessTimesService implements IAccessTimesService {

	@Autowired
	private CacheService<String> cacheService;
	private String totalConvertTimesKey = RedisConsts.TotalConvertTimesKey;
	private String ipKey = RedisConsts.IpKey;
	private String ipUploadTimesKey = RedisConsts.IpUploadTimesKey;
	private String ipconvertTimesKey = RedisConsts.IpconvertTimesKey;

	/**
	 * Description:获取该ip当天上传次数
	 * 
	 * @param ip
	 * @return
	 */
	@Override
	public IResult<Integer> getIpUploadTimes(String ip) {

		try {
			addTotalIp(ip);
			Integer uploadTimes = getUploadTimes(ip);
			return DefaultResult.successResult(uploadTimes);
		} catch (Exception e) {
			SysLog4JUtils.error(e);
			return DefaultResult.failResult("get upload times failed :" + ip);
		}
	}

	/**
	 * Description:获取该ip当天上传次数
	 * 
	 * @param ip
	 * @return
	 */
	private Integer getUploadTimes(String ip) {
		try {
			CacheManager<String> cacheManager = cacheService.getCacheManager();
			Double uploadTimesDouble = cacheManager.getScore(ipUploadTimesKey, ip);
			if (uploadTimesDouble == null) {
				cacheManager.pushZSet(ipUploadTimesKey, "ip");
				uploadTimesDouble = (double) 0;
			}
			Integer uploadTimes = uploadTimesDouble.intValue();
			return uploadTimes;
		} catch (Exception e) {
			SysLog4JUtils.error(e);
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Description:获取该ip当天转码次数
	 * 
	 * @param ip
	 * @return
	 */
	@Override
	public IResult<Integer> getIpConvertTimes(String ip) {
		try {
			Integer convertTimes = getConvertTimes(ip);
			return DefaultResult.successResult(convertTimes);
		} catch (Exception e) {
			SysLog4JUtils.error(e);
			return DefaultResult.failResult("get convert times failed :" + ip);
		}
	}

	/**
	 * Description:获取该ip当天转码次数
	 * 
	 * @param ip
	 * @return
	 */
	private Integer getConvertTimes(String ip) {
		try {
			CacheManager<String> cacheManager = cacheService.getCacheManager();
			Double convertTimesDouble = cacheManager.getScore(ipconvertTimesKey, ip);
			if (convertTimesDouble == null) {
				cacheManager.pushZSet(ipconvertTimesKey, "ip");
				convertTimesDouble = (double) 0;
			}
			Integer convertTimes = convertTimesDouble.intValue();
			return convertTimes;
		} catch (Exception e) {
			SysLog4JUtils.error(e);
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Description:该ip当天上传次数加一返回上传次数；达到最大限制则返回0
	 * 
	 * @param ip
	 * @return
	 */
	public IResult<Integer> addUploadTimes(String ip) {
		try {
			Integer result = 0;
			CacheManager<String> cacheManager = cacheService.getCacheManager();
			Integer uploadTimes = getUploadTimes(ip);

			// if (uploadTimes >= config.getUploadTimes()) {
			// result = 0;
			// } else {
			// }
			cacheManager.pushZSet(ipUploadTimesKey, ip);
			result = uploadTimes + 1;
			return DefaultResult.successResult(result);
		} catch (Exception e) {
			SysLog4JUtils.error(e);
			return DefaultResult.failResult("add upload times failed :" + ip);
		}
	}

	/**
	 * Description:该ip当天转码次数加一返回上传次数；达到最大限制则返回0
	 * 
	 * @param ip
	 * @return
	 */
	public IResult<Integer> addConvertTimes(String ip) {
		try {
			Integer result = 0;
			CacheManager<String> cacheManager = cacheService.getCacheManager();
			Integer convertTimes = getConvertTimes(ip);
			Integer totalConvertTimes = 0;

			// if (convertTimes >= config.getConvertTimes()) {
			// result = 0;
			// } else {
			// }
			result = convertTimes + 1;

			if (!cacheManager.exists(totalConvertTimesKey)) {
				cacheManager.set(totalConvertTimesKey, totalConvertTimes.toString());
			}
			totalConvertTimes = getTotalConvertCount() + 1;
			if (totalConvertTimes > 0) {
				cacheManager.pushZSet(ipconvertTimesKey, ip);
				cacheManager.set(totalConvertTimesKey, totalConvertTimes.toString());
			}
			return DefaultResult.successResult(result);
		} catch (Exception e) {
			SysLog4JUtils.error(e);
			return DefaultResult.failResult("add convert times failed :" + ip);
		}
	}

	/**
	 * Description:ip统计数量
	 * 
	 * @return
	 */
	public IResult<Long> getTotalIpCount() {
		try {
			CacheManager<String> cacheManager = cacheService.getCacheManager();
			Long countLong = cacheManager.getZSetCount(ipKey);
			return DefaultResult.successResult(countLong);
		} catch (Exception e) {
			SysLog4JUtils.error(e);
			return DefaultResult.failResult("get Total Ip Count failed");
		}

	}

	/**
	 * Description:ip统计数量+1
	 * 
	 * @param ip
	 * @return
	 */
	private void addTotalIp(String ip) {
		CacheManager<String> cacheManager = cacheService.getCacheManager();
		cacheManager.pushZSet(ipKey, ip);
	}

	/**
	 * Description:转码统计数量
	 * 
	 * @return
	 */
	public IResult<Integer> getTotalConvertTimesCount() {
		try {
			Integer count = getTotalConvertCount();
			return DefaultResult.successResult(count);
		} catch (Exception e) {
			SysLog4JUtils.error(e);
			return DefaultResult.failResult("get Total Convert Times failed");
		}
	}

	/**
	 * Description:转码统计数量
	 * 
	 * @return
	 */
	private Integer getTotalConvertCount() {
		try {
			Integer count = 0;
			CacheManager<String> cacheManager = cacheService.getCacheManager();
			String countString = cacheManager.get(totalConvertTimesKey);
			if (countString == null) {
				cacheManager.set(totalConvertTimesKey, String.valueOf(0));
			} else {
				count = Integer.valueOf(countString);
			}
			return count;
		} catch (Exception e) {
			SysLog4JUtils.error(e);
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Description:ip每日数量重置
	 * 
	 * @return
	 */
	public void clearIpTimes() {
		CacheManager<String> cacheManager = cacheService.getCacheManager();
		cacheManager.delete(ipUploadTimesKey);
		cacheManager.delete(ipconvertTimesKey);
	}

}
