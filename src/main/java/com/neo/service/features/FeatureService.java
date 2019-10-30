package com.neo.service.features;

import com.neo.service.httpclient.HttpAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author zhoufeng
 * @description 特性service
 * @create 2019-10-30 16:29
 **/
@Service("featureService")
public class FeatureService {

    @Autowired
    private HttpAPIService httpAPIService;

    /**
     * 初始化特性
     * @author zhoufeng
     * @date 2019/10/30
     */
    @PostConstruct
    public void initFeatures(){
        //TODO 没写呢
    }



}
