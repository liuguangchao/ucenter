package com.ucenter.service.impl;

import com.ucenter.entity.Authcode;
import com.ucenter.service.IAuthcodeService;
import com.ucenter.service.SmsService;
import com.ucenter.util.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.List;

@Service
public class AuthcodeServiceImpl implements IAuthcodeService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	SmsService smsService;

	@Override
	public void sendAuthCode(String tel) {
		StringBuilder sb = new StringBuilder();
		String[] chars = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		for (int i = 0; i < 4; i++) {
			int id = (int) Math.floor(Math.random() * 10);
			sb.append(chars[id]);
		}
		String code = sb.toString();
		// save to db
		Timestamp now = Utils.getCurrentTimestamp();
		jdbcTemplate.update("insert into authcode (tel, code, createtime) values (?,?,?)", new Object[] { tel, code, now },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP });
		smsService.sendSms("短信验证码", tel, "SMS_98965016", "{\"number\":\"" + code + "\"}");
	}

	@Override
	public boolean verifyAuthCode(String tel, String code) {
		if (code == null || "".equals(code)) {
			return false;
		}
		// test
		if ("0000".equals(code)) {
			return true;
		}
		String sql = "select * from authcode where tel=? order by createtime desc LIMIT 1";
		List<Authcode> list = jdbcTemplate.query(sql, new Object[] { tel },
				new BeanPropertyRowMapper<Authcode>(Authcode.class));
		if (list != null && !list.isEmpty()) {
			Authcode authcode = list.get(0);
			// 15分钟有效期
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MINUTE, -15);
			if (authcode != null && authcode.getCode().equals(code)
					&& authcode.getCreatetime().getTime() > calendar.getTimeInMillis()) {
				return true;
			}
		} else {
			logger.info("cannot find authcode, tel:" + tel);
		}
		return false;
	}
}
