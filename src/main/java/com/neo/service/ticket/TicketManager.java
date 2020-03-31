package com.neo.service.ticket;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.properties.ConfigProperty;

/**
 * 限制转换并发的类
 * @author xujun
 * @create 2019-07-23
 */
@Service("ticketManager")
public class TicketManager {
	@Autowired
	private ConfigProperty config;
	
	private static int totalSize;
	
	private ArrayBlockingQueue<String> pool;

//	@PostConstruct
	public void init() {
		totalSize = config.getConvertPoolSize();
		this.pool = new ArrayBlockingQueue<String>(totalSize);
		for (int i = 1; i <= totalSize; i++) {
			pool.offer("ticket " + i);
			System.out.println("ticket num:" + i);
		}
	}


	public int getTotalSize() {
		return totalSize;
	}

	public int Size() {
		return pool.size();
	}

	public String get() {
		try {
			return pool.poll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String take() {
		try {
			return pool.take();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String poll(int seconds) {
		try {
			return pool.poll(seconds, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void put(String ticket) {
		try {
			pool.put(ticket);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
	}
}
