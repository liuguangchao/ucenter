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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/sso")
public class SsoController extends BaseController {

	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	IAuthcodeService authcodeService;
	private Logger logger = LoggerFactory.getLogger(getClass());



	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public HttpBaseDto login(@RequestParam String tel, @RequestParam String code) {
		if (StringUtils.isAllEmpty(tel, code)) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
		if (this.authcodeService.verifyAuthCode(tel, code)) {
			UserInfo userInfo = userInfoService.getUserInfoByUsername(tel);
			if (userInfo == null) {
				logger.info("该手机号尚未注册, tel:" + tel);
				throw new BizException(RespCode.U_TEL_NOT_REGED);
			}
			String token = this.tokenInfoService.genToken(userInfo.getUser_id());
			HttpBaseDto dto = new HttpBaseDto();
			Map<String, Object> dataMap = new HashMap<>();
			dataMap.put("id", userInfo.getUser_id());
			dataMap.put("username", userInfo.getUsername());
			dataMap.put("imei", userInfo.getImei());
			dataMap.put("token", token);
			dto.setData(dataMap);
			return dto;
		} else {
			// 验证码错误
			logger.info("验证码验证失败, tel:" + tel + ",code:" + code);
			throw new BizException(RespCode.U_AUTHCODE_NOTEXIST);
		}
	}

}
