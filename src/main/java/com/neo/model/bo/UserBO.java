package com.neo.model.bo;

import java.util.Set;

import com.alibaba.fastjson.JSON;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBO {
	
	private String role;
	private Long userId;
	private String yomoerId;
	private String	lastLogin;
	private String	modifyTime;
	private String  account;
	private String	phone;
	private String	email;
	private String	avatar;
	private String	name;
	private String	sex;
	private String	birthday;
	private String	chPasswd;
	private Set<String>	binds;
	private String	canUnbind;
	private String  canMerge;
	private MembershipsBO[]  memberships;
	private String membership;
	private String securityLevel;
	private String securityLevelCN;
	
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
	

	
}

