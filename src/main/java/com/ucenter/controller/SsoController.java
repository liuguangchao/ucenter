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
    private Logger logger = LoggerFactory.getLogger( getClass() );


    @ResponseBody
    @RequestMapping(value = "/smsLogin", method = RequestMethod.POST)
    public HttpBaseDto login(@RequestParam String tel, @RequestParam String code) {
        if (StringUtils.isAllEmpty( tel, code )) {
            throw new BizException( RespCode.NOTEXIST_PARAM );
        }
        if (this.authcodeService.verifyAuthCode( tel, code )) {
            UserInfo userInfo = userInfoService.getUserInfoByUsername( tel );
            if (userInfo == null) {
                logger.info( "该手机号尚未注册, tel:" + tel );
                throw new BizException( RespCode.U_TEL_NOT_REGED );
            }
            String token = this.tokenInfoService.genToken( userInfo.getId() );
            HttpBaseDto dto = new HttpBaseDto();
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put( "id", userInfo.getId() );
            dataMap.put( "username", userInfo.getUsername() );
            dataMap.put( "token", token );
            dto.setData( dataMap );
            return dto;
        } else {
            // 验证码错误
            logger.info( "验证码验证失败, tel:" + tel + ",code:" + code );
            throw new BizException( RespCode.U_AUTHCODE_NOTEXIST );
        }
    }

    @ResponseBody
    @RequestMapping(value = "/pwdLogin", method = RequestMethod.POST)
    public HttpBaseDto loginByPwd(@RequestParam String tel, @RequestParam String password) {
        if (StringUtils.isAllEmpty( tel, password )) {
            throw new BizException( RespCode.NOTEXIST_PARAM );
        }
        UserInfo userInfo = userInfoService.loginBypwd( tel,password );
        if (userInfo == null) {
            logger.info( "该手机号尚未注册, tel:" + tel );
            throw new BizException( RespCode.U_TEL_NOT_REGED );
        }
        String token = this.tokenInfoService.genToken( userInfo.getId() );
        HttpBaseDto dto = new HttpBaseDto();
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put( "id", userInfo.getId() );
        dataMap.put( "username", userInfo.getUsername() );
        dataMap.put( "token", token );
        dto.setData( dataMap );
        return dto;
    }

    @ResponseBody
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public HttpBaseDto checkToken(@RequestParam String userId, @RequestParam String token) {
        if (StringUtils.isAllEmpty( userId, token )) {
            throw new BizException( RespCode.NOTEXIST_PARAM );
        }
        Long userid = tokenInfoService.getUserIdByToken( token );
        String useridStr = String.valueOf( userid );
        if (StringUtils.isEmpty( useridStr )) {
            throw new BizException( RespCode.U_TOKEN_ERR );
        }
        if (!useridStr.equals( userId )) {
            throw new BizException( RespCode.U_TOKEN_ERR );
        }
        return new HttpBaseDto();
    }
}
