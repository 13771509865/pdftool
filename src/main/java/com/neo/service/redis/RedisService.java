package com.neo.service.redis;

import com.neo.commons.util.SysLogUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * redis分布锁机制避免多次执行定时任务
 * @author miaowei
 */
@Service
public class RedisService {


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public boolean setScheduler(final String key, Object value,Long time) {
        boolean result = false;
        try {
            if(StringUtils.isEmpty(key)){
                return false;
            }
            result =  redisTemplate.opsForValue().setIfAbsent(key, value,time, TimeUnit.SECONDS);
            SysLogUtils.info("--------------"+redisTemplate.opsForValue().get(key));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


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
}
