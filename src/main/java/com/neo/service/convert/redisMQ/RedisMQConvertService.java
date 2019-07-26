package com.neo.service.convert.redisMQ;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.RedisConsts;
import com.neo.commons.cons.constants.TimeConsts;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.util.JsonResultUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.model.po.FcsFileInfoPO;
import com.neo.service.cache.CacheManager;
import com.neo.service.cache.CacheService;
import com.neo.service.convert.PtsConvertService;


@Service("redisMQConvertService")
public class RedisMQConvertService {

	@Autowired
	private CacheService<String> cacheService;

	@Autowired
	private ConfigProperty configProperty;

	@Autowired
	private PtsConvertService PtsConvertService;

	private ExecutorService pExecutorService;

	private List<ExecutorService> threadList;


	public IResult<String> Producer(ConvertParameterBO convertBO) {
		try {
			pExecutorService.execute(new Runnable() {
				@Override
				public void run() {
					CacheManager<String> cacheManager = cacheService.getCacheManager();
					boolean pushResult = cacheManager.push(RedisConsts.MQ_WAIT_CONVERT, convertBO.toString());
					//TODO 塞进redis时候失败怎么办
					if(!pushResult) {
						System.out.println("插入待转换队列失败");
						SysLogUtils.error("时间"+new Date()+"时,convertBO:"+convertBO.toString()+"塞入转换队列失败");
					}
				}
			});
			return DefaultResult.successResult();
		} catch (Exception e) {
			e.printStackTrace();
			return DefaultResult.failResult("插入待转换队列失败");
		}
		
	}

	public void Consumer() {
		try {
			String result;
			CacheManager<String> cacheManager = cacheService.getCacheManager();
			String json = cacheManager.pop(RedisConsts.MQ_WAIT_CONVERT);//拿带转换的消息
			if(StringUtils.isEmpty(json)) {
			  //System.out.println("消费队列无商品,等待中");
				Thread.sleep(TimeConsts.MQ_FREE_WAIT);
			}else {
				System.out.println("消费者为"+json);
				ConvertParameterBO convertBO = JsonUtils.json2obj(json, ConvertParameterBO.class);
				HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
				IResult<FcsFileInfoBO> convertResult = PtsConvertService.dispatchConvert(convertBO, -1,request);
				if(convertResult.isSuccess()){
					FcsFileInfoBO fcsFileInfoBO = convertResult.getData();
					result = JsonResultUtils.success(fcsFileInfoBO);
					System.out.println("消息队列fcs转换成功了"+result);
				}else{
					result = JsonResultUtils.buildJsonResult(convertResult.getData().getCode(), null, convertResult.getMessage());
				}

				//不管转换成功或者失败，把转换结果塞入result队列
				Boolean setHashValue = cacheManager.setHashValue(RedisConsts.MQ_RESULT_CONVERT, convertBO.getFileHash(), result);

				if(setHashValue==false) {//TODO redis塞值失败了怎么办
					SysLogUtils.error("convertBO为"+convertBO+"插入转换完成队列失败");
				}
			}

		}catch(Exception e) {
			SysLogUtils.error("MQ转换时抛出异常", e);
		}
	}





	@PostConstruct 
	public void init() {
		pExecutorService = Executors.newFixedThreadPool(configProperty.getConvertPoolSize());
		this.threadList = new ArrayList<ExecutorService>();
		for(int i=0;i<configProperty.getConvertPoolSize();i++) {
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



}
