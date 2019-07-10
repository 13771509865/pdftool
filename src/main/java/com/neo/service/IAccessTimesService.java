package com.neo.service;

import com.neo.commons.cons.IResult;

/**
 * ${DESCRIPTION} ip上传转码次数控制服务
 * 
 * @authore dh
 * @create 2018-12-13 20:40
 */
public interface IAccessTimesService {
	/**
	 * Description:获取该ip当天上传次数
	 * 
	 * @param ip
	 * @return
	 */
	public IResult<Integer> getIpUploadTimes(String ip);

	/**
	 * Description:获取该ip当天转码次数
	 * 
	 * @param ip
	 * @return
	 */
	public IResult<Integer> getIpConvertTimes(String ip);

	/**
	 * Description:获取该ip当天上传转码信息
	 * 
	 * @param ip
	 * @return
	 */
	public IResult<Integer> addUploadTimes(String ip);

	/**
	 * Description:该ip当天上传次数加一；达到最大限制则返回失败
	 * 
	 * @param ip
	 * @return
	 */
	public IResult<Integer> addConvertTimes(String ip);

	/**
	 * Description:ip统计数量
	 * @return
	 */
	public IResult<Long> getTotalIpCount();
	
	/**
	 * Description:转码统计数量
	 * @return
	 */
	public IResult<Integer> getTotalConvertTimesCount() ;

	/**
	 * Description:ip每日数量重置
	 * @return
	 */
	public void clearIpTimes() ;
}