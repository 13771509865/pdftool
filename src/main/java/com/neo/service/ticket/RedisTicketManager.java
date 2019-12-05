package com.neo.service.ticket;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.IResult;
import com.neo.commons.properties.ConfigProperty;
import com.neo.service.cache.impl.RedisCacheManager;

@Service("redisTicketManager")
public class RedisTicketManager {

	@Autowired
	private ConfigProperty config;

	@Autowired
	private RedisCacheManager<String> redisCacheManager;



//	@PostConstruct
	public void init() {
		if (config.getTicketMaster()) {
			initPriorityQueue();
		}
	}



	public void initPriorityQueue() {
		Integer convertTicket = config.getConvertPoolSize();
		Boolean check1 = convertTicket!=null && convertTicket>0;
		if(check1) {
			redisCacheManager.initPriorityQueue();
			System.out.println("ticekt队列初始化成功");
		}else {
			System.out.println("ticekt参数有误,请检查配置文件相关参数");
		}
	}



	public IResult<String> getConverTicket(){
		return null;
	}
	













}
