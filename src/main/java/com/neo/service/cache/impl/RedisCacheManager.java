package com.neo.service.cache.impl;

import com.neo.commons.util.SysLogUtils;
import com.neo.config.RedisConfig;
import com.neo.service.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * redis实现存取
 *
 * @param <T>
 * @author zhouf
 * @create 2018-12-14 11:49
 */
@ConditionalOnBean(RedisConfig.class)
@Service("redisCacheManager")
public class RedisCacheManager<T> implements CacheManager<T> {

    @Autowired
    @Qualifier("htbRateLimiterScript")
    private DefaultRedisScript<Number> htbRateLimiterScript;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean set(String key, T value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            SysLogUtils.error("key为" + key + ",redis插入失败", e);
            return false;
        }
    }

    @Override
    public boolean set(String key, T value, Long time) {
        try {
            if (StringUtils.isEmpty(key)) {
                return false;
            }
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            SysLogUtils.error("key为" + key + ",redis插入失败", e);
            return false;
        }
    }

    @Override
    public boolean setnx(String key, Object value, Long time) {
        try {
            if (StringUtils.isEmpty(key)) {
                return false;
            }
            return redisTemplate.opsForValue().setIfAbsent(key, value, time, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            SysLogUtils.error("key为" + key + ",redis setnx插入失败", e);
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


    //redis不支持Long
    @Override
    public T get(String key, Class<T> clazz) {
        try {
            if (StringUtils.isEmpty(key)) {
                return null;
            }
            Object obj = redisTemplate.opsForValue().get(key);
            if (clazz != null && clazz == Long.class && obj instanceof Integer) {
                return (T) Long.valueOf(obj.toString());
            }
            return (T) obj;
        } catch (Exception e) {
            e.printStackTrace();
            SysLogUtils.error("key为" + key + ",redis获取失败", e);
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
            SysLogUtils.error("key为" + key + ",redis判断存在失败", e);
            return false;
        }
    }


    @Override
    public boolean orderExists(String key) {
        try {
            if (StringUtils.isEmpty(key)) {
                return false;
            }
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            SysLogUtils.error("key为" + key + ",redis判断存在失败", e);
            return true;
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
            SysLogUtils.error("redis清空失败", e);
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
            SysLogUtils.error("key为" + key + ",redis删除失败", e);
            return false;
        }
    }

    @Override
    public boolean push(String key, T value) {
        try {
            if (StringUtils.isEmpty(key)) {
                return false;
            }
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            SysLogUtils.error("key为" + key + ",redis push失败", e);
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
            SysLogUtils.error("key为" + key + ",redis pop失败", e);
            return null;
        }
    }


    @Override
    public Long getListLen(String key) {
        try {
            if (StringUtils.isEmpty(key)) {
                return -1L;
            }
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            SysLogUtils.error("key为" + key + ",redis获取llen失败", e);
            return -1L;
        }
    }


    /**
     * 向ZSet塞值，如果值存在就分数加1
     *
     * @param key
     * @param value
     * @return
     * @author zhouf
     * @create 2018-12-14 16:14
     */
    @Override
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
            SysLogUtils.error("key为" + key + ",redis插入ZSet失败", e);
            return false;
        }
    }

    /**
     * 根据key和value获得分数
     *
     * @param key
     * @param value
     * @return
     * @author zhouf
     * @create 2018-12-14 16:14
     */
    @Override
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
            SysLogUtils.error("key为" + key + ",redis拿分失败", e);
            return (double) 0;
        }
    }

    /**
     * 根据key拿到总数
     *
     * @param key
     * @return
     * @author zhouf
     * @create 2018-12-14 16:14
     */
    @Override
    public Long getZSetCount(String key) {
        try {
            if (StringUtils.isEmpty(key)) {
                return 0l;
            } else {
                Long zCard = redisTemplate.opsForZSet().zCard(key);
                if (zCard == null) {
                    return 0L;
                } else {
                    return zCard;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            SysLogUtils.error("key为" + key + ",redis获取ZSet总数失败", e);
            return 0L;
        }
    }

    @Override
    public Boolean setHashValue(String key, String hashKey, T value) {
        try {
            if (StringUtils.isEmpty(key) || StringUtils.isEmpty(hashKey)) {
                return false;
            }
            redisTemplate.opsForHash().put(key, hashKey, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            SysLogUtils.error("key为" + key + ",redis Hash插入失败", e);
            return false;
        }
    }

    @Override
    public T getHashValue(String key, String hashKey) {
        try {
            if (StringUtils.isEmpty(key) || StringUtils.isEmpty(hashKey)) {
                return null;
            }
            return (T) redisTemplate.opsForHash().get(key, hashKey);
        } catch (Exception e) {
            e.printStackTrace();
            SysLogUtils.error("key为" + key + ",redis Hash获取失败", e);
            return null;
        }
    }


    @Override
    public Boolean existHashKey(String key, String hashKey) {
        try {
            if (StringUtils.isEmpty(key) || StringUtils.isEmpty(hashKey)) {
                return false;
            }
            return redisTemplate.opsForHash().hasKey(key, hashKey);
        } catch (Exception e) {
            e.printStackTrace();
            SysLogUtils.error("key为" + key + ",redis Hash判断存在失败", e);
            return false;
        }
    }

    @Override
    public Boolean deleteHashKey(String key, String hashKey) {
        try {
            if (StringUtils.isEmpty(key) || StringUtils.isEmpty(hashKey)) {
                return false;
            }
            redisTemplate.opsForHash().delete(key, hashKey);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            SysLogUtils.error("key为" + key + ",redis Hash删除失败", e);
            return false;
        }
    }

    /**
     * @return true表示限流, false表示不限流
     * @description 判断限流操作
     * @author zhoufeng
     * @date 2019/12/03
     */
    public Boolean htbRateLimiter(String key, Integer max_permits, Float permits_per_second, Integer required_permits, Long timeout_micros) {
        List<String> keys = Collections.singletonList("htbRateLimiter_" + key);
        Number number = redisTemplate.execute(htbRateLimiterScript, keys, max_permits, permits_per_second, required_permits, timeout_micros);
        return number.longValue() > 0;
    }

}
