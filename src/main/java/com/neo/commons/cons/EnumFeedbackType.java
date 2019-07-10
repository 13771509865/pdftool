package com.neo.commons.cons;


/**
 * 用户反馈枚举类型
 * @author xujun
 *
 */
public enum EnumFeedbackType {

	FILE_UPLOAD_FAIL(0,"文档上传失败"),
	CONVERT_FAIL(1,"转换失败"),
	CONVERT_TIMEOUT(2,"转换时间过长"),
	FILE_DOWNLOAD_FAIL(3,"转换文档下载失败"),
	CONVERT_FILE_OPEN_FAIL(4,"转换后的文档无法打开"),
	FILE_CONTENT_LOSE(5,"文档内容丢失"),
	CODES_ERROR(6,"乱码"),
	TEXT_MOVE(7,"文本偏移"),
	PICTURE_CHANGE(8,"图片变形"),
	CHARACTER_CONVERT_FAIL(9,"公式等符号转换错误"),
	OHTERS(10,"其他");



	private Integer value;
	private String info;

	private EnumFeedbackType(int code, String info) {
		this.value = code;
		this.info = info;
	}


	public static String getStatusInfo(Integer velue) {
		for (EnumFeedbackType feedbackType : values()) {
			if (feedbackType.getValue().equals(velue)) {
				return feedbackType.getInfo();
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
