package com.ucenter.controller;

import com.ucenter.dto.HttpBaseDto;
import com.ucenter.entity.DeviceInfo;
import com.ucenter.entity.UserInfo;
import com.ucenter.exception.BizException;
import com.ucenter.service.IAuthcodeService;
import com.ucenter.service.IDeviceService;
import com.ucenter.service.IUserInfoService;
import com.ucenter.util.RespCode;
import com.ucenter.vo.BindDeviceVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	IAuthcodeService authcodeService;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	IDeviceService deviceService;


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


	@ResponseBody
	@RequestMapping(value = "/device", method = RequestMethod.GET)
	public HttpBaseDto register(@RequestParam String token) {
		if (StringUtils.isAllEmpty(token)) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
		Long userId=tokenInfoService.getUserIdByToken( token );
		if (null==userId) {
			logger.info("用户token错误");
			throw new BizException(RespCode.U_TOKEN_ERR);
		} else {
			List<DeviceInfo> deviceInfos=deviceService.getListByUserId( userId );
			HttpBaseDto baseDto= new HttpBaseDto();
			baseDto.setData( deviceInfos );
			return baseDto;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/device/bind", method = RequestMethod.POST)
	public HttpBaseDto bindDevice(@RequestParam BindDeviceVo vo) {
		if (StringUtils.isAllEmpty(vo.getToken())) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
		Long userId=tokenInfoService.getUserIdByToken( vo.getToken() );
		if (null==userId) {
			logger.info("用户token错误");
			throw new BizException(RespCode.U_TOKEN_ERR);
		} else {
			if (deviceService.bind( vo.getType(),vo.getIdtype(),vo.getMac(),vo.getImei(),vo.getSn(),userId )){
				return new HttpBaseDto();
			}else {
				throw new BizException(RespCode.DEV_BIND_ERROR);
			}
		}
	}

	@ResponseBody
	@RequestMapping(value = "/device/unbind", method = RequestMethod.POST)
	public HttpBaseDto unbindDevice(@RequestParam String  token,@RequestParam String id) {
		if (StringUtils.isAllEmpty(token,id)) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
		Long userId=tokenInfoService.getUserIdByToken( token );
		if (null==userId) {
			logger.info("用户token错误");
			throw new BizException(RespCode.U_TOKEN_ERR);
		} else {
			if (deviceService.unbind( Long.valueOf( id ),userId)){
				return new HttpBaseDto();
			}else {
				throw new BizException(RespCode.DEV_UNBIND_ERROR);
			}
		}
	}

	@ResponseBody
	@RequestMapping(value = "/ask/device/{id}", method = RequestMethod.GET)
	public HttpBaseDto bindDevice(@RequestParam String  token,@PathVariable String id) {
		if (StringUtils.isAllEmpty(token,id)) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
		Long userId=tokenInfoService.getUserIdByToken( token );
		if (null==userId) {
			logger.info("用户token错误");
			throw new BizException(RespCode.U_TOKEN_ERR);
		} else {
			DeviceInfo deviceInfo=deviceService.getById( Long.valueOf( id ));
			if (null==deviceInfo){
				throw new BizException(RespCode.DEV_NOT_EXIST);
			}else if (null==deviceInfo.getUser_id()){
				throw new BizException(RespCode.DEV_NOT_BIND);

			}else if (userId!=deviceInfo.getUser_id().longValue()){
				throw new BizException( RespCode.DEV_NOT_EXIST_RELATION);
			}else {
				return new HttpBaseDto();
			}
		}
	}
}
