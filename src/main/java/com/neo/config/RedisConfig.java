package com.neo.config;

import java.time.Duration;
import java.util.HashMap;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo.commons.cons.constants.RedisConsts;

/**
 * redis配置
 * @author xujun
 * 2019-07-30
 */
@Configuration
public class RedisConfig {


	@Bean("ptsRedisTemplate")
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory redisConnectionFactory) {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(buildJackson2JsonRedisSerializer());
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(buildJackson2JsonRedisSerializer());
        //开启事务
        template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();
        return template;
    }

	
    @Bean(name = "cacheKeyGenerator")
    public KeyGenerator cacheKeyGenerator() {
        return (o, method, objects) -> {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(o.getClass().getSimpleName());
            stringBuilder.append(".");
            stringBuilder.append(method.getName());
            stringBuilder.append("[");
            for (Object obj : objects) {
                stringBuilder.append(obj.toString());
            }
            stringBuilder.append("]");
            return stringBuilder.toString();
        };
    }

    @Bean
    public RedisCacheManager cacheManager(LettuceConnectionFactory redisConnectionFactory) {
        RedisCacheManager redisCacheManager = RedisCacheManager.builder(redisConnectionFactory)
                //默认30分钟
                .cacheDefaults(getRedisCacheConfigurationWithTTL(Duration.ofSeconds(30 * 60)))
                //自定义配置,配合@CacheConfig(cacheNames = "custom")使用
                .withInitialCacheConfigurations(new HashMap<String, RedisCacheConfiguration>() {
                    {
                        this.put(RedisConsts.CACHE_DAY, getRedisCacheConfigurationWithTTL(Duration.ofHours(24L)));
                        this.put(RedisConsts.CACHE_QUARTER_DAY, getRedisCacheConfigurationWithTTL(Duration.ofHours(6L)));
                        this.put(RedisConsts.CACHE_HALF_DAY, getRedisCacheConfigurationWithTTL(Duration.ofHours(12L)));
                        this.put(RedisConsts.DYNAMIC_DOMAIN_COUNT_CACHE, getRedisCacheConfigurationWithTTL(Duration.ofHours(6L)));
                    }
                })
                .transactionAware()
                .build();
        return redisCacheManager;
    }

    private RedisCacheConfiguration getRedisCacheConfigurationWithTTL(Duration duration) {
        //disableCachingNullValues()是否允许空值
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(duration)
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(buildJackson2JsonRedisSerializer()));
    }

    private Jackson2JsonRedisSerializer<Object> buildJackson2JsonRedisSerializer() {
        //redis序列化器
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }

	
	

}
