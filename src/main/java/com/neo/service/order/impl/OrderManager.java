package com.neo.service.order.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	 * 生效订单
	 * @param dto
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public IResult<String> modifyOrderEffective(RedisOrderDto dto){
		try {
			//商品id、优先级、是否会员升级
			String productId = dto.getOrderRequestDto().getProductId();
			Integer priority = dto.getOrderRequestDto().getPriority();
			Boolean upgrade = dto.getOrderRequestDto().getUpgrade();

			for(OrderServiceAppSpec osa : dto.getOrderRequestDto().getSpecs()) {

				//获取pdf的会员权益
				if(YozoServiceApp.PdfTools.getApp().equalsIgnoreCase(osa.getApp().getApp())) {
					OrderSpecsEntity orderSpecsEntity = getFeatureDetails(osa);
					if (orderSpecsEntity == null) {
						throw new RuntimeException();
					}
					//查看该用户是否购买过商品 && status=1，并且锁表
					List<PtsAuthPO> list = iAuthService.selectPtsAuthPO(new PtsAuthQO(dto.getUserId(),EnumStatus.ENABLE.getValue(), EnumLockCode.LOCK.getValue()));

					//list空表示首次购买，直接insert
					if(list.isEmpty() || list.size() < 1) {
						if(iAuthService.insertPtsAuthPO(orderSpecsEntity,dto.getUserId(),productId,priority)) {
							SysLogUtils.error("用户首次购买会员，插入数据库失败："+dto.toString());
							throw new RuntimeException();
						}
						return DefaultResult.successResult();
					}

					//1,升级
					//升级就随便改第一条数据，其他数据改status=0禁用
					if(upgrade) {
						for(int i = 0 ; i<list.size(); i++) {
							Date newExpireDate = i > 0?null:DateViewUtils.getTimeDay(DateViewUtils.getNowDate(),orderSpecsEntity.getValidityTime(),orderSpecsEntity.getUnitType());
							Integer status =i > 0?EnumStatus.DISABLE.getValue():EnumStatus.ENABLE.getValue();
							String auth = i > 0?null:orderSpecsEntity.getAuth();
							priority = i > 0?null:priority;
							productId =  i > 0?list.get(i).getProductId():productId;
							
							if(!iAuthService.updatePtsAuthPO(auth,dto.getUserId(),productId,newExpireDate,status,priority)) {
								SysLogUtils.error("用户会员升级，更新数据库失败："+dto.toString());
								throw new RuntimeException();	
							}
						}
						return DefaultResult.successResult();
					}

					//2,购买或者续费
					Boolean exitProductId = false;//是否存在productId
					Boolean isExpired = true;//是否过期
					Date gmtExpire = null;
					for(PtsAuthPO ptsAuthPO : list) {
						if(StringUtils.equals(ptsAuthPO.getProductId(), productId)) {
							exitProductId = true;//productId存在
							if(!DateViewUtils.isExpiredForTimes(ptsAuthPO.getGmtExpire())) {
								isExpired = false;//没过期
								gmtExpire = ptsAuthPO.getGmtExpire();
							}
						}
					}

					//productId不存在就insert
					if(!exitProductId) {
						if(iAuthService.insertPtsAuthPO(orderSpecsEntity,dto.getUserId(),productId,priority)) {
							SysLogUtils.error("用户再次购买，插入数据库失败："+dto.toString());
							throw new RuntimeException();
						}
					}else {
						gmtExpire = DateViewUtils.getTimeDay(isExpired?DateViewUtils.getNowDate():gmtExpire,orderSpecsEntity.getValidityTime(),orderSpecsEntity.getUnitType());
						Boolean updatePtsAuthPO =  iAuthService.updatePtsAuthPO(orderSpecsEntity.getAuth(),dto.getUserId(),productId,gmtExpire,EnumStatus.ENABLE.getValue(),priority);
						if(!updatePtsAuthPO) {
							SysLogUtils.error("用户会员续费或者再次购买，更新数据库失败："+dto.toString());
							throw new RuntimeException();	
						}
					}
					//没有过期的低优先级的改时间，数据库时间+购买时间
					iAuthService.updatePtsAuthPOByPriority(orderSpecsEntity.getValidityTime(),orderSpecsEntity.getUnitType().toString(), dto.getUserId(), priority);
				}
			}
			return DefaultResult.successResult();
		} catch (Exception e) {
			e.printStackTrace();
			SysLogUtils.error("订单域名生效失败,订单为:"+JSON.toJSONString(dto),e);
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


	public static void main(String[] args) {
	}


}
