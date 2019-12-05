package com.neo.service.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.EnumStatus;
import com.neo.commons.cons.IResult;
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
import com.neo.model.bo.FileUploadBO;
import com.neo.model.po.FcsFileInfoPO;
import com.neo.model.po.PtsSummaryPO;
import com.neo.model.qo.FcsFileInfoQO;
import com.neo.model.qo.PtsSummaryQO;
import com.neo.service.cache.impl.RedisCacheManager;
import com.neo.service.httpclient.HttpAPIService;

@Service("statisticsService")
public class StatisticsService {

	@Autowired
	private FcsFileInfoPOMapper fcsFileInfoPOMapper;

	@Autowired
	private PtsSummaryPOMapper ptsSummaryPOMapper;

	@Autowired
	private ConfigProperty config;

	@Autowired
	private RedisCacheManager<String> redisCacheManager;

	@Autowired
	private PtsProperty ptsProperty;

	@Autowired
	private HttpAPIService httpAPIService;

	/**
	 * 根据userID查询三天内的转换记录
	 * @param request
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
			Integer counNum = fcsFileInfoPOMapper.selectCountNumFcsFileInfoPOByUserID(fcsFileInfoQO);
			map.put(SysConstant.FCS_DATA, list);
			map.put(SysConstant.COUNT, counNum);
			return DefaultResult.successResult(map);
		} catch (Exception e) {
			SysLogUtils.error("查询三天内的转换记录失败，原因：", e);
			return DefaultResult.failResult("没有查询到三天内的转换记录");
		}
	}





	/**
	 * 查询剩余的转换次数
	 * @param request
	 * @return
	 */
	public IResult<String> getConvertTimes(String ipAddr,Long userID){
		try {
			String key = userID != null?RedisConsts.ID_CONVERT_TIME_KEY:RedisConsts.IP_CONVERT_TIME_KEY;
			String value = userID != null?String.valueOf(userID):ipAddr;
			Integer maxConvertTimes = userID != null?config.getMConvertTimes():config.getVConvertTimes();//登录用户20个

			int convertTimes = redisCacheManager.getScore(key,value).intValue();
			String otherTimes = String.valueOf(maxConvertTimes - convertTimes);
			return DefaultResult.successResult(otherTimes);

		} catch (Exception e) {
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








}
