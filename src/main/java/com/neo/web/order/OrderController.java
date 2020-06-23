package com.neo.web.order;

import com.neo.commons.cons.IResult;
import com.neo.commons.util.SysLogUtils;
import com.neo.service.order.IOrderService;
import com.yozosoft.api.order.dto.OrderRequestDto;
import com.yozosoft.api.order.dto.ServiceAppUserRightDto;
import com.yozosoft.api.tcc.Participant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * 商品订单接口
 * @author xujun
 * @description
 * @create 2019年10月30日
 */
@Api(value = "订单Controller", tags = {"订单Controller"})
@Controller
@RequestMapping(value = "/api")
public class OrderController {

	@Autowired
	private IOrderService iOrderService;


	@ApiOperation(value = "资源预留接口")
	@PostMapping(value = "/order/reserve")
	public ResponseEntity reserveOrder(@RequestParam long userId, @RequestParam long orderId, @RequestBody OrderRequestDto dto, @RequestParam String nonce, @RequestParam String sign) {
		try {
			IResult<Participant> reserveResult = iOrderService.reserve(userId, orderId, dto, nonce, sign);
			SysLogUtils.info("[userId]"+userId+""+"[,订单号："+orderId+"预留,],预留结果："+reserveResult.getData());
			return ResponseEntity.ok(reserveResult.getData());
		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}



	@ApiOperation(value = "订单取消接口")
	@DeleteMapping(value = "/order/{orderId}")
	public ResponseEntity cancelOrder(@PathVariable("orderId") long orderId, @RequestParam String nonce, @RequestParam String sign) {
		try {
			ResponseEntity result = iOrderService.cancel(orderId, nonce, sign);
			SysLogUtils.info("[订单号："+orderId+"取消],取消结果："+result.getStatusCode());
			return result;
		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} 




	@ApiOperation(value = "订单确认接口")
	@PutMapping(value = "/order/{orderId}")
	public ResponseEntity confirmOrder(@RequestBody ServiceAppUserRightDto serviceAppUserRightDto,@PathVariable("orderId") long orderId, @RequestParam String nonce, @RequestParam String sign) {
		try {
			ResponseEntity result = iOrderService.confirm(serviceAppUserRightDto,orderId, nonce, sign);
			SysLogUtils.info("[订单号："+orderId+"开始确认],确认结果："+result.getStatusCode());
			return result;
		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}













}
