package com.neo.model.po;

import java.util.Date;

import com.alibaba.fastjson.JSON;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PtsConvertRecordPO {
	
	private Integer id;
	private Date createDate;
	private Date createTime;
	private Date modifiedDate;
	private Date modifiedTime;
	private Integer status;
	private Long userID;
	private Integer module;
	private Integer convertNum;
	private String remark;
	
	public PtsConvertRecordPO(Long userID,Integer module,Date modifiedDate) {
		this.userID = userID;
		this.module = module;
		this.modifiedDate = modifiedDate;
	}
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
