package com.neo.service.message;

import com.yozosoft.auth.client.security.UaaToken;

/**
 * @Author: xujun
 * @Date: 2020/9/15 1:38 下午
 */
public interface IMessageService {

    void sendMessageTemplate(UaaToken uaaToken);
}
