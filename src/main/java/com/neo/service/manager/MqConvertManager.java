package com.neo.service.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.ResultCode;
import com.neo.commons.cons.constants.RedisConsts;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.cons.constants.TimeConsts;
import com.neo.commons.cons.entity.HttpResultEntity;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.service.cache.CacheManager;
import com.neo.service.cache.CacheService;
import com.neo.service.convert.PtsConvertParamService;
import com.neo.service.httpclient.HttpAPIService;

@Service("mqConvertManager")
public class MqConvertManager{

	@Autowired
	private CacheService<String> cacheService;
	
	@Autowired
	private ConfigProperty config;
	
	@Autowired
	private HttpAPIService httpAPIService;
	
	@Autowired
	private PtsConvertParamService ptsConvertParamService;
	
	@Autowired
	private PtsProperty ptsProperty;
	
	private ExecutorService pExecutorService;
	
	private List<ExecutorService> threadList;
	

	
	@PostConstruct 
	public void init() {
		pExecutorService = Executors.newFixedThreadPool(SysConstant.MQPOOLSIZE);
		
		this.threadList = new ArrayList<ExecutorService>();
		for(int i=0;i<config.getConvertPoolSize();i++) {
			ExecutorService executorService = Executors.newSingleThreadExecutor(); 
			threadList.add(executorService);
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println("启动转换线程监听线程");
					while(true) {
						Consumer();
					}
				}
			});
		}
	}
	
	@PreDestroy
	public void destroy() {
		if(pExecutorService!=null) { //关闭待转换队列线程
			pExecutorService.shutdown();
		}
		if(threadList!=null) { //关闭消费者线程
			for(ExecutorService executor:threadList) {
				if(executor!=null) {
					executor.shutdown();
				}
			}
		}
	}
	
	public void Producer(ConvertParameterBO convertBO) {
		pExecutorService.execute(new Runnable() {
			@Override
			public void run() {
				CacheManager<String> cacheManager = cacheService.getCacheManager();
				boolean pushResult = cacheManager.push(RedisConsts.MQ_WAIT_CONVERT, convertBO.toString());
				//TODO 塞进redis时候失败怎么办
				if(pushResult==false) {
					System.out.println("插入待转换队列失败");
					SysLogUtils.error("时间"+new Date()+"时,convertBO:"+convertBO.toString()+"塞入转换队列失败");
				}
			}
		});
	}
	
	public void Consumer() {
		try {
			String result;
			CacheManager<String> cacheManager = cacheService.getCacheManager();
			String json = cacheManager.pop(RedisConsts.MQ_WAIT_CONVERT);
			if(StringUtils.isEmpty(json)) {
//				System.out.println("消费队列无商品,等待中");
				Thread.sleep(TimeConsts.MQ_FREE_WAIT);
			}else {
				//TODO 记得到时候删除
				System.out.println("消费者为"+json);
				ConvertParameterBO convertBO = JsonUtils.json2obj(json, ConvertParameterBO.class);
				String url = String.format(ptsProperty.getFcs_convert_url());
				
				 IResult<HttpResultEntity> httpResult = httpAPIService.doPost(ptsProperty.getFcs_convert_url(), ptsConvertParamService.buildFcsMapParam(convertBO));
				 if (!HttpUtils.isHttpSuccess(httpResult)) {
		                System.out.println("fcs通讯失败");
		            }
				 
				Map<String, Object> httpResultMap = JsonUtils.parseJSON2Map(httpResult.getData().getBody());
				System.out.println("fcs转码结果："+httpResultMap);
				
				
				
//				convertParameterBOService.buildConvertParameterBO(convertBO);
//				IResult<ResultCode> checkResult = convertParameterBOService.checkParam(convertBO);
//				if(!checkResult.isSuccess()){
//					result = JsonResultUtils.buildJsonResult(checkResult.getData().getValue(), null, checkResult.getMessage());
//				}else {
//					IResult<FileInfoBO> convertResult = convertManager.dispatchConvert(convertBO);
//					if(convertResult.isSuccess()){
//						FileInfoBO fileInfoBO = convertResult.getData();
//						result = JsonResultUtils.success(fileInfoBO);
//						System.out.println(result);
//					}else{
//						result = JsonResultUtils.buildJsonResult(convertResult.getData().getCode(), null, convertResult.getMessage());
//					}
//				}
//				//往转换完成队列塞值
//				Boolean setHashValue = cacheManager.setHashValue(RedisConsts.MQ_RESULT_CONVERT, convertBO.getFileHash(), result);
//				if(setHashValue==false) {
//					//TODO redis塞值失败了怎么办
//					SysLogUtils.error("convertBO为"+convertBO+"插入转换完成队列失败");
//				}
			}
		}catch(Exception e) {
			SysLogUtils.error("MQ转换时抛出异常", e);
		}
	}
	

}
