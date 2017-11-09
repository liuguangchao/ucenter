package com.ucenter.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserInfo {
	private Long id;
	private String username;
	// 暂时不用
	private String password;
	private Timestamp createtime;
	private Timestamp updatetime;

}
