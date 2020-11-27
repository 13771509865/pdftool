package com.neo.service.features;

import com.neo.commons.cons.IResult;
import com.neo.commons.cons.entity.HttpResultEntity;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.model.bo.FeatureBo;
import com.neo.model.bo.FeatureDetailBo;
import com.neo.model.po.PtsAuthNamePO;
import com.neo.model.qo.PtsAuthNameQO;
import com.neo.service.authName.IAuthNameService;
import com.neo.service.httpclient.HttpAPIService;
import com.yozosoft.util.SecretSignatureUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author zhoufeng
 * @description 特性service
 * @create 2019-10-30 16:29
 **/
@Service("featureService")
public class FeatureService {

    @Autowired
    private HttpAPIService httpAPIService;

    @Autowired
    private IAuthNameService iAuthNameService;

    @Autowired
    private PtsProperty ptsProperty;

    /**
     * 初始化特性
     *
     * @author zhoufeng
     * @date 2019/10/30
     */
//    @PostConstruct
    public void initFeatures() {
        registerFeatures();
    }

    public void registerFeatures() {
        String nonce = UUID.randomUUID().toString().replace("-", "");
        String sign = "";
        try {
            sign = SecretSignatureUtils.hmacSHA256(nonce, ptsProperty.getProduct_hmac_key());
        } catch (Exception e) {
            SysLogUtils.error("商品注册生成签名失败");
        }
        String featuresJsonStr = getFeaturesJsonStr();
        String url = ptsProperty.getFeatures_insert_url() + "?nonce=" + nonce + "&sign=" + sign;
        IResult<HttpResultEntity> httpResult = httpAPIService.doPostByJson(url, featuresJsonStr);
        if (!HttpUtils.isHttpSuccess(httpResult)) {
            if (httpResult.getData() == null) {
                SysLogUtils.info("商品注册信息发送失败,消息体为:" + httpResult.getMessage());
            } else {
                SysLogUtils.info("商品注册信息发送失败,消息体为:" + httpResult.getData().getBody());
            }
        } else {
            SysLogUtils.info("商品注册信息发送成功!");
        }
    }

    private String getFeaturesJsonStr() {
        FeatureBo featureBo = new FeatureBo();
        featureBo.setType("PdfTools");
        featureBo.setName("PDF");
        featureBo.setComment("PDF 工具核心特性说明");
        featureBo.setVersion(ptsProperty.getFeatures_version());
        List<PtsAuthNamePO> ptsAuthNamePOS = iAuthNameService.selectPtsAuthNamePO(new PtsAuthNameQO());
        List<FeatureDetailBo> list = new ArrayList<>();
        if (!ptsAuthNamePOS.isEmpty()) {
            for (PtsAuthNamePO ptsAuthNamePO : ptsAuthNamePOS) {
                FeatureDetailBo featureDetailBo = buildFeatureDetailBo(ptsAuthNamePO);
                list.add(featureDetailBo);
            }
        }
        featureBo.setFeatures(list);
        return featureBo.toString();
    }

    private FeatureDetailBo buildFeatureDetailBo(PtsAuthNamePO ptsAuthNamePO) {
        FeatureDetailBo featureDetailBo = new FeatureDetailBo();
        if (ptsAuthNamePO != null) {
            featureDetailBo.setId(ptsAuthNamePO.getAuthCode());
            featureDetailBo.setName(ptsAuthNamePO.getAuthName());
            featureDetailBo.setDesc(ptsAuthNamePO.getDescription());
            featureDetailBo.setValueType(ptsAuthNamePO.getValueType());
            featureDetailBo.setMinValue(ptsAuthNamePO.getMinValue());
            featureDetailBo.setMaxValue(ptsAuthNamePO.getMaxValue());
            featureDetailBo.setValueUnit(ptsAuthNamePO.getValueUnit());
            featureDetailBo.setDefaultValue(ptsAuthNamePO.getDefaultVaule());
            featureDetailBo.setOptionals(JsonUtils.jsonToList(ptsAuthNamePO.getOptionals(), String.class));
        }
        return featureDetailBo;
    }
}
