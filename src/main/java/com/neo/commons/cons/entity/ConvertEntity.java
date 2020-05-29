package com.neo.commons.cons.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConvertEntity {
	
	public String ipAddress;
	public String cookie;
	public Boolean isMember;
	public String fileHash;
	public Boolean isMobile;
	public Long userId;
	public Integer module;

}
