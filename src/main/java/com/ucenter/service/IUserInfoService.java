package com.ucenter.service;

import com.ucenter.entity.UserInfo;

public interface IUserInfoService {

	boolean insert(String tel);

	boolean bindDevice(Long user_id, String imei);

	boolean unbindDevice(Long user_id, String imei);

	UserInfo getUserInfoByImei(String imei);

	UserInfo getUserInfoById(Long id);

	UserInfo getUserInfoByUsername(String username);

}
