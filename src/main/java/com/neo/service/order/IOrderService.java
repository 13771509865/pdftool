package com.neo.service.order;

import org.springframework.http.ResponseEntity;

import com.neo.commons.cons.IResult;
import com.yozosoft.api.order.dto.OrderRequestDto;
import com.yozosoft.api.order.dto.ServiceAppUserRightDto;
import com.yozosoft.api.tcc.Participant;

public interface IOrderService {

	IResult<Participant> reserve(long userId, long orderId, OrderRequestDto dto, String nonce, String sign);
	
	ResponseEntity cancel(long orderId, String nonce, String sign);
	
	ResponseEntity confirm(ServiceAppUserRightDto serviceAppUserRightDto,long orderId, String nonce, String sign);
}
