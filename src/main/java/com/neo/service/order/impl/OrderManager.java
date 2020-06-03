package com.neo.service.order.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.neo.commons.util.UUIDHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;
import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumAuthCode;
import com.neo.commons.cons.EnumLockCode;
import com.neo.commons.cons.EnumMemberType;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.EnumStatus;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.UnitType;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.cons.entity.OrderSpecsEntity;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.DateViewUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.model.dto.RedisOrderDto;
import com.neo.model.po.PtsAuthNamePO;
import com.neo.model.po.PtsAuthPO;
import com.neo.model.qo.PtsAuthNameQO;
import com.neo.model.qo.PtsAuthQO;
import com.neo.service.auth.IAuthService;
import com.yozosoft.api.order.dto.OrderRequestDto;
import com.yozosoft.api.order.dto.OrderServiceAppSpec;
import com.yozosoft.api.order.dto.ServiceAppUserRightDto;
import com.yozosoft.api.order.dto.UserRightItem;
import com.yozosoft.saas.YozoServiceApp;
import com.yozosoft.util.SecretSignatureUtils;

@Service
public class OrderManager {


	@Autowired
	private PtsProperty ptsProperty;

	@Autowired
	private IAuthService iAuthService;


	/**
	 * 根据OrderRequestDto校验签名
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
	 * 生效订单
	 * @param dto
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public IResult<String> modifyOrderEffective(ServiceAppUserRightDto serviceAppUserRightDto,Long orderId,Long userId){
		try {
			
			YozoServiceApp app = serviceAppUserRightDto.getApp();
			List<UserRightItem> list = serviceAppUserRightDto.getRights();
			if(app!=null && YozoServiceApp.PdfTools.getApp().equalsIgnoreCase(app.getApp()) && list.size()>0 && !list.isEmpty()) {
				
				iAuthService.deletePtsAuth(userId);//删除当前用户所有权益
				
				List<PtsAuthPO> authList = new ArrayList<>();
				for(UserRightItem serRightItem : list) {
					if(!StringUtils.equals(serRightItem.getFeature(), EnumAuthCode.PTS_VALIDITY_TIME.getAuthCode())) {
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
				
				Boolean insertPtsAuthPO = iAuthService.insertPtsAuthPO(authList)>0;
				if(!insertPtsAuthPO) {
					SysLogUtils.error("插入用户权益失败，orderId："+orderId);
					throw new RuntimeException();
				}
			}
			
			return DefaultResult.successResult();
		} catch (Exception e) {
			e.printStackTrace();
			SysLogUtils.error("订单域名生效失败,订单为:"+orderId+"，订单对象："+serviceAppUserRightDto.toString(),e);
			//手动事务回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return DefaultResult.failResult();
		}
	}




	private OrderSpecsEntity getFeatureDetails(OrderServiceAppSpec osa) {
		OrderSpecsEntity orderSpecsEntity = null;
		try {
			StringBuilder build = new StringBuilder();
			Map<String, String[]> specs = osa.getSpecs();

			//过期时间值
			Integer validityTime = Integer.valueOf(specs.get(EnumAuthCode.PTS_VALIDITY_TIME.getAuthCode())[0]);
			//过期时间单位
			UnitType unitType = UnitType.getUnit(specs.get(EnumAuthCode.PTS_VALIDITY_TIME.getAuthCode())[1]);

			for (Map.Entry<String, String[]> m : specs.entrySet()) {
				if(!StringUtils.equals(m.getKey(), EnumAuthCode.PTS_VALIDITY_TIME.getAuthCode())) {
					build.append(m.getKey()).append(SysConstant.COLON).append(m.getValue()[0]).append(SysConstant.COMMA);
				}
			}
			orderSpecsEntity = new OrderSpecsEntity(build.toString(), validityTime,unitType);
		} catch (Exception e) {
			SysLogUtils.error("解析order商品详情错误，原因："+e.getMessage());
		}
		return orderSpecsEntity;
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
