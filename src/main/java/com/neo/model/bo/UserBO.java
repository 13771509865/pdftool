package com.neo.model.bo;

public class UserBO {
	
	private String role;
	private String userId;
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
	private String	binds;
	private String	canUnbind;
	
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getYomoerId() {
		return yomoerId;
	}
	public void setYomoerId(String yomoerId) {
		this.yomoerId = yomoerId;
	}
	public String getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getChPasswd() {
		return chPasswd;
	}
	public void setChPasswd(String chPasswd) {
		this.chPasswd = chPasswd;
	}
	public String getBinds() {
		return binds;
	}
	public void setBinds(String binds) {
		this.binds = binds;
	}
	public String getCanUnbind() {
		return canUnbind;
	}
	public void setCanUnbind(String canUnbind) {
		this.canUnbind = canUnbind;
	}
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	@Override
	public String toString() {
		return "UserBO [role=" + role + ", userId=" + userId + ", yomoerId=" + yomoerId + ", lastLogin=" + lastLogin
				+ ", modifyTime=" + modifyTime + ", phone=" + phone + ", email=" + email + ", avatar=" + avatar
				+ ", name=" + name + ", sex=" + sex + ", birthday=" + birthday + ", chPasswd=" + chPasswd + ", binds="
				+ binds + ", canUnbind=" + canUnbind + "]";
	}

	
}
