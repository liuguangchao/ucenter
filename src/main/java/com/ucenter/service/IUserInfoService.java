package com.ucenter.service;

import com.ucenter.entity.UserInfo;

public interface IUserInfoService {

	boolean insert(String tel,String password);


	UserInfo getUserInfoById(Long id);

	UserInfo getUserInfoByUsername(String username);

	UserInfo loginBypwd(String username,String pwd);

}
