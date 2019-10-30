package com.neo.model.po;

import java.util.Date;

import com.alibaba.fastjson.JSON;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PtsAuthNamePO {
	
	
	private Integer id;
	private Date gmtCreate;
	private Date gmtModified;
	private Integer status;
	private String authName;
	private String authCode;
	private String valueType;
	private String valueUnit;	
	private String defaultVaule;
	private String minValue;
	private String maxValue;
	private String description;
	private String optionals;

	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
