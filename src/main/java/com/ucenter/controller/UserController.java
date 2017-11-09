package com.ucenter.controller;

import com.ucenter.dto.HttpBaseDto;
import com.ucenter.entity.UserInfo;
import com.ucenter.exception.BizException;
import com.ucenter.service.IAuthcodeService;
import com.ucenter.service.IUserInfoService;
import com.ucenter.util.RespCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	IAuthcodeService authcodeService;
	private Logger logger = LoggerFactory.getLogger(getClass());


	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public HttpBaseDto register(@RequestParam String tel, @RequestParam String code,@RequestParam String password) {
		if (StringUtils.isAllEmpty(tel, code,password)) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
		if (this.authcodeService.verifyAuthCode(tel, code)) {
			UserInfo userInfo = userInfoService.getUserInfoByUsername(tel);
			if (userInfo != null) {
				logger.info("该手机号已经注册, tel:" + tel);
				throw new BizException(RespCode.U_ALREADY_REGED);
			}
			if (this.userInfoService.insert(tel,password)) {
				UserInfo savedObj = userInfoService.getUserInfoByUsername(tel);
				String token = this.tokenInfoService.genToken(savedObj.getId());
				HttpBaseDto dto = new HttpBaseDto();
				Map<String, Object> dataMap = new HashMap<>();
				dataMap.put("id", savedObj.getId());
				dataMap.put("username", savedObj.getUsername());
				dataMap.put("token", token);
				dto.setData(dataMap);
				return dto;
			} else {
				logger.info("用户注册保存数据库失败, tel:" + tel);
				throw new BizException(RespCode.SYS_ERR);
			}
		} else {
			// 验证码错误
			logger.info("验证码验证失败, tel:" + tel + ",code:" + code);
			throw new BizException(RespCode.U_AUTHCODE_NOTEXIST);
		}
	}


}
