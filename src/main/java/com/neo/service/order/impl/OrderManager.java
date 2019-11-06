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
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.EnumStatus;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.cons.entity.OrderSpecsEntity;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.DateViewUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.model.dto.RedisOrderDto;
import com.neo.model.po.PtsAuthNamePO;
import com.neo.model.po.PtsAuthPO;
import com.neo.model.qo.PtsAuthNameQO;
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
			for(OrderServiceAppSpec osa : dto.getOrderRequestDto().getSpecs()) {
				if(YozoServiceApp.PdfTools.getApp().equalsIgnoreCase(osa.getApp().getApp())) {

					OrderSpecsEntity orderSpecsEntity = getFeatureDetails(osa);
					if (orderSpecsEntity == null) {
						throw new RuntimeException();
					}
					//查看该用户是否购买过商品，并且锁表
					List<PtsAuthPO> list = iAuthService.selectAuthByUserid(dto.getUserId());
					PtsAuthPO ptsAuthPO = new PtsAuthPO();
					if(list.isEmpty() || list.size() < 1) {
						//当前时间+权益有效期
						Date expireDate = DateViewUtils.stepMonth(DateViewUtils.getNowDate(),orderSpecsEntity.getValidityTime());
						ptsAuthPO.setAuth(orderSpecsEntity.getAuth());
						ptsAuthPO.setGmtCreate(DateViewUtils.getNowDate());
						ptsAuthPO.setGmtModified(DateViewUtils.getNowDate());
						ptsAuthPO.setStatus(EnumStatus.ENABLE.getValue());
						ptsAuthPO.setUserid(dto.getUserId());
						ptsAuthPO.setGmtExpire(expireDate);
						boolean insertPtsAuthPO = iAuthService.insertPtsAuthPO(ptsAuthPO)>0;
						if(!insertPtsAuthPO) {
							SysLogUtils.error("插入用户商品权限失败");
							throw new RuntimeException();
						}
					}else {
						Date expireDate = list.get(0).getGmtExpire();
						Date newExpireDate;
						if(DateViewUtils.isExpiredForTimes(expireDate)) {//精确到秒
							//过期了：当前时间+权限给的月数
							newExpireDate = DateViewUtils.stepMonth(DateViewUtils.getNowDate(),orderSpecsEntity.getValidityTime());
						}else {
							//没过期：数据库时间+权限给的月数
							newExpireDate = DateViewUtils.stepMonth(expireDate,orderSpecsEntity.getValidityTime());
						}
						ptsAuthPO.setGmtModified(DateViewUtils.getNowDate());
						ptsAuthPO.setAuth(orderSpecsEntity.getAuth());
						ptsAuthPO.setUserid(dto.getUserId());
						ptsAuthPO.setGmtExpire(newExpireDate);
						boolean updatePtsAuthPOByUserId = iAuthService.updatePtsAuthPOByUserId(ptsAuthPO)>0;
						if(!updatePtsAuthPOByUserId) {
							SysLogUtils.error("修改用户商品权限失败");
							throw new RuntimeException();
						}
					}
				}
			}
			return DefaultResult.successResult();
		} catch (Exception e) {
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
			Integer validityTime = Integer.valueOf(specs.get(EnumAuthCode.PTS_VALIDITY_TIME.getAuthCode())[0]);
			for (Map.Entry<String, String[]> m : specs.entrySet()) {
				if(!StringUtils.equals(m.getKey(), EnumAuthCode.PTS_VALIDITY_TIME.getAuthCode())) {
					build.append(m.getKey()).append(SysConstant.COLON).append(m.getValue()[0]).append(SysConstant.COMMA);
				}
			}
			orderSpecsEntity = new OrderSpecsEntity(build.toString(), validityTime);
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
		StringBuilder build = new StringBuilder();
		String[] a = {"0"};

		Map<String, String[]> specs =  new HashMap<>();
		specs.put("connvrt", a);
		specs.put("validityTime", a);
		Integer validityTime = Integer.valueOf(specs.get(EnumAuthCode.PTS_VALIDITY_TIME.getAuthCode())[0]);
		for (Map.Entry<String, String[]> m : specs.entrySet()) {
			build.append(m.getKey()).append(SysConstant.COLON).append(m.getValue()[0]).append(SysConstant.COMMA);
		}
		System.out.println(validityTime);
		System.out.println(build.toString());

	}


}
