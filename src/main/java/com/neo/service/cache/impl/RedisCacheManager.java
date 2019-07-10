package com.neo.service.cache.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.neo.service.cache.CacheManager;

/**
 * redis实现存取
 * 
 * @author zhouf
 * @create 2018-12-14 11:49
 * @param <T>
 */
@Service("redisCacheManager")
public class RedisCacheManager<T> implements CacheManager<T> {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public boolean set(String key, T value) {
		try {
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public T get(String key) {
		try {
			if (StringUtils.isEmpty(key)) {
				return null;
			}
			return (T) redisTemplate.opsForValue().get(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean exists(String key) {
		try {
			if (StringUtils.isEmpty(key)) {
				return false;
			}
			return redisTemplate.hasKey(key);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean clearAll() {
		try {
			return redisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					connection.flushDb();
					return true;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(String key) {
		try {
			if (StringUtils.isEmpty(key)) {
				return false;
			}
			redisTemplate.delete(key);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean push(String key, T value) {
		try {
			Long result = redisTemplate.opsForList().rightPush(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public T pop(String key) {
		try {
			if (StringUtils.isEmpty(key)) {
				return null;
			}
			return (T) redisTemplate.opsForList().leftPop(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 向ZSet塞值，如果值存在就分数加1
	 * 
	 * @author zhouf
	 * @create 2018-12-14 16:14
	 * @param key
	 * @param value
	 * @return
	 */
	public Boolean pushZSet(String key, T value) {
		try {
			Double score = redisTemplate.opsForZSet().score(key, value); // 拿风,不存在返回null
			if (score == null) { // 新建
				redisTemplate.opsForZSet().add(key, value, 1);
			} else { // 已存在
				redisTemplate.opsForZSet().incrementScore(key, value, 1);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根据key和value获得分数
	 * 
	 * @author zhouf
	 * @create 2018-12-14 16:14
	 * @param key
	 * @param value
	 * @return
	 */
	public Double getScore(String key, T value) {
		try {
			if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
				return (double) 0;
			} else {
				Double score = redisTemplate.opsForZSet().score(key, value);
				if (score == null) {
					return (double) 0;
				} else {
					return score;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return (double) 0;
		}
	}

	/**
	 * 根据key拿到总数
	 * 
	 * @author zhouf
	 * @create 2018-12-14 16:14
	 * @param key
	 * @return
	 */
	public Long getZSetCount(String key) {
		try {
			if (StringUtils.isEmpty(key)) {
				return 0l;
			} else {
				Long zCard = redisTemplate.opsForZSet().zCard(key);
				if(zCard==null){
					return 0L;
				}else{
					return zCard;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0L;
		}
	}
	
	public Boolean setHashValue(String key,String hashKey,T value){
		try{
			redisTemplate.opsForHash().put(key, hashKey, value);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public T getHashValue(String key,String hashKey){
		try{
			if(StringUtils.isEmpty(key) || StringUtils.isEmpty(hashKey)){
				return null;
			}
			T object = (T)redisTemplate.opsForHash().get(key, hashKey);
			return object;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public Boolean existHashKey(String key,String hashKey){
		try{
			if(StringUtils.isEmpty(key) || StringUtils.isEmpty(hashKey)){
				return false;
			}
			return redisTemplate.opsForHash().hasKey(key, hashKey);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public Boolean deleteHashKey(String key,String hashKey){
		try{
			if(StringUtils.isEmpty(key) || StringUtils.isEmpty(hashKey)){
				return false;
			}
			redisTemplate.opsForHash().delete(key, hashKey);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
}
