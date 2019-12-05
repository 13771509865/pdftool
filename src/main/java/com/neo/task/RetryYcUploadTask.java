package com.neo.task;

import com.neo.commons.cons.IResult;
import com.neo.commons.util.SysLogUtils;
import com.neo.model.qo.PtsYcUploadQO;
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

    @Scheduled(cron = "0 0 * * * ?")
    public void retryYcUpload(){
        SysLogUtils.info("=======================================开始重试YcUpload=======================================");
        IResult<String> result = ptsYcUploadService.retryYCUpload();
        SysLogUtils.info("-------------------------------"+result.getMessage());
        SysLogUtils.info("=======================================结束重试YcUpload=======================================");
        SysLogUtils.info("=======================================开始更新10小时内未重试成功YcUpload记录的状态为不重试=======================================");
        PtsYcUploadQO ptsYcUploadQO = new PtsYcUploadQO();
        ptsYcUploadQO.setStatus(2);
        ptsYcUploadQO.setFlag("del");
        IResult<String> uResult = ptsYcUploadService.updatePtsYcUpload(ptsYcUploadQO);
        SysLogUtils.info("=======================================结束更新10小时内未重试成功YcUpload记录的状态为不重试=======================================");
    }
}
