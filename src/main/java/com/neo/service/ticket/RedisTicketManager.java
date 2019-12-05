package com.neo.service.ticket;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		 
		 
	 }
















}
