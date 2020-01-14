package com.neo.service.file;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumStatus;
import com.neo.commons.cons.IResult;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.dao.FcsFileInfoPOMapper;
import com.neo.model.po.FcsFileInfoPO;
import com.neo.model.qo.FcsFileInfoQO;


@Service("deleteFileService")
public class DeleteFileService {
	
	
	@Autowired
	private FcsFileInfoPOMapper fcsFileInfoPOMapper;
	
	
	/**
	 * 根据fileHash和userID删除用户的转换记录
	 * @param fcsFileInfoPO
	 * @param request
	 * @return
	 */
	public IResult<String> deleteConvert(String fileHash,Long userID){
		FcsFileInfoPO fcsFileInfoPO =new FcsFileInfoPO();
		
		if(userID == null) {
			return DefaultResult.failResult("请登录后，再执行此操作");
		}else {
			fcsFileInfoPO.setUserID(userID);
			fcsFileInfoPO.setFileHash(fileHash);
			fcsFileInfoPO.setStatus(EnumStatus.DISABLE.getValue());
		}
		try {
			int count = fcsFileInfoPOMapper.updatePtsConvert(fcsFileInfoPO);
			if(count < 1) {
				return DefaultResult.failResult("删除用户转换记录失败");
			}
			return DefaultResult.successResult();
		} catch (Exception e) {
			SysLogUtils.error("删除用户转换记录失败，原因：", e);
			return DefaultResult.failResult("删除用户转换记录失败");
		}
	}
	public static void main(String[] args) {
		String a = "abc12r";
		String newa = StringUtils.substring(a, 4, 5);
		System.out.println(newa);
	}

}
