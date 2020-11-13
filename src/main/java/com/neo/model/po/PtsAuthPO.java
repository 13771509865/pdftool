package com.neo.model.po;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;



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

	}
	
	
}
