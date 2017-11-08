package com.ucenter.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TokenInfo {

	private Long id;
	private String token;
	private Long user_id;
	private Timestamp createtime;


}
