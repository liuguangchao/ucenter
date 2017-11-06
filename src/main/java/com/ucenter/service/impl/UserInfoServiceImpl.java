package com.ucenter.service.impl;

import com.ucenter.dto.SocketLoginDto;
import com.ucenter.entity.UserInfo;
import com.ucenter.service.IUserInfoService;
import com.ucenter.util.ChannelMap;
import com.ucenter.util.Utils;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

@Service
public class UserInfoServiceImpl implements IUserInfoService {
	@Autowired
	JdbcTemplate jdbcTemplate;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public boolean insert(String tel,String password) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update("insert into user_info (username, password, createtime) values (?,?,?)",
				new Object[] { tel, password, now }, new int[] { Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP });
		return i == 1;
	}

	public boolean bindDevice(Long user_id, String imei) {
		String sql = "select * from user_info where imei=? and user_id != ?";
		List<UserInfo> list = jdbcTemplate.query(sql, new Object[] { imei, user_id },
				new BeanPropertyRowMapper<UserInfo>(UserInfo.class));
		if (list != null && !list.isEmpty()) {
			logger.warn("用户[" + user_id + "]，设备[" + imei + "]已经被绑定，请核对您的设备信息!");
			return false;
		}

		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update("update user_info set imei = ?, bindingtime = ? where user_id = ?",
				new Object[] { imei, now, user_id }, new int[] { Types.VARCHAR, Types.TIMESTAMP, Types.INTEGER });
		return i == 1;
	}

	public boolean unbindDevice(Long user_id, String imei) {
		boolean flag = true;
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update("update user_info set imei = '', bindingtime = ? where user_id = ?",
				new Object[] { now, user_id }, new int[] { Types.TIMESTAMP, Types.INTEGER });
		flag = (i == 1);
		if (flag) {
			// clear channel
			SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
			if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
				logger.info("用户[" + user_id + "]，设备[" + imei + "]对应的channel不存在，无需关闭!");
			} else {
				Channel channel = socketLoginDto.getChannel();
				channel.close();
			}
		}
		return flag;
	}

	@Override
	public UserInfo getUserInfoByImei(String imei) {
		String sql = "select * from user_info where imei=? LIMIT 1";
		List<UserInfo> list = jdbcTemplate.query(sql, new Object[] { imei },
				new BeanPropertyRowMapper<UserInfo>(UserInfo.class));
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("cannot find userinfo,imei:" + imei);
		}
		return null;
	}

	@Override
	public UserInfo getUserInfoById(Long id) {
		String sql = "select * from user_info where user_id=? LIMIT 1";
		List<UserInfo> list = jdbcTemplate.query(sql, new Object[] { id },
				new BeanPropertyRowMapper<UserInfo>(UserInfo.class));
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("cannot find userinfo,id:" + id);
		}
		return null;
	}

	@Override
	public UserInfo getUserInfoByUsername(String username) {
		String sql = "select * from user_info where username=? LIMIT 1";
		List<UserInfo> list = jdbcTemplate.query(sql, new Object[] { username },
				new BeanPropertyRowMapper<UserInfo>(UserInfo.class));
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("cannot find userinfo,username:" + username);
		}
		return null;
	}

	@Override
	public UserInfo loginBypwd(String username, String pwd) {
		String sql = "select * from user_info where username=? and password=? LIMIT 1";
		List<UserInfo> list = jdbcTemplate.query(sql, new Object[] { username ,pwd},
				new BeanPropertyRowMapper<UserInfo>(UserInfo.class));
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("cannot find userinfo,username:" + username);
		}
		return null;
	}


}
