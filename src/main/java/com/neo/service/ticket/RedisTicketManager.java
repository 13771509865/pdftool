package com.neo.service.ticket;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.EnumMemberType;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.RedisConsts;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.util.SysLogUtils;
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



	/**
	 * 取票
	 * @param membership
	 * @return
	 */
	public String getConverTicket(String membership){
		if(EnumMemberType.isMember(membership)) {//会员
			return redisCacheManager.pop(RedisConsts.CONVERT_QUEUE_KEY);
		}else {//非会员
			Long ticketNum = redisCacheManager.getListLen(RedisConsts.CONVERT_QUEUE_KEY);
			if(ticketNum >0 && ticketNum <=config.getTicketLimiter()) {
				try {
					Thread.currentThread().sleep(2000);
				} catch (InterruptedException e) {
					return null;
				}
			}
			return redisCacheManager.pop(RedisConsts.CONVERT_QUEUE_KEY);
		}
	}
	



	/**
	 * 还票
	 * @param ticket
	 */
	public void returnPriorityTicket(String ticket) {
		Boolean result = false;
		if(StringUtils.isNotBlank(ticket)) {
			result = redisCacheManager.push(RedisConsts.CONVERT_QUEUE_KEY, ticket);
		}
		if(!result) {
			SysLogUtils.error("redis返还ticket失败");
		}
	}










}
