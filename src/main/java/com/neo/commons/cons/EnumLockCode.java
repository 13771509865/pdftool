package com.neo.commons.cons;

import lombok.Getter;


/**
 * 主要用于订单购买是是否上锁
 * @author xujun
 * @description
 * @create 2020年4月3日
 */
@Getter
public enum EnumLockCode {
	
	UNLOCK(0, "解锁"),
	LOCK(1, "锁");
	
	
	private Integer value;
	private String info;
	
	private EnumLockCode(int code, String info) {
		this.value = code;
		this.info = info;
	}

}
