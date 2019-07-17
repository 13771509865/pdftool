/**
 */
package com.neo.commons.cons.constants;

public final class TimeConsts {
	public static final int SessionTimout 		= 3600*24*5;//五天的秒数
	public static final long Mq_Free_Wait       = 10*1000; //Mq空闲等待时间，10s
	
	public static final int 	Millisecond_Of_Minute 	= 60000;//一分钟的毫秒数
	public static final long	Millisecond_Of_Hour 		= 3600000;//预签名url的超时时间:一小时.(单位是毫秒)
	public static final long	Millisecond_Of_Day 		= 86400000;//一天的毫秒数
	public static final long	Millisecond_Of_Year 		= 31536000000L;//一年的毫秒数
}
