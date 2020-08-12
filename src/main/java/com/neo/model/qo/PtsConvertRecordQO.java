package com.neo.model.qo;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PtsConvertRecordQO {
	
	private Integer convertNum;
	
	
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
