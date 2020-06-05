package com.neo.service.update.impl;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumAuthCode;
import com.neo.commons.cons.EnumStatus;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.util.SysLogUtils;
import com.neo.model.po.PtsAuthPO;
import com.neo.model.qo.PtsAuthQO;
import com.neo.service.auth.IAuthService;
import com.neo.service.auth.impl.AuthManager;
import com.neo.service.update.IUpdateService;
import com.yozosoft.api.order.dto.ServiceAppUserRightDto;
import com.yozosoft.api.order.dto.UserRightItem;
import com.yozosoft.saas.YozoServiceApp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
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
    private IAuthService iAuthService;

    @Autowired
    private IUpdateService iUpdateService;

    @Autowired
    private AuthManager authManager;

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

                //拿orderId
                List<PtsAuthPO> ptsAuthPOList = iUpdateService.selectAuth(new PtsAuthQO(userId,null,null));
                Long orderId = ptsAuthPOList.get(0).getOrderId();

                iAuthService.deletePtsAuth(userId);//删除当前用户所有权益

                Map<String,Object> convertAuthCodeMap =  authManager.getPermissionByConfig(new HashMap<>());//拿到所有模块的authcode

                List<PtsAuthPO> authList = new ArrayList<>();
                for(UserRightItem serRightItem : list) {

                    //不需要validityTime，convertNum，uploadSize这几个特性
                    if(!StringUtils.equals(serRightItem.getFeature(), EnumAuthCode.PTS_VALIDITY_TIME.getAuthCode())&&
                       !StringUtils.equals(serRightItem.getFeature(), EnumAuthCode.PTS_UPLOAD_SIZE.getAuthCode())&&
                       !StringUtils.equals(serRightItem.getFeature(), EnumAuthCode.PTS_CONVERT_NUM.getAuthCode())) {

                        //是老特性就拆分，新特性不需要拆
                        Map<String,String> map = getFeature(serRightItem.getFeature(),serRightItem.getSpecs()[0],convertAuthCodeMap);

                        for (Map.Entry<String, String> m : map.entrySet()) {
                            PtsAuthPO ptsAuthPO = new PtsAuthPO();
                            ptsAuthPO.setAuthCode(m.getKey());
                            ptsAuthPO.setAuthValue(m.getValue());
                            ptsAuthPO.setGmtCreate(serRightItem.getBegin());
                            ptsAuthPO.setGmtModified(serRightItem.getBegin());
                            ptsAuthPO.setGmtExpire(serRightItem.getEnd());
                            ptsAuthPO.setOrderId(orderId);
                            ptsAuthPO.setPriority(serRightItem.getPriority());
                            ptsAuthPO.setStatus(EnumStatus.ENABLE.getValue());
                            ptsAuthPO.setUserid(serRightItem.getUserId());
                            authList.add(ptsAuthPO);
                        }


                    }
                }

                Boolean insertPtsAuthPO = iAuthService.insertPtsAuthPO(authList)>0;
                if(!insertPtsAuthPO) {
                    SysLogUtils.error("更新用户权益失败，userId："+userId);
                    throw new RuntimeException();
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
