package com.neo.model.qo;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.neo.model.po.PtsConvertRecordPO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PtsConvertRecordQO {
	
	private Integer convertNum;
	
	
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
