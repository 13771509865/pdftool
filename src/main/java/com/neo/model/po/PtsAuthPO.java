package com.neo.model.po;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class PtsAuthPO {
	
	private Integer id;
	private Date gmtCreate;
	private Date gmtModified;
	private Date gmtExpire;
	private Integer status;
	private Long userid;
	private String authCode;
	private String authValue;
	private String remark;
	private Long orderId;
	private Integer priority;
	

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	
	public static void main(String[] args) {
		Map<String,Object> newAuthMap = new HashMap<>();
		Map<String,Object> newAuthMap2 = new HashMap<>();
		newAuthMap.put("v", 1);
		newAuthMap2.put("v", 1);
		
		
		System.out.println(newAuthMap.get("v").toString());
		System.out.println(newAuthMap2.get("v").toString());
		System.out.println(StringUtils.equals(newAuthMap.get("v").toString(), newAuthMap2.get("v").toString()));
	}
	
	
}
