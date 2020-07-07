package com.neo.service.update.impl;

import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.EnumStatus;
import com.neo.commons.cons.IResult;
import com.neo.commons.util.DateViewUtils;
import com.neo.dao.PtsAuthPOMapper;
import com.neo.model.bo.PtsAuthNameBO;
import com.neo.model.po.PtsAuthNamePO;
import com.neo.model.po.PtsAuthPO;
import com.neo.model.qo.PtsAuthQO;
import com.neo.service.authName.IAuthNameService;
import com.neo.service.order.impl.OrderManager;
import com.neo.service.update.IUpdateService;
import com.yozosoft.api.order.dto.ServiceAppUserRightDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    @Autowired
    private IAuthNameService iAuthNameService;


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
     * 更新authName表数据
     * @param ptsAuthNameBO
     * @param flag
     * @return
     */
    public ResponseEntity authNameUpdate(PtsAuthNameBO ptsAuthNameBO, String flag){
        Boolean isSuccess = false;
        if(StringUtils.equals(flag,"insert")){
            PtsAuthNamePO ptsAuthNamePO =  PtsAuthNamePO.builder()
                    .gmtCreate(DateViewUtils.getNowDate())
                    .gmtModified(DateViewUtils.getNowDate())
                    .status(EnumStatus.ENABLE.getValue())
                    .authCode(ptsAuthNameBO.getAuthCode())
                    .authName(ptsAuthNameBO.getAuthName())
                    .defaultVaule(ptsAuthNameBO.getDefaultVaule())
                    .description(ptsAuthNameBO.getDescription())
                    .valueType(ptsAuthNameBO.getValueType())
                    .valueUnit(ptsAuthNameBO.getValueUnit()).build();
            isSuccess =  iAuthNameService.insertPtsAuthNamePO(ptsAuthNamePO)>0;
        }
        return ResponseEntity.ok(isSuccess?EnumResultCode.E_SUCCES.getInfo():EnumResultCode.E_FAIL.getInfo());
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
