package com.neo.model.po;

import java.util.Date;

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
	private String auth;
	private String remark;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	
	
}
