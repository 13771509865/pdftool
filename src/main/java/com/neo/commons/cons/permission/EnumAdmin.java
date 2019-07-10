package com.neo.commons.cons.permission;

public enum EnumAdmin {
	SYS_USER(1, "只能访问用户页面的普通用户", "普通用户"), SYS_ADMIN(0, "无敌", "超级管理员");

	private Integer code;
	private String info;
	private String name;

	private EnumAdmin(Integer code, String info, String name) {
		this.code = code;
		this.info = info;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static EnumAdmin getEnum(Integer code) {
		for (EnumAdmin obj : values()) {
			if (obj.getCode().equals(code)) {
				return obj;
			}
		}
		return null;
	}

	public static boolean isManager(Integer code) {
		if (SYS_ADMIN.code.equals(code)) {
			return true;
		}
		return false;
	}
}
