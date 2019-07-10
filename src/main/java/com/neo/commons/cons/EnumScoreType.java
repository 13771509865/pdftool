package com.neo.commons.cons;

public enum EnumScoreType {
	AD(0, "广告" , 20) , DRINK(1, "饮水",10);
	private Integer value;
	private String info;
	private Integer score;

	private EnumScoreType(Integer value, String info,Integer score) {
		this.value = value;
		this.info = info;
		this.score = score;
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

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

}
