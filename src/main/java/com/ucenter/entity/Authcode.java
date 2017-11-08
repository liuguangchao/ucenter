package com.ucenter.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Authcode {

	private Long id;
	private String tel;
	private String code;
	private Timestamp createtime;


}
