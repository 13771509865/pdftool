package com.neo.service.statistics;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.ResultCode;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.dao.FcsFileInfoPOMapper;
import com.neo.model.po.FcsFileInfoPO;

@Service("statisticsService")
public class StatisticsService {
	
	@Autowired
	private FcsFileInfoPOMapper fcsFileInfoPOMapper;
	
	
	/**
	 * 根据userID查询三天内的转换记录
	 * @param request
	 * @return
	 */
	public IResult<List<FcsFileInfoPO>> selectConvertByUserID(HttpServletRequest request){
		Long userID = HttpUtils.getSessionUserID(request);
		if(userID == null) {
			return DefaultResult.failResult("请先登录或注册");
		}
		List<FcsFileInfoPO> list = fcsFileInfoPOMapper.selectFcsFileInfoPOByUserID(userID);
		if(list.isEmpty() || list == null) {
			return DefaultResult.failResult("没有查询到三天内的转换记录");
		}
		return DefaultResult.successResult(list);
	}
	
	
	
	/**
	 * 根据fileHash和userID删除用户的转换记录
	 * @param fcsFileInfoPO
	 * @param request
	 * @return
	 */
	public IResult<String> deleteByFileHash(FcsFileInfoPO fcsFileInfoPO,HttpServletRequest request){
		if( HttpUtils.getSessionUserID(request) == null) {
			return DefaultResult.failResult("请登录后，再执行此操作");
		}
		if(StringUtils.isBlank(fcsFileInfoPO.getFileHash())) {
			return DefaultResult.failResult(ResultCode.E_NOTALL_PARAM.getInfo());
		}
		
		try {
			int count = fcsFileInfoPOMapper.deletePtsConvert(fcsFileInfoPO);
			if(count < 1) {
				return DefaultResult.failResult("删除用户转换记录失败");
			}
			
			return DefaultResult.successResult();
		} catch (Exception e) {
			SysLogUtils.error("删除用户转换记录失败，原因：", e);
			return DefaultResult.failResult("删除用户转换记录失败");
		}
	}
	
	
	

}
