package com.neo.service.update;

import com.neo.model.bo.PtsAuthNameBO;
import com.neo.model.po.PtsAuthPO;
import com.neo.model.qo.PtsAuthQO;
import com.yozosoft.api.order.dto.ServiceAppUserRightDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @Author: xujun
 * @Date: 2020/6/1 5:17 下午
 */
public interface IUpdateService {

    ResponseEntity authUpdate(ServiceAppUserRightDto serviceAppUserRightDto, long userId, String nonce, String sign);

    List<PtsAuthPO> selectAuth(PtsAuthQO ptsAuthQO);

    ResponseEntity authNameUpdate(PtsAuthNameBO ptsAuthNameBO, String flag);
}
