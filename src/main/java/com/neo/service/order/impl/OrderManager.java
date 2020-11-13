package com.neo.service.order.impl;

import com.neo.commons.cons.*;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.SysLogUtils;
import com.neo.model.dto.RedisOrderDto;
import com.neo.model.po.PtsAuthCorpPO;
import com.neo.model.po.PtsAuthPO;
import com.neo.service.auth.IAuthService;
import com.neo.service.authCorp.IAuthCorpService;
import com.yozosoft.api.order.dto.OrderRequestDto;
import com.yozosoft.api.order.dto.OrderServiceAppSpec;
import com.yozosoft.api.order.dto.ServiceAppUserRightDto;
import com.yozosoft.api.order.dto.UserRightItem;
import com.yozosoft.saas.YozoServiceApp;
import com.yozosoft.util.SecretSignatureUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderManager {


    @Autowired
    private PtsProperty ptsProperty;

    @Autowired
    private IAuthService iAuthService;

    @Autowired
	private IAuthCorpService iAuthCorpService;


    /**
     * 根据OrderRequestDto校验签名
     *
     * @param dto
     * @param nonce
     * @param sign
     * @return
     */
    public IResult<String> checkSign(OrderRequestDto dto, String nonce, String sign) {
        StringBuilder rawBuilder = new StringBuilder();
        rawBuilder.append(dto.getProductId()).append(dto.getInfo()).append(nonce);
        IResult<String> checkResult = check(rawBuilder.toString(), sign);
        List<OrderServiceAppSpec> list = dto.getSpecs();
        if (!checkResult.isSuccess() || list.isEmpty()) {
            return DefaultResult.failResult(EnumResultCode.E_ORDER_ILLEGAL.getInfo());
        }
        return DefaultResult.successResult();
    }


    /**
     * 根据uri校验签名
     *
     * @param uri
     * @param nonce
     * @param sign
     * @return
     */
    public IResult<String> checkSign(String uri, String nonce, String sign) {
        StringBuilder rawBuilder = new StringBuilder();
        rawBuilder.append(uri).append(nonce);
        IResult<String> checkResult = check(rawBuilder.toString(), sign);
        return checkResult;
    }


    /**
     * 根据userId检验签名
     *
     * @param userId
     * @param nonce
     * @param sign
     * @return
     */
    public IResult<String> checkSign(Long userId, String nonce, String sign) {
        StringBuilder rawBuilder = new StringBuilder();
        rawBuilder.append(userId).append(nonce);
        IResult<String> checkResult = check(rawBuilder.toString(), sign);
        return checkResult;
    }


    /**
     * 用户生效订单
     * @param serviceAppUserRightDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public IResult<String> modifyOrderEffective(ServiceAppUserRightDto serviceAppUserRightDto, Long orderId, RedisOrderDto dto) {
        try {
            YozoServiceApp app = serviceAppUserRightDto.getApp();
            List<UserRightItem> list = serviceAppUserRightDto.getRights();
            if (app != null && YozoServiceApp.PdfTools.getApp().equalsIgnoreCase(app.getApp()) && list.size() > 0 && !list.isEmpty()) {
                //corpId不为空则是企业订阅
                if (dto.getOrderRequestDto().getCorpId() != null) {
					IResult<String> updatePtsAuthCorp = updatePtsAuthCorp(list,dto.getOrderRequestDto().getCorpId(),orderId);
					if(!updatePtsAuthCorp.isSuccess()){
						throw new RuntimeException();
					}
                } else {
                    IResult<String> updatePtsAuth = updatePtsAuth(list, dto.getUserId(), orderId);
                    if(!updatePtsAuth.isSuccess()) {
                        throw new RuntimeException();
                    }
                }
            }
            return DefaultResult.successResult();
        } catch (Exception e) {
            e.printStackTrace();
            SysLogUtils.error("订单域名生效失败,订单为:" + orderId + "，订单对象：" + serviceAppUserRightDto.toString(), e);
            //手动事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return DefaultResult.failResult();
        }
    }


    /**
     * 更新个人用户权益
     * @return
     */
    private IResult<String> updatePtsAuth(List<UserRightItem> list, Long userId, Long orderId) {
        Integer deletePtsAuthNum = iAuthService.deletePtsAuth(userId);
        SysLogUtils.info("删除个人用户权益一共：" + deletePtsAuthNum + "条，userId为：" + userId);
        List<PtsAuthPO> authList = new ArrayList<>();
        for (UserRightItem serRightItem : list) {
            if (!StringUtils.equals(serRightItem.getFeature(), EnumAuthCode.PTS_VALIDITY_TIME.getAuthCode())) {
                PtsAuthPO ptsAuthPO = new PtsAuthPO();
                ptsAuthPO.setAuthCode(serRightItem.getFeature());
                ptsAuthPO.setAuthValue(serRightItem.getSpecs()[0]);
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
        if (!authList.isEmpty()) {
            Boolean insertPtsAuthPO = iAuthService.insertPtsAuthPO(authList) > 0;
            if (!insertPtsAuthPO) {
                SysLogUtils.error("插入用户权益失败，orderId：" + orderId);
                return DefaultResult.failResult();
            }
        }
        return DefaultResult.successResult();
    }


	/**
	 * 更新企业账号权益
	 * @param list
	 * @param corpId
	 * @param orderId
	 * @return
	 */
	private IResult<String> updatePtsAuthCorp(List<UserRightItem> list, Long corpId, Long orderId){
		Integer deletePtsAuthCorpNum = iAuthCorpService.deletePtsAuthCorp(corpId);
		SysLogUtils.info("删除企业账号权益一共：" + deletePtsAuthCorpNum + "条，corpId为：" + corpId);
		List<PtsAuthCorpPO> authCorpList = new ArrayList<>();
		for (UserRightItem serRightItem : list) {
			if (!StringUtils.equals(serRightItem.getFeature(), EnumAuthCode.PTS_VALIDITY_TIME.getAuthCode())) {
				PtsAuthCorpPO ptsAuthCorpPO = new PtsAuthCorpPO();
				ptsAuthCorpPO.setAuthCode(serRightItem.getFeature());
				ptsAuthCorpPO.setAuthValue(serRightItem.getSpecs()[0]);
				ptsAuthCorpPO.setGmtCreate(serRightItem.getBegin());
				ptsAuthCorpPO.setGmtModified(serRightItem.getBegin());
				ptsAuthCorpPO.setGmtExpire(serRightItem.getEnd());
				ptsAuthCorpPO.setOrderId(orderId);
				ptsAuthCorpPO.setPriority(serRightItem.getPriority());
				ptsAuthCorpPO.setStatus(EnumStatus.ENABLE.getValue());
				ptsAuthCorpPO.setCorpId(corpId);
				authCorpList.add(ptsAuthCorpPO);
			}
		}
		if (!authCorpList.isEmpty()) {
			Boolean insertPtsAuthCorpPO = iAuthCorpService.insertPtsAuthCorpPO(authCorpList) > 0;
			if (!insertPtsAuthCorpPO) {
				SysLogUtils.error("插入用户权益失败，orderId：" + orderId);
				return DefaultResult.failResult();
			}
		}
		return DefaultResult.successResult();
	}




    private IResult<String> check(String str, String sign) {
        try {
            String signature = SecretSignatureUtils.hmacSHA256(str, ptsProperty.getProduct_hmac_key());
            if (!sign.equals(signature)) {
                return DefaultResult.failResult(EnumResultCode.E_ORDER_ILLEGAL.getInfo());
            }
            return DefaultResult.successResult();
        } catch (Exception e) {
            e.printStackTrace();
            return DefaultResult.failResult(EnumResultCode.E_ORDER_ILLEGAL.getInfo());
        }
    }


    public static void main(String[] args) throws Exception {
    }


}
