package com.bracelet.socket.business.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bracelet.exception.BizException;
import com.bracelet.util.RespCode;
import com.bracelet.socket.business.IService;

/**
 * 业务类型工厂类,根据type返回对应的业务处理对象
 * 
 */
@Component
public class SocketBusinessFactory {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 根据名称注入对应的业务
	 */
	@Resource
	private IService loginService;
	@Resource
	private IService heartBeatService;
	@Resource
	private IService heartRateService;
	@Resource
	private IService locationService;
	@Resource
	private IService heartCheck;
	@Resource
	private IService heartPressureService;
	@Resource
	private IService sosService;
	@Resource
	private IService stepService;

	public IService getService(int type) throws BizException {
		logger.info("*****type:" + type);
		switch (type) {
		case 1:
			// 登录
			return loginService;
		case 2:
			// 心跳
			return heartBeatService;
		case 9:
			// 系统心跳
			return heartCheck;
		case 10:
			// 脉搏
			return heartRateService;
		case 11:
			// 血压
			return heartPressureService;
		case 12:
			// 位置信息
			return locationService;
		case 13:
			// 计步
			return stepService;
		case 30:
			// 位置信息
			return locationService;
		case 32:
			// 位置信息
			return sosService;
		default:
			logger.info("找不到对应的类型:" + type);
			throw new BizException(RespCode.DEV_REQ_TYPE_ERR);
		}
	}
}
