package com.neo.task;

import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.RedisConsts;
import com.neo.commons.cons.constants.TimeConsts;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.DateViewUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.model.qo.PtsYcUploadQO;
import com.neo.service.cache.CacheManager;
import com.neo.service.convert.PtsYcUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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

    @Scheduled(cron = "0 0 * * * ?")
    public void retryYcUpload(){
        if(cacheManager.setScheduler(RedisConsts.RETRY_YC_KEY, RedisConsts.RETRY_YC_KEY+"___"+DateViewUtils.getNowFull(), TimeConsts.SECOND_OF_HALFHOUR)) {
            if ("true".equals(ptsProperty.getRetryFlag())) {
                SysLogUtils.info("=======================================开始重试YcUpload=======================================");
                IResult<String> result = ptsYcUploadService.retryYCUpload();
                SysLogUtils.info("-------------------------------" + result.getMessage());
                SysLogUtils.info("=======================================结束重试YcUpload=======================================");
                SysLogUtils.info("=======================================开始更新24小时内未重试成功YcUpload记录的状态为不重试=======================================");
                PtsYcUploadQO ptsYcUploadQO = new PtsYcUploadQO();
                ptsYcUploadQO.setStatus(2);
                ptsYcUploadQO.setFlag("del");
                IResult<String> uResult = ptsYcUploadService.updatePtsYcUpload(ptsYcUploadQO);
                SysLogUtils.info("=======================================结束更新24小时内未重试成功YcUpload记录的状态为不重试=======================================");
            }
        }
    }
}
