package com.neo.service;

import com.neo.commons.cons.IResult;

import java.util.Map;

/**
 * ${DESCRIPTION}
 *转码服务
 * @authore sumnear
 * @create 2018-12-10 20:40
 */
public interface IConvertService
{
	/**
	 * Description:转码服务
	 * @param parame
	 * @return
	 */
    public IResult<Map<String,Object>> convert(String parame);

	/**
	 * Description:获取转码结果信息
	 * @param parame
	 * @return
	 */
    public Map<String, Object> convertResult(String parame);
}

