package com.ucenter.entity;

import java.sql.Timestamp;

public class UserInfo {
	private Long user_id;
	private String username;
	// 暂时不用
	private String password;
	private String dv;
	private String sd;
	private String imei;
	private Timestamp createtime;
	private Timestamp bindingtime;

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDv() {
		return dv;
	}

	public void setDv(String dv) {
		this.dv = dv;
	}

	public String getSd() {
		return sd;
	}

	public void setSd(String sd) {
		this.sd = sd;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	/**
	 * @return the bindingtime
	 */
	public Timestamp getBindingtime() {
		return bindingtime;
	}

	/**
	 * @param bindingtime the bindingtime to set
	 */
	public void setBindingtime(Timestamp bindingtime) {
		this.bindingtime = bindingtime;
	}
}
