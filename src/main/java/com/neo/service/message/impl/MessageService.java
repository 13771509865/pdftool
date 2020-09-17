package com.neo.service.message.impl;

import com.neo.commons.cons.IResult;
import com.neo.commons.cons.entity.HttpResultEntity;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.SysLogUtils;
import com.neo.commons.util.UUIDHelper;
import com.neo.model.bo.MessageTemplateBO;
import com.neo.service.httpclient.HttpAPIService;
import com.neo.service.message.IMessageService;
import com.yozosoft.auth.client.security.UaaToken;
import com.yozosoft.util.SecretSignatureUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xujun
 * @Date: 2020/9/15 1:39 下午
 */
@Service("messageService")
public class MessageService implements IMessageService {

    @Autowired
    private PtsProperty ptsProperty;

    @Autowired
    private HttpAPIService httpAPIService;

    /**
     * 转换成功后，给auth发送消息模版
     * @param uaaToken
     * @return
     */
    public void sendMessageTemplate(UaaToken uaaToken){
        List<String> users = new ArrayList();
        users.add(String.valueOf(uaaToken.getUserId()));
        String nonce = UUIDHelper.generateUUID();
        try{
            String sign = SecretSignatureUtils.hmacSHA256(nonce, ptsProperty.getMember_hamc_key());
            MessageTemplateBO messageTemplateBO =  MessageTemplateBO.builder()
                    .templateId("52")//52对应大后台PDF推送的ID
                    .userIds(users)
                    .nonce(nonce)
                    .sign(sign).build();
            IResult<HttpResultEntity> result = httpAPIService.doPostByJson(ptsProperty.getMessage_url(),messageTemplateBO.toString());
            SysLogUtils.info(uaaToken.getUserId()+":发送消息模版结果为："+result.getData().getBody());
        }catch (Exception e){
            SysLogUtils.error("发送消息模版失败", e);
        }
    }


}
