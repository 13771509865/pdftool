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
import com.neo.model.bo.UserBO;
import com.neo.service.cache.impl.RedisCacheManager;

@Service("redisTicketManager")
public class RedisTicketManager {

	@Autowired
	private ConfigProperty configProperty;

	@Autowired
	private RedisCacheManager<String> redisCacheManager;



	@PostConstruct
	public void init() {
		if (configProperty.getTicketMaster()) {
			initPriorityQueue(); 
		}
	}



	/**
	 * 初始化队列
	 */
	public void initPriorityQueue() {
		Integer convertTicket = configProperty.getConvertPoolSize();
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
	public String getConverTicket(UserBO userBO){
		if(userBO!=null && EnumMemberType.isMember(userBO.getMembership())) {//会员
			return redisCacheManager.pop(RedisConsts.CONVERT_QUEUE_KEY);
		}else {//非会员
			Long ticketNum = redisCacheManager.getListLen(RedisConsts.CONVERT_QUEUE_KEY);
			if(ticketNum >0 && ticketNum <= configProperty.getTicketLimiter()) {
				try {
					Thread.currentThread().sleep(configProperty.getTicketWaitTime()*1000);
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
