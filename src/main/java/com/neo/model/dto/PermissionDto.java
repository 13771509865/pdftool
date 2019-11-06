package com.neo.model.dto;

import com.alibaba.fastjson.JSON;
import com.yozosoft.api.order.dto.OrderRequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 权限对象
 * @author xujun
 * @description
 * @create 2019年11月3日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto {

	private String convert001;
	private String convert002;
	private String convert003;
	private String convert004;
	private String convert005;
	private String convert006;
	private String convert007;
	private String convert008;
	private String convert009;
	private String convert010;
	private String convert011;
	private String convert012;
	private String convert013;
	private Integer convertNum;
	private Integer uploadSize;
	private Integer validityTime;


	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}	





}
