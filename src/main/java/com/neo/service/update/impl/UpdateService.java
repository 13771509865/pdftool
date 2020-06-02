package com.neo.service.update.impl;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.RedisConsts;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.dao.PtsAuthPOMapper;
import com.neo.model.po.PtsAuthPO;
import com.neo.model.qo.PtsAuthQO;
import com.neo.service.order.impl.OrderManager;
import com.neo.service.update.IUpdateService;
import com.yozosoft.api.order.dto.ServiceAppUserRightDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: xujun
 * @Date: 2020/6/1 5:18 下午
 */
@Service("updateService")
public class UpdateService implements IUpdateService {

    @Autowired
    private PtsAuthPOMapper ptsAuthPOMapper;

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private UpdateManager updateManager;


    /**
     * 更新用户auth数据
     * @return
     */
    public ResponseEntity authUpdate(ServiceAppUserRightDto serviceAppUserRightDto, long userId, String nonce, String sign){
        if (orderManager.checkSign(userId,nonce, sign).isSuccess()) {
            IResult<String> result = updateManager.updatePermissions(serviceAppUserRightDto,userId);
            if (result.isSuccess()) {
                return ResponseEntity.ok(EnumResultCode.E_SUCCES.getInfo());
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }




    /**
     * 根据userid查询auth信息
     * @param ptsAuthQO
     * @return
     */
    @Override
    public List<PtsAuthPO> selectAuth(PtsAuthQO ptsAuthQO){
        return ptsAuthPOMapper.selectAuth(ptsAuthQO);
    }


}
