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
		int i = jdbcTemplate.update("insert into uc_user (username, password, createtime) values (?,?,?)",
				new Object[] { tel, password, now }, new int[] { Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP });
		return i == 1;
	}





	@Override
	public UserInfo getUserInfoById(Long id) {
		String sql = "select * from uc_user where user_id=? LIMIT 1";
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
		String sql = "select * from uc_user where username=? LIMIT 1";
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
		String sql = "select * from uc_user where username=? and password=? LIMIT 1";
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
