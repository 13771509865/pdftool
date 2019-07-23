package com.neo.service.cache;

public interface CacheManager<T> {
	
	public abstract boolean set(String key,T value);//设置指定key的值
	
	public abstract boolean set(String key, T value, Long time);
	
	public abstract T get(String key);//获取指定key的value
	
	public abstract T get(String key, Class<T> clazz);
	
	public abstract boolean exists(String key);//判断指定key是否存在
	
	public abstract boolean clearAll();//清空缓存
	
	public abstract boolean delete(String key);//删除指定key
	
	public abstract boolean push(String key,T value);
	
	public abstract T pop(String key);
	
	public abstract Boolean pushZSet(String key,T value); //往zset中塞值
	
	public abstract  Long getListLen(String key);
	
	public abstract Double getScore(String key,T value);//获得指定value的ip
	
	public abstract Long getZSetCount(String key);//获取zset的总数
	
	public abstract Boolean setHashValue(String key,String hashKey,T value);
	
	public abstract T getHashValue(String key,String hashKey);
	
	public abstract Boolean existHashKey(String key,String hashKey);
	
	public Boolean deleteHashKey(String key,String hashKey);

}
