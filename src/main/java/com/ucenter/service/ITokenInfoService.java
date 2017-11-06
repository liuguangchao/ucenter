package com.ucenter.service;

public interface ITokenInfoService {

	Long getUserIdByToken(String token);
	
	String getTokenByUserId(Long userId);

	String genToken(Long userId);

}
