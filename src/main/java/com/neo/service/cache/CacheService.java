package com.neo.service.cache;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.service.cache.impl.RedisCacheManager;

/**
 * 要使用存取直接用这个类
 * @author zhouf
 * @create 2018-12-14 11:50
 */
@Service("cacheService")
public class CacheService<T> {

	private CacheManager<T> cacheManager;
	
	@Autowired
	private RedisCacheManager<T> redisCacheManager;
	
	public CacheManager<T> getCacheManager(){
		cacheManager = redisCacheManager;
		return cacheManager;
	}
}
