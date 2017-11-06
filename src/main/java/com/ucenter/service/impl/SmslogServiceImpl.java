package com.ucenter.service.impl;

import com.ucenter.service.ISmslogService;
import com.ucenter.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.sql.Types;

@Service
public class SmslogServiceImpl implements ISmslogService {
	@Autowired
	JdbcTemplate jdbcTemplate;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean insert(String name, String mobile, String tpl_code, String tpl_param, Integer rstatus, String rmsg) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"insert into smslog (name, mobile, tpl_code, tpl_param, rstatus, rmsg, createtime) values (?,?,?,?,?,?,?)",
				new Object[] { name, mobile, tpl_code, tpl_param, rstatus, rmsg, now }, new int[] { Types.VARCHAR,
						Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.TIMESTAMP });
		return i == 1;
	}

}
