package com.ucenter.entity;

import java.sql.Timestamp;

public class UserInfo {
	private Long user_id;
	private String username;
	// 暂时不用
	private String password;
	private Timestamp createtime;

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


	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}


}
