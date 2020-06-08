package com.neo.service.convert.async;

import com.neo.commons.cons.EnumAuthCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.TimeConsts;
import com.neo.commons.cons.entity.ConvertEntity;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.DateViewUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.model.po.PtsConvertRecordPO;
import com.neo.service.auth.impl.AuthManager;
import com.neo.service.cache.impl.RedisCacheManager;
import com.neo.service.convert.PtsConvertService;
import com.neo.service.convertRecord.IConvertRecordService;
import com.neo.service.file.SaveBadFileService;
import com.neo.service.yzcloud.IYzcloudService;
import com.yozosoft.auth.client.security.UaaToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("asyncConvertService")
public class AsyncConvertService {


	@Autowired
	private PtsProperty ptsProperty;

	@Autowired
	private RedisCacheManager<String> redisCacheManager;

	@Autowired
	private AuthManager authManager;

	@Autowired
	private ConfigProperty configProperty;

	@Autowired
	private SaveBadFileService saveBadFileService;

	@Autowired
	private PtsConvertService ptsConvertService;

	@Autowired
	private IYzcloudService iYzcloudService;
	
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
		
		//异步转换结果存redis,保存24小时
		redisCacheManager.setFileInfo(convertEntity.getFileHash(), result.getData().toString(), TimeConsts.SECOND_OF_DAY);
		
		ptsConvertService.updatePtsSummay(result.getData(), convertBO, convertEntity);
		
		if (!result.isSuccess()) {
			//转换失败归还转换次数
			String authCode = authManager.getAuthCode(convertBO);
			String nowDate = DateViewUtils.getNow();
			PtsConvertRecordPO ptsConvertRecordPO = new PtsConvertRecordPO(uaaToken.getUserId(), EnumAuthCode.getValue(authCode), DateViewUtils.parseSimple(nowDate));
			boolean updateConvertNum = iConvertRecordService.updateConvertNum(ptsConvertRecordPO) > 0;
			if(!updateConvertNum) {
				SysLogUtils.info("归还用户转换次数失败："+ ptsConvertRecordPO.toString());
			}
		} 
	}




}
