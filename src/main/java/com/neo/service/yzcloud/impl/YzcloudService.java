package com.neo.service.yzcloud.impl;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.YzcloudConsts;
import com.neo.commons.cons.entity.HttpResultEntity;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.service.httpclient.HttpAPIService;
import com.neo.service.yzcloud.IYzcloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoufeng
 * @description
 * @create 2019-11-26 09:07
 **/
@Service("yzcloudService")
public class YzcloudService implements IYzcloudService {

    @Autowired
    private HttpAPIService httpAPIService;

    @Autowired
    private PtsProperty ptsProperty;

    @Override
    public IResult<String> uploadFileToYc(String targetRelativePath) {
        File targetFile = new File(ptsProperty.getFcs_targetfile_dir(), targetRelativePath);
        if (targetFile.isFile() && targetFile.exists()) {
            String url = ptsProperty.getYzcloud_domain() + YzcloudConsts.UPLOAD_INTERFACE;
            Map<String, Object> params = new HashMap<>();
            params.put("fileName", targetFile.getName());
            params.put("typeOfSource", "application.pdf");
            IResult<HttpResultEntity> httpResult = httpAPIService.uploadResouse(targetFile, url, params, null);
            if (HttpUtils.isHttpSuccess(httpResult)) {
                Map<String, Object> resultMap = JsonUtils.parseJSON2Map(httpResult.getData().getBody());
                if (!resultMap.isEmpty() && "0".equals(resultMap.get(YzcloudConsts.ERRORCODE).toString())) {
                    Map<String, Object> result = (Map<String, Object>) resultMap.get(YzcloudConsts.RESULT);
                    String fileId = result.get("fileId").toString();
                    return DefaultResult.successResult(fileId);
                }
            }
        }
        return DefaultResult.failResult("保存文件至优云失败");
    }
}
