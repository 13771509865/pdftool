package com.neo.commons.cons;

public enum EnumSex {
	MALE(1, "男"), FAMALE(0, "女");
	private Integer value;
	private String info;

	private EnumSex(Integer code, String info) {
		this.value = code;
		this.info = info;
	}
//	public static void main(String[] args) {
//		System.out.println(getCheckedValue(null));
//	}

	public static Integer getCheckedValue(Integer velue) {
         for (EnumSex sex : values()) {
			if(sex.getValue().equals(velue)){
				return sex.getValue();
			}
		}
		return EnumSex.MALE.value;
	}
	public static String getSexInfo(Integer velue) {
        for (EnumSex sex : values()) {
			if(sex.getValue().equals(velue)){
				return sex.getInfo();
			}
		}
		return EnumSex.MALE.info;
	}
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
