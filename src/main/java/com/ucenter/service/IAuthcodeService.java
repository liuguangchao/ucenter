package com.ucenter.service;

public interface IAuthcodeService {

	void sendAuthCode(String tel);
	
	boolean verifyAuthCode(String tel, String code);

}
