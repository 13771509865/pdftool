package com.neo.commons.cons;

public enum EnumGoodsType {
	COUPONS(0, "电子券"), MATERIAL(1, "实体");
	private Integer value;
	private String info;

	private EnumGoodsType(Integer code, String info) {
		this.value = code;
		this.info = info;
	}
//	public static void main(String[] args) {
//		System.out.println(getCheckedValue(null));
//	}

	public static Integer getCheckedValue(Integer velue) {
         for (EnumGoodsType type : values()) {
			if(type.getValue().equals(velue)){
				return type.getValue();
			}
		}
		return null;
	}
	public static String getTypeInfo(Integer velue) {
        for (EnumGoodsType type : values()) {
			if(type.getValue().equals(velue)){
				return type.getInfo();
			}
		}
		return null;
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
