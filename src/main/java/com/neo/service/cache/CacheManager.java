package com.neo.service.cache;

public interface CacheManager<T> {

	boolean set(String key,T value);//设置指定key的值

	boolean set(String key, T value, Long time);

	boolean setnx(String key, Object value, Long time);

	T get(String key);//获取指定key的value

	T get(String key, Class<T> clazz);

	boolean exists(String key);//判断指定key是否存在

	boolean clearAll();//清空缓存

	boolean delete(String key);//删除指定key

	boolean push(String key,T value);

	T pop(String key);

	Boolean pushZSet(String key,T value); //往zset中塞值

	Long getListLen(String key);

	Double getScore(String key,T value);//获得指定value的ip

	Long getZSetCount(String key);//获取zset的总数

	Boolean setHashValue(String key,String hashKey,T value);

	T getHashValue(String key,String hashKey);

	Boolean existHashKey(String key,String hashKey);

	Boolean deleteHashKey(String key,String hashKey);

}
