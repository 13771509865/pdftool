package com.neo.service.file;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.EnumStatus;
import com.neo.commons.cons.IResult;
import com.neo.commons.util.SysLogUtils;
import com.neo.model.po.FcsFileInfoPO;
import com.neo.service.convert.PtsConvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
	public IResult<String> deleteConvert(Integer id,String fileHash,String uCloudFileId,Long userID){

		FcsFileInfoPO fcsFileInfoPO =new FcsFileInfoPO();
		fcsFileInfoPO.setUserID(userID);
		fcsFileInfoPO.setFileHash(fileHash);
		fcsFileInfoPO.setUCloudFileId(uCloudFileId);
		fcsFileInfoPO.setStatus(EnumStatus.DISABLE.getValue());
		fcsFileInfoPO.setId(id);

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
