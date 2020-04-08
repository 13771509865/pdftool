package com.neo.service.order.impl;

import java.time.OffsetDateTime;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumAuthCode;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.UnitType;
import com.neo.commons.cons.constants.RedisConsts;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.cons.constants.TimeConsts;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.DateViewUtils;
import com.neo.model.dto.RedisOrderDto;
import com.neo.model.po.PtsAuthNamePO;
import com.neo.model.qo.PtsAuthNameQO;
import com.neo.service.authName.IAuthNameService;
import com.neo.service.cache.impl.RedisCacheManager;
import com.neo.service.order.IOrderService;
import com.yozosoft.api.order.dto.OrderRequestDto;
import com.yozosoft.api.order.dto.OrderServiceAppSpec;
import com.yozosoft.api.tcc.Participant;
import com.yozosoft.api.tcc.TccStatus;
import com.yozosoft.saas.YozoServiceApp;

@Service
public class OrderService implements IOrderService{


	@Autowired
	private OrderManager orderManager;

	@Autowired
	private RedisCacheManager<RedisOrderDto> redisCacheManager;

	@Autowired
	private IAuthNameService iAuthNameService;

	@Autowired
	private PtsProperty ptsProperty;

	/**
	 * 资源预留接口
	 * @param userId
	 * @param orderId
	 * @param dto
	 * @param nonce
	 * @param sign
	 * @return
	 */
	@Override
	public IResult<Participant> reserve(long userId, long orderId, OrderRequestDto dto, String nonce, String sign) {
		Participant participant = new Participant();
		try {
			
			//判断订单是否是已经确认过的订单
			if(redisCacheManager.orderExists(RedisConsts.ORDERIDMP + orderId)) {
				return buildFailResult(participant, EnumResultCode.E_ORDER_ILLEGAL.getInfo(), HttpStatus.FORBIDDEN);
			}
			
			IResult<String> checkSignResult = orderManager.checkSign(dto, nonce, sign);
			if (!checkSignResult.isSuccess()) {
				return buildFailResult(participant, EnumResultCode.E_ORDER_ILLEGAL.getInfo(), HttpStatus.FORBIDDEN);
			}

			//1,遍历所有服务，注册的商品特性
			//2,看有没有pdf的商品特性，没有就是非法
			//3,把pdf所有的特性都遍历，查询数据库里面有没有该特性，没有就是非法
			boolean errorFlag = true;//默认非法

			for (OrderServiceAppSpec osa : dto.getSpecs()) {
				if(YozoServiceApp.PdfTools.getApp().equalsIgnoreCase(osa.getApp().getApp())) {
					boolean flag = true;//默认合法
					if(osa.getSpecs().isEmpty()) {
						flag = false;//如果map为空，改成不合法
					}
					
					//有效期的值
					Object validityTime = osa.getSpecs().get(EnumAuthCode.PTS_VALIDITY_TIME.getAuthCode())[0];
					//有效期的单位
					Object unitType = osa.getSpecs().get(EnumAuthCode.PTS_VALIDITY_TIME.getAuthCode())[1];
					//转换次数
					Object num = osa.getSpecs().get(EnumAuthCode.PTS_CONVERT_NUM.getAuthCode())[0];
					//转换大小
					Object size = osa.getSpecs().get(EnumAuthCode.PTS_UPLOAD_SIZE.getAuthCode())[0];
					
					if(num ==null || size==null ||validityTime == null || unitType==null || Integer.valueOf(validityTime.toString()) <=0 || UnitType.getUnit(unitType.toString()) == null) {
						flag = false;//有效期、单位、次数、大小为空，改成不合法
					}
					Iterator<String> it = osa.getSpecs().keySet().iterator();
					if(it.hasNext()) {
						Object defaultValue = EnumAuthCode.getDefaultValue(it.next());
						if(defaultValue == null) {
							flag = false;//枚举类没有对应的authCode，就改成非法
						}
					}
					if(flag) {//如果合法
						errorFlag = false;//errorFlag = false表示合法
					}
				}
			}
			if(errorFlag) {
				return buildFailResult(participant, EnumResultCode.E_ORDER_AUTH_CODE_ERROR.getInfo(), HttpStatus.NOT_FOUND);
			}
			boolean setResult = redisCacheManager.set(RedisConsts.ORDERKEY + orderId, new RedisOrderDto(dto, userId), TimeConsts.SECOND_OF_HOUR * 12);
			if (!setResult) {
				return buildFailResult(participant, EnumResultCode.E_ORDER_ILLEGAL.getInfo(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return buildSuccessResult(participant, ptsProperty.getDomain()+"/"+SysConstant.ORDER_URIKEY + orderId);
		} catch (Exception e) {
			return buildFailResult(participant, EnumResultCode.E_ORDER_ILLEGAL.getInfo(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}



	/**
	 * 订单取消
	 * @param orderId
	 * @param nonce
	 * @param sign
	 * @return
	 */
	@Override
	public ResponseEntity cancel(long orderId, String nonce, String sign) {
		RedisOrderDto dto = redisCacheManager.get(RedisConsts.ORDERKEY + orderId, RedisOrderDto.class);
		if (dto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		//校验签名值
		if (orderManager.checkSign(SysConstant.ORDER_URIKEY + orderId, nonce, sign).isSuccess()) {
			//幂等调用接口
			boolean isRepeat = redisCacheManager.setnx(RedisConsts.ORDERIDMP + orderId, "cancel:" + DateViewUtils.getNowFull(), -1L);
			if (isRepeat) {
				//redis中删除orderId对应信息
				Boolean delResult = redisCacheManager.delete(RedisConsts.ORDERKEY + orderId);
				if (delResult) {
					redisCacheManager.delete(RedisConsts.ORDERIDMP + orderId);
					return ResponseEntity.ok(EnumResultCode.E_SUCCES.getInfo());
				}
				//删除失败删除幂等key
				redisCacheManager.delete(RedisConsts.ORDERIDMP + orderId);
			}
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}




	/**
	 * 订单确认
	 */
	@Override
	public ResponseEntity confirm(long orderId, String nonce, String sign) {
		RedisOrderDto dto = redisCacheManager.get(RedisConsts.ORDERKEY + orderId, RedisOrderDto.class);
		if (dto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		//校验签名值
		if (orderManager.checkSign(SysConstant.ORDER_URIKEY + orderId, nonce, sign).isSuccess()) {
			//幂等调用接口
			boolean isRepeat = redisCacheManager.setnx(RedisConsts.ORDERIDMP + orderId, "confirm:" + DateViewUtils.getNowFull(), -1L);
			if (isRepeat) {
				//事务支持
				IResult<String> result = orderManager.modifyOrderEffective(dto);
				if (result.isSuccess()) {
					//操作成功,删除记录的key
					redisCacheManager.delete(RedisConsts.ORDERKEY + orderId);
					return ResponseEntity.ok(EnumResultCode.E_SUCCES.getInfo());
				} else {
					//操作失败
					redisCacheManager.delete(RedisConsts.ORDERIDMP + orderId);
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}




	private IResult<Participant> buildFailResult(Participant participant, String info, HttpStatus httpStatus) {
		participant.setExecuteTime(OffsetDateTime.now());
		participant.setParticipantErrorResponse(new ResponseEntity<>(info, httpStatus));
		participant.setTccStatus(TccStatus.CONFLICT);
		return DefaultResult.failResult(info, participant);
	}

	private IResult<Participant> buildSuccessResult(Participant participant, String uri) {
		participant.setUri(uri);
		//12小时失效
		participant.setExpireTime(OffsetDateTime.now().plusHours(12L));
		participant.setExecuteTime(OffsetDateTime.now());
		participant.setParticipantErrorResponse(ResponseEntity.ok(EnumResultCode.E_SUCCES.getInfo()));
		participant.setTccStatus(TccStatus.TO_BE_CONFIRMED);
		return DefaultResult.successResult(participant);
	}

	public static void main(String[] args) {
		Object obj = "1";
		System.out.println(obj.toString());
		System.out.println(Integer.valueOf(obj.toString()));
	}



}
