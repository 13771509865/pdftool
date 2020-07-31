package com.neo.service.statistics;

import com.neo.commons.cons.*;
import com.neo.commons.cons.constants.PtsConsts;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.cons.constants.UaaConsts;
import com.neo.commons.cons.constants.YzcloudConsts;
import com.neo.commons.cons.entity.HttpResultEntity;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.DateViewUtils;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.dao.FcsFileInfoPOMapper;
import com.neo.dao.PtsApplyPOMapper;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.model.po.FcsFileInfoPO;
import com.neo.model.qo.FcsFileInfoQO;
import com.neo.service.cache.impl.RedisCacheManager;
import com.neo.service.httpclient.HttpAPIService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("statisticsService")
public class StatisticsService {

	@Autowired
	private FcsFileInfoPOMapper fcsFileInfoPOMapper;

	@Autowired
	private RedisCacheManager<String> redisCacheManager;

	@Autowired
	private PtsProperty ptsProperty;

	@Autowired
	private HttpAPIService httpAPIService;

	@Autowired
	private ConfigProperty configProperty;

	@Autowired
	private StaticsManager staticsManager;

	@Autowired
	private PtsApplyPOMapper ptsApplyPOMapper;


	/**
	 * 根据userID查询三天内的转换记录
	 * @param userID
	 * @return
	 */
	public IResult<Map<String,Object>> selectConvertByUserID(FcsFileInfoQO fcsFileInfoQO,Long userID){
		if(userID == null) {
			return DefaultResult.failResult("请登录后，再执行此操作");
		}else {
			fcsFileInfoQO.setUserID(userID);
			fcsFileInfoQO.setStatus(EnumStatus.ENABLE.getValue());
		}
		try {
			Map<String,Object> map = new HashMap<>();
			List<FcsFileInfoPO> list = fcsFileInfoPOMapper.selectFcsFileInfoPOByUserID(fcsFileInfoQO);
			map.put(SysConstant.FCS_DATA, list);
			map.put(SysConstant.COUNT, list.size());
			return DefaultResult.successResult(map);
		} catch (Exception e) {
			SysLogUtils.error("查询三天内的转换记录失败，原因：", e);
			return DefaultResult.failResult("没有查询到三天内的转换记录");
		}
	}





	/**
	 * 查询剩余的转换次数
	 * @param userID
	 * @return
	 */
	public IResult<Map<String,Object>> getConvertTimes(Long userID){
		try {
			//获取用户剩余权益
			IResult<Map<String,Object>> result =  staticsManager.getAuth(userID);
			if(!result.isSuccess()){
				return DefaultResult.failResult(result.getMessage());
			}

			Map<String,Object> newMap = new HashMap<>();

			//转换成前端想要的字符
			for (Map.Entry<String, Object> numEntry : result.getData().entrySet()) {
				if(EnumAuthCode.getModuleByModuleNum(numEntry.getKey()) != null) {
					newMap.put(EnumAuthCode.getModuleByModuleNum(numEntry.getKey()), numEntry.getValue());
				}
			}

			return DefaultResult.successResult(newMap);
		} catch (Exception e) {
			e.printStackTrace();
			SysLogUtils.error("查询每天的转换记录失败，原因：", e);
			return DefaultResult.failResult("查询每日转换记录失败");
		}
	}


	/**
	 * 获取用户剩余的权益，包含：每日剩余次数和文件大小
	 * @param userID
	 * @return
	 */
	public IResult<Map<String,Object[]>> getAuth(Long userID){
		try {
			//获取用户剩余权益
			IResult<Map<String,Object>> result =  staticsManager.getAuth(userID);
			if(!result.isSuccess()){
				return DefaultResult.failResult(result.getMessage());
			}

			Map<String,Object[]> newMap = new HashMap<>();

			//转换成前端想要的字符
			for (Map.Entry<String, Object> numEntry : result.getData().entrySet()) {
				Object[] auth = new Object[2];
				if(EnumAuthCode.getModuleByModuleNum(numEntry.getKey()) != null) {
					auth[0] = numEntry.getValue();
					auth[1] = result.getData().get(EnumAuthCode.getModuleSizeByModuleNum(numEntry.getKey()));
					newMap.put(EnumAuthCode.getModuleByModuleNum(numEntry.getKey()), auth);
				}
			}

			return DefaultResult.successResult(newMap);
		} catch (Exception e) {
			e.printStackTrace();
			SysLogUtils.error("查询每天的转换记录失败，原因：", e);
			return DefaultResult.failResult("查询每日转换记录失败");
		}
	}


	/**
	 * 查询跳转优云文件夹的id
	 * @param cookie
	 * @return
	 */
	public IResult<String> findUCloudFolderId(String cookie){
		if(StringUtils.isBlank(cookie)) {
			return DefaultResult.failResult(EnumResultCode.E_UNLOGIN_ERROR.getInfo());
		}
		String url = ptsProperty.getYzcloud_domain() + YzcloudConsts.FOLDERID_INTERFACE;
		Map<String, Object> params = new HashMap<>();
		params.put("typeOfSource", "application.pdf");
		Map<String, Object> headers = new HashMap<>();
		headers.put(UaaConsts.COOKIE, cookie);
		IResult<HttpResultEntity> httpResult = httpAPIService.doGet(url, params, headers);
		if(HttpUtils.isHttpSuccess(httpResult)) {
			try {
				Map<String, Object> resultMap = JsonUtils.parseJSON2Map(httpResult.getData().getBody());
				if (!resultMap.isEmpty() && "0".equals(resultMap.get(YzcloudConsts.ERRORCODE).toString())) {
					Map<String, Object> result = (Map<String, Object>) resultMap.get(YzcloudConsts.RESULT);
					String fileId = result.get("fileId").toString();
					if (StringUtils.isNotBlank(fileId)) {
						return DefaultResult.successResult(fileId);
					}
				}
			} catch (Exception e) {
				return DefaultResult.failResult(EnumResultCode.E_GET_UCLOUD_ID_ERROR.getInfo());
			}
		}
		return DefaultResult.failResult(EnumResultCode.E_GET_UCLOUD_ID_ERROR.getInfo());

	}



	/**
	 * 根据fileHash查询转换结果
	 * @param fileHash
	 * @return
	 */
	public IResult<FcsFileInfoBO> getFileInfoByFileHash(String fileHash){
		String fileInfo = redisCacheManager.getFileInfo(fileHash);
		if(StringUtils.isBlank(fileInfo)) {
			return DefaultResult.failResult(EnumResultCode.E_BEING_CONVERT.getInfo());
		}
		FcsFileInfoBO fcsFileInfoBO = JsonUtils.json2obj(fileInfo, FcsFileInfoBO.class);
		
		
		return DefaultResult.successResult(fcsFileInfoBO);
	}


	/**
	 * 获取PDF工具集目前线上运行的模块
	 * @return
	 */
	public Map<String,Object> getPdfMudules(){
		Map<String,Object> moduleMap = new HashMap<>();
		String[] convertCode = configProperty.getConvertModule().split(SysConstant.COMMA);
		for(String code : convertCode) {
			String info = EnumAuthCode.getTypeInfo(Integer.valueOf(code));
			moduleMap.put(code, info);
		}
		return moduleMap;
	}


	/**
	 * 统计登录来源
	 * @param sourceId
	 * @param userId
	 * @return
	 */
	public IResult<String> statisticsRegister(String sourceId,Long userId){
		try {
			String opertime = DateViewUtils.getNowFull();
			String message = "sourceId="+sourceId+",userId="+userId+",opertime="+opertime;
			SysLogUtils.statisticsInfo(message);
			return DefaultResult.successResult();
		}catch (Exception e){
			SysLogUtils.error("统计注册来源失败，原因："+e);
			return DefaultResult.failResult("统计注册来源失败");
		}
	}


	/**
	 * 获取首页展示数量
	 * @return
	 */
	public IResult<Long> getShowNum(){
		try {
			Long num = ptsApplyPOMapper.selectCountOfPtsApply()+ PtsConsts.showNum;
			return DefaultResult.successResult(num);
		}catch (Exception e){
			SysLogUtils.error("查询上传文件总数失败，原因：",e);
			return DefaultResult.failResult();
		}
	}


}
