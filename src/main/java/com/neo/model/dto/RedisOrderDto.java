package com.neo.model.dto;

import com.yozosoft.api.order.dto.OrderRequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xujun
 * @description redis记录order对象
 * @create 2019年10月31日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisOrderDto {

    private OrderRequestDto orderRequestDto;

    private Long userId;
}
