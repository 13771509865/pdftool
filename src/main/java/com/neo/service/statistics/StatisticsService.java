package com.neo.service.statistics;

import com.neo.commons.cons.*;
import com.neo.commons.cons.constants.RedisConsts;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.cons.constants.UaaConsts;
import com.neo.commons.cons.constants.YzcloudConsts;
import com.neo.commons.cons.entity.HttpResultEntity;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.dao.FcsFileInfoPOMapper;
import com.neo.dao.PtsSummaryPOMapper;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.model.bo.FileUploadBO;
import com.neo.model.po.FcsFileInfoPO;
import com.neo.model.po.PtsSummaryPO;
import com.neo.model.qo.FcsFileInfoQO;
import com.neo.model.qo.PtsSummaryQO;
import com.neo.service.auth.impl.AuthManager;
import com.neo.service.auth.impl.OldAuthManager;
import com.neo.service.cache.impl.RedisCacheManager;
import com.neo.service.convertRecord.IConvertRecordService;
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
	private PtsSummaryPOMapper ptsSummaryPOMapper;

	@Autowired
	private RedisCacheManager<String> redisCacheManager;

	@Autowired
	private PtsProperty ptsProperty;

	@Autowired
	private HttpAPIService httpAPIService;

	@Autowired
	private IConvertRecordService iConvertRecordService;
	
	@Autowired
	private AuthManager authManager;
	
	@Autowired
	private ConfigProperty configProperty;

	@Autowired
	private OldAuthManager oldAuthManager;

	@Autowired
	private StaticsManager staticsManager;


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



	/**==================================运营统计数据============================================ */	




	/**
	 * 查询每个文件大小区间的转换数量（包括失败的），缓存6小时
	 * @return
	 */
	//	@Cacheable(value = RedisConsts.CACHE_QUARTER_DAY, keyGenerator = "cacheKeyGenerator")
	public IResult<PtsSummaryPO> selectCountBySize(){
		try {
			PtsSummaryPO ptsSummaryPO = ptsSummaryPOMapper.selectCountBySize();
			if(ptsSummaryPO == null ) {
				return DefaultResult.failResult("查询转换记录失败");
			}
			return DefaultResult.successResult(ptsSummaryPO);
		} catch (Exception e) {
			SysLogUtils.error("查询转换数量失败，原因：", e);
			return DefaultResult.failResult("查询转换数量失败");
		}
	}



	/**
	 * 查询每个ip每天的转换量，缓存6小时
	 * @param request
	 * @return
	 */
	//	@Cacheable(value = RedisConsts.CACHE_QUARTER_DAY, keyGenerator = "cacheKeyGenerator")
	public IResult<List<PtsSummaryPO>> selectCountByIpAndDate(PtsSummaryQO ptsSummaryQO){
		try {
			List<PtsSummaryPO> list = ptsSummaryPOMapper.selectCountByIpAndDate(ptsSummaryQO);
			if(list.isEmpty() || list == null) {
				return DefaultResult.failResult("根据ip查询每天的转换数量失败");
			}
			return DefaultResult.successResult(list);
		} catch (Exception e) {
			SysLogUtils.error("根据ip查询每天的转换数量失败，原因：", e);
			return DefaultResult.failResult("根据ip查询每天的转换数量失败");
		}
	}




	/**
	 * 查询每天的转换量，缓存6小时
	 * @param request
	 * @return
	 */
	//	@Cacheable(value = RedisConsts.CACHE_QUARTER_DAY, keyGenerator = "cacheKeyGenerator")
	public IResult<List<PtsSummaryPO>> selectConvertByDay(PtsSummaryQO ptsSummaryQO){
		try {
			ptsSummaryQO.setGroupby("DATE");
			List<PtsSummaryPO> list = ptsSummaryPOMapper.selectCountByIpAndDate(ptsSummaryQO);
			if(list.isEmpty() || list == null) {
				return DefaultResult.failResult("查询每天的转换数量失败");
			}
			return DefaultResult.successResult(list);
		} catch (Exception e) {
			SysLogUtils.error("查询每天的转换数量失败，原因：", e);
			return DefaultResult.failResult("查询每天的转换数量失败");
		}
	}




	/**
	 * 查询上传文件的记录
	 * @return
	 */
	public IResult<FileUploadBO> getUploadTimes(){
		Integer successUpload = redisCacheManager.getScore(RedisConsts.UPLOAD_CONNT, RedisConsts.SUCCESS).intValue();
		Integer failUpload = redisCacheManager.getScore(RedisConsts.UPLOAD_CONNT, RedisConsts.FAIL).intValue();
		FileUploadBO fileUploadBO = new FileUploadBO();
		fileUploadBO.setFailNum(failUpload);
		fileUploadBO.setSuccessNum(successUpload);
		fileUploadBO.setCount(failUpload+successUpload);
		return DefaultResult.successResult(fileUploadBO);
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





}
