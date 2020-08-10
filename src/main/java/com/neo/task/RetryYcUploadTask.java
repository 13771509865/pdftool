package com.neo.task;

import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.RedisConsts;
import com.neo.commons.cons.constants.TimeConsts;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.DateViewUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.service.cache.CacheManager;
import com.neo.service.convert.PtsYcUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时重试YcUpload记录
 * @author miaowei
 * @create 2019-12-03
 */
@Component
public class RetryYcUploadTask {

    @Autowired
    private PtsYcUploadService ptsYcUploadService;

    @Autowired
    private PtsProperty ptsProperty;

    @Autowired
    private CacheManager cacheManager;


    /**
     * 每个小时重试一次
     * 上传失败的文件24小时内重试到成功，超过24小时则不再重试
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void retryYcUpload(){
        if(cacheManager.setnx(RedisConsts.RETRY_YC_KEY, RedisConsts.RETRY_YC_KEY+"___"+DateViewUtils.getNowFull(), TimeConsts.SECOND_OF_HALFHOUR)) {
            if ("true".equals(ptsProperty.getRetryFlag())) {
                SysLogUtils.info("=======================================开始重试YcUpload=======================================");
                IResult<String> result = ptsYcUploadService.retryYCUpload();
            }
        }
    }
}
