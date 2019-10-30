package com.neo.commons.cons;


/**
 * 对应数据库auth字段中的值
 * @author xujun
 * @description
 * @create 2019年10月30日
 */
public enum EnumAuthCode {
	
	PTS_CONVERT_NUM(0, "convertNum"), 
	PTS_UPLOAD_SIZE(1,"uploadSize");
	
	
	private Integer value;
	private String info;
	
	
	private EnumAuthCode(int code, String info) {
		this.value = code;
		this.info = info;
	}
	public static String getTypeInfo(Integer velue) {
        for (EnumAuthCode statu : values()) {
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
