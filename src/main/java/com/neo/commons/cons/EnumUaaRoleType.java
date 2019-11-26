package com.neo.commons.cons;

import lombok.Getter;


/**
 * 单点账号类型
 * @author xujun
 * @description
 * @create 2019年11月25日
 */
@Getter
public enum EnumUaaRoleType {
	
	USER(1,"User"),
    CORP_MEMBER(2,"CorpMember"),
    CORP_ADMIN(3,"CorpAdmin"),
    ADMIN(4,"Admin");
	
	
	private Integer value;
	private String info;
	
	private EnumUaaRoleType(Integer value, String info) {
		this.value = value;
		this.info = info;
	}

}
