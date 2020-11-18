package com.neo.service.update.impl;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumAuthCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.util.SysLogUtils;
import com.neo.model.qo.PtsAuthCorpQO;
import com.neo.model.qo.PtsAuthQO;
import com.neo.service.authCorp.IAuthCorpService;
import com.neo.service.order.impl.OrderManager;
import com.neo.service.update.IUpdateService;
import com.yozosoft.api.order.dto.ServiceAppUserRightDto;
import com.yozosoft.api.order.dto.UserRightItem;
import com.yozosoft.saas.YozoServiceApp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: xujun
 * @Date: 2020/6/2 10:09 上午
 */
@Service("updateManager")
public class UpdateManager {

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private IUpdateService iUpdateService;

    @Autowired
    private IAuthCorpService iAuthCorpService;

    /**
     * 更新老用户权益
     * @param serviceAppUserRightDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public IResult<String> updatePermissions(ServiceAppUserRightDto serviceAppUserRightDto, Long userId){
        try {
            YozoServiceApp app = serviceAppUserRightDto.getApp();
            List<UserRightItem> list = serviceAppUserRightDto.getRights();
            if(app!=null && YozoServiceApp.PdfTools.getApp().equalsIgnoreCase(app.getApp()) && list.size()>0 && !list.isEmpty()) {
                Long corpId = list.get(0).getCorpId();
                //企业订单
                if (corpId != null){
                    PtsAuthCorpQO ptsAuthCorpQO = new PtsAuthCorpQO();
                    ptsAuthCorpQO.setCorpId(corpId);
                    Long orderId =  iAuthCorpService.selectAuthCorp(ptsAuthCorpQO).get(0).getOrderId();
                    IResult<String> updatePtsAuthCorp = orderManager.updatePtsAuthCorp(list,corpId,orderId);
                    if(!updatePtsAuthCorp.isSuccess()){
                        throw new RuntimeException();
                    }
                }else {
                    PtsAuthQO ptsAuthQO = new PtsAuthQO();
                    ptsAuthQO.setUserid(userId);
                    Long orderId = iUpdateService.selectAuth(ptsAuthQO).get(0).getOrderId();
                    IResult<String> updatePtsAuth = orderManager.updatePtsAuth(list,userId,orderId);
                    if(!updatePtsAuth.isSuccess()){
                        throw new RuntimeException();
                    }
                }
            }
            return DefaultResult.successResult();
        } catch (Exception e) {
            e.printStackTrace();
            SysLogUtils.error("用户更新权益失败,userId为:"+userId+"，权益更新对象："+serviceAppUserRightDto.toString(),e);
            //手动事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return DefaultResult.failResult();
        }
    }


    /**
     * 如果是新特性就不动，如果是老特性就要拆分成2个
     * @param feature
     * @return
     */
    private Map<String,String> getFeature(String feature,String space,Map<String,Object> convertAuthCodeMap){
        Map<String,String> map = new HashMap<>();
        Object authValue = convertAuthCodeMap.get(feature);
        if(authValue!=null && authValue!="" && StringUtils.equals(space, SysConstant.TRUE)){
            map.put(EnumAuthCode.getModuleNum(feature),"-1");
            map.put(EnumAuthCode.getModuleSize(feature),"-1");
        }else{
            map.put(feature,space);
        }
        return map;
    }



}
