package com.neo.model.po;

import java.util.Date;

import com.alibaba.fastjson.JSON;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * pdf工具集，用户投票对象
 * @author xujun
 * @description
 * @create 2019年10月28日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PtsVotePO {

	private Integer id;
	private Date gmtCreate;
	private Date gmtModified;
	private Integer status;
	private String ipAddress;
	private Long userid;
	private String vote;
	private String otherContent;
	private String remark;
	
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	
	
	
	
}
