package com.neo.commons.cons;

public enum EnumStatus {
	ENABLE(1, "启用"), DISABLE(0, "禁用");
	private Integer value;
	private String info;
	private EnumStatus(int code, String info) {
		this.value = code;
		this.info = info;
	}
	public static String getStatusInfo(Integer velue) {
        for (EnumStatus statu : values()) {
			if(statu.getValue().equals(velue)){
				return statu.getInfo();
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
