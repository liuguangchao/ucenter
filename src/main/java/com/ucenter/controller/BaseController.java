package com.ucenter.controller;

import com.ucenter.exception.BizException;
import com.ucenter.service.ITokenInfoService;
import com.ucenter.util.RespCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {

	@Autowired
	protected ITokenInfoService tokenInfoService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	protected Long checkTokenAndUser(String token) {
		if (StringUtils.isEmpty(token)) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
		Long id = tokenInfoService.getUserIdByToken(token);
		if (id == null) {
			logger.info("[checkTokenAndUser] 通过token检查userid不存在，token:" + token);
			throw new BizException(RespCode.U_NOT_EXIST);
		}
		return id;
	}

}
