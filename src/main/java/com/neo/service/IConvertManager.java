package com.neo.service;

import com.neo.commons.cons.IResult;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.bo.FileInfoBO;

/**
 * 转换接口类
 * @author zhouf
 * @create 2018-12-14 11:57
 */
public interface IConvertManager {
	 public IResult<FileInfoBO> dispatchConvert(ConvertParameterBO paramBO);
}
