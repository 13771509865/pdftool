package com.neo.service.file;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.EnumStatus;
import com.neo.commons.cons.IResult;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.dao.FcsFileInfoPOMapper;
import com.neo.model.po.FcsFileInfoPO;
import com.neo.model.qo.FcsFileInfoQO;
import com.neo.service.convert.PtsConvertService;


@Service("deleteFileService")
public class DeleteFileService {


	@Autowired
	private PtsConvertService ptsConvertService;


	/**
	 * 根据fileHash和userID删除用户的转换记录
	 * @param fcsFileInfoPO
	 * @param request
	 * @return
	 */
	public IResult<String> deleteConvert(String fileHash,String uCloudFileId,Long userID){

		//filehash和uCloudFileId，必须要有一个
		if(StringUtils.isBlank(fileHash) && StringUtils.isBlank(uCloudFileId)) {
			return DefaultResult.failResult(EnumResultCode.E_NOTALL_PARAM.getInfo());
		}

		FcsFileInfoPO fcsFileInfoPO =new FcsFileInfoPO();
		fcsFileInfoPO.setUserID(userID);
		fcsFileInfoPO.setFileHash(fileHash);
		fcsFileInfoPO.setUCloudFileId(uCloudFileId);
		fcsFileInfoPO.setStatus(EnumStatus.DISABLE.getValue());

		try {
			int count = ptsConvertService.updatePtsConvert(fcsFileInfoPO);
			if(count < 1) {
				return DefaultResult.failResult(EnumResultCode.E_DELETE_CONVERT_RECORD_NULL.getInfo());
			}
			return DefaultResult.successResult();
		} catch (Exception e) {
			SysLogUtils.error("删除用户转换记录失败，原因：", e);
			return DefaultResult.failResult(EnumResultCode.E_DELETE_CONVERT_RECORD_ERROR.getInfo());
		}
	}

}
