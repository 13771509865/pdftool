package com.neo.service.convert.async;

import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.TimeConsts;
import com.neo.commons.cons.entity.ConvertEntity;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.service.auth.impl.AuthManager;
import com.neo.service.cache.impl.RedisCacheManager;
import com.neo.service.convert.PtsConvertService;
import com.neo.service.convertRecord.IConvertRecordService;
import com.neo.service.convertRecord.ITotalConvertRecordService;
import com.yozosoft.auth.client.security.UaaToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("asyncConvertService")
public class AsyncConvertService {


	@Autowired
	private RedisCacheManager<String> redisCacheManager;

	@Autowired
	private AuthManager authManager;

	@Autowired
	private PtsConvertService ptsConvertService;

	@Autowired
	private ITotalConvertRecordService iTotalConvertRecordService;
	
	@Autowired
	private IConvertRecordService iConvertRecordService;
	


	/**
	 * 异步转换
	 * @param convertBO
	 * @param uaaToken
	 * @param convertEntity
	 */
	@Async("asynConvertExecutor")
	public void asyncConvert(ConvertParameterBO convertBO,UaaToken uaaToken,ConvertEntity convertEntity){
		IResult<FcsFileInfoBO> result = ptsConvertService.dispatchConvert(convertBO, uaaToken, convertEntity);
		FcsFileInfoBO fcsFileInfoBO = result.getData();
		fcsFileInfoBO.setFcsMessage(result.getMessage());

		//异步转换结果存redis,保存24小时
		redisCacheManager.setFileInfo(convertEntity.getFileHash(), fcsFileInfoBO.toString(), TimeConsts.SECOND_OF_DAY);
		
		ptsConvertService.updatePtsSummay(result, convertBO, convertEntity);

		//转换失败归还转换次数
		if (!result.isSuccess()) {
			ptsConvertService.returnConvertNum(convertEntity,authManager.getAuthCode(convertBO));
		} 
	}




}
