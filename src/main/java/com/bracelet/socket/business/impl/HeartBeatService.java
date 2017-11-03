package com.bracelet.socket.business.impl;

import io.netty.channel.Channel;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.socket.business.IService;

/**
 * 心跳业务
 * 
 */
@Component("heartBeatService")
public class HeartBeatService implements IService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public SocketBaseDto process(JSONObject jsonObject, Channel channel) {
		logger.info("===系统心跳：" + jsonObject.toJSONString());
		SocketBaseDto dto = new SocketBaseDto();
		dto.setType(jsonObject.getIntValue("type"));
		dto.setNo(jsonObject.getString("no"));
		dto.setTimestamp(new Date().getTime());
		dto.setStatus(0);

		return dto;
	}
}
