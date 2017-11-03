package com.bracelet.controller;

import com.alibaba.fastjson.JSON;
import com.bracelet.dto.HttpBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.UserInfo;
import com.bracelet.exception.BizException;
import com.bracelet.service.IAuthcodeService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.RanomUtil;
import com.bracelet.util.RespCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
	@RequestMapping(value = "/getAuthCode/{tel}", method = RequestMethod.GET)
	public HttpBaseDto getAuthCode(@PathVariable String tel) {
		if (StringUtils.isEmpty(tel)) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
		this.authcodeService.sendAuthCode(tel);
		HttpBaseDto dto = new HttpBaseDto();
		return dto;
	}

	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public HttpBaseDto register(@RequestParam String tel, @RequestParam String code) {
		if (StringUtils.isAllEmpty(tel, code)) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
		if (this.authcodeService.verifyAuthCode(tel, code)) {
			UserInfo userInfo = userInfoService.getUserInfoByUsername(tel);
			if (userInfo != null) {
				logger.info("该手机号已经注册, tel:" + tel);
				throw new BizException(RespCode.U_ALREADY_REGED);
			}
			if (this.userInfoService.insert(tel)) {
				UserInfo savedObj = userInfoService.getUserInfoByUsername(tel);
				String token = this.tokenInfoService.genToken(savedObj.getUser_id());
				HttpBaseDto dto = new HttpBaseDto();
				Map<String, Object> dataMap = new HashMap<>();
				dataMap.put("id", savedObj.getUser_id());
				dataMap.put("username", savedObj.getUsername());
				dataMap.put("imei", savedObj.getImei());
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

	@ResponseBody
	@RequestMapping(value = "/device/{token}", method = RequestMethod.GET)
	public HttpBaseDto getDevice(@PathVariable String token) {
		Long user_id = checkTokenAndUser(token);
		UserInfo userInfo = userInfoService.getUserInfoById(user_id);
		if (userInfo == null) {
			logger.info("获取用户设备，token对应的user不存在 token:" + token + ",userId:" + user_id);
			throw new BizException(RespCode.U_NOT_EXIST);
		}
		HttpBaseDto dto = new HttpBaseDto();
		Map<String, Object> dataMap = new HashMap<>();
		if (StringUtils.isNotEmpty(userInfo.getImei())) {
			dataMap.put("imei", userInfo.getImei());
			dataMap.put("dv", userInfo.getDv());
			dataMap.put("sd", userInfo.getSd());
			dataMap.put("bindingtime", userInfo.getBindingtime() != null ? userInfo.getBindingtime().getTime() : 0);
		}
		dto.setData(dataMap);
		return dto;
	}

	@ResponseBody
	@RequestMapping(value = "/device/bind", method = RequestMethod.POST)
	public HttpBaseDto deviceBind(@RequestParam String token, @RequestParam String imei) {
		if (StringUtils.isEmpty(imei)) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
		Long user_id = checkTokenAndUser(token);
		if (this.userInfoService.bindDevice(user_id, imei)) {
			HttpBaseDto dto = new HttpBaseDto();
			return dto;
		} else {
			logger.info("用户绑定设备失败, token:" + token + ",userId:" + user_id + ",imei:" + imei);
			throw new BizException(RespCode.U_BINGDING_ERR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/device/unbind", method = RequestMethod.POST)
	public HttpBaseDto deviceUnbind(@RequestParam String token, @RequestParam String imei) {
		if (StringUtils.isEmpty(imei)) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
		Long user_id = checkTokenAndUser(token);
		if (this.userInfoService.unbindDevice(user_id, imei)) {
			HttpBaseDto dto = new HttpBaseDto();
			return dto;
		} else {
			logger.info("用户解除绑定设备失败, token:" + token + ",userId:" + user_id + ",imei:" + imei);
			throw new BizException(RespCode.SYS_ERR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/ask/device/{token}", method = RequestMethod.GET)
	public HttpBaseDto askDevice(@PathVariable String token) {
		Long user_id = checkTokenAndUser(token);
		UserInfo userInfo = userInfoService.getUserInfoById(user_id);
		if (userInfo == null) {
			logger.info("askDevice error.no login.token:" + token);
			throw new BizException(RespCode.U_NOT_EXIST);
		}
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(userInfo.getImei());
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			logger.info("socketLoginDto error.no login.token:" + token);
			throw new BizException(RespCode.DEV_NOT_LOGIN);
		}

		Map<String, Object> req = new HashMap<String, Object>();
		req.put("a", 0);
		req.put("type", 31);
		req.put("no", RanomUtil.getFixLenthString(10));
		req.put("timestamp", System.currentTimeMillis() / 1000);
		if (socketLoginDto.getChannel().isActive()) {
			socketLoginDto.getChannel().writeAndFlush(JSON.toJSONString(req) + "\r\n");
			logger.info("===request askDevice...ip:" + socketLoginDto.getChannel().remoteAddress().toString() + ",data:"
					+ JSON.toJSONString(req));
		} else {
			logger.info("socketLoginDto error.no login.not active.token:" + token);
			throw new BizException(RespCode.DEV_NOT_LOGIN);
		}

		HttpBaseDto dto = new HttpBaseDto();
		return dto;
	}

}
