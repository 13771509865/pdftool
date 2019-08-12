package com.neo.service.file;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.IResult;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.dao.FcsFileInfoPOMapper;
import com.neo.model.qo.FcsFileInfoQO;

public class DeleteFileService {
	
	
	@Autowired
	private FcsFileInfoPOMapper fcsFileInfoPOMapper;
	
	
	/**
	 * 根据fileHash和userID删除用户的转换记录
	 * @param fcsFileInfoPO
	 * @param request
	 * @return
	 */
	public IResult<String> deleteConvert(FcsFileInfoQO fcsFileInfoQO,HttpServletRequest request){
		Long userID = HttpUtils.getSessionUserID(request);
		if(userID == null) {
			return DefaultResult.failResult("请登录后，再执行此操作");
		}else {
			fcsFileInfoQO.setUserID(userID);
		}
		try {
			int count = fcsFileInfoPOMapper.deletePtsConvert(fcsFileInfoQO);
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
