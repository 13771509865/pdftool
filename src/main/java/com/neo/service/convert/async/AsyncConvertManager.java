package com.neo.service.convert.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.entity.ConvertEntity;
import com.neo.commons.util.HttpUtils;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.bo.FcsFileInfoBO;
import com.yozosoft.auth.client.security.UaaToken;


@Service("asyncConvertManager")
public class AsyncConvertManager {

	@Autowired
	private AsyncConvertService asyncConvertService;

	
	/**
	 * 给FcsFileInfoBO赋值
	 * @param convertBO
	 * @param uaaToken
	 * @param convertEntity
	 * @return
	 */
	public IResult<FcsFileInfoBO> dispatchConvert(ConvertParameterBO convertBO,UaaToken uaaToken,ConvertEntity convertEntity) {
		FcsFileInfoBO fcsFileInfoBO = new FcsFileInfoBO();
		try {
			fcsFileInfoBO.setCode(EnumResultCode.E_SUCCES.getValue());
			fcsFileInfoBO.setFileHash(convertEntity.getFileHash());
			asyncConvertService.asyncConvert(convertBO, uaaToken, convertEntity);
			return DefaultResult.successResult(fcsFileInfoBO);
		} catch (TaskRejectedException taskException) {
			fcsFileInfoBO.setCode(EnumResultCode.E_SERVER_BUSY.getValue());
			return DefaultResult.failResult(EnumResultCode.E_SERVER_BUSY.getInfo(), fcsFileInfoBO);
		}

	}

}
