package com.neo.commons.cons.entity;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConvertEntity {
	
	private String ipAddress;
	private String cookie;
	private Boolean isMember;
	private String fileHash;
	private Boolean isMobile;
	private Long userId;
	private Integer module;
	private Boolean isRPT;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
