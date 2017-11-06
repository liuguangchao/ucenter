package com.ucenter.service;

import com.ucenter.entity.UserInfo;

public interface IUserInfoService {

	boolean insert(String tel,String password);

	boolean bindDevice(Long user_id, String imei);

	boolean unbindDevice(Long user_id, String imei);

	UserInfo getUserInfoByImei(String imei);

	UserInfo getUserInfoById(Long id);

	UserInfo getUserInfoByUsername(String username);

	UserInfo loginBypwd(String username,String pwd);

}
