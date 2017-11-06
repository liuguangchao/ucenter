package com.bracelet.controller;

import com.bracelet.dto.HttpBaseDto;
import com.bracelet.exception.BizException;
import com.bracelet.service.IAuthcodeService;
import com.bracelet.util.RespCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 拆分验证码接口
 */
@Controller
@RequestMapping("/authcode")
public class AuthcodeController extends BaseController {


    @Autowired
    IAuthcodeService authcodeService;
    private Logger logger = LoggerFactory.getLogger( getClass() );

    @ResponseBody
    @RequestMapping(value = "/authcode/{tel}", method = RequestMethod.GET)
    public HttpBaseDto getAuthCode(@PathVariable String tel) {
        if (StringUtils.isEmpty( tel )) {
            throw new BizException( RespCode.NOTEXIST_PARAM );
        }
        this.authcodeService.sendAuthCode( tel );
        HttpBaseDto dto = new HttpBaseDto();
        return dto;
    }

    @ResponseBody
    @RequestMapping(value = "/authcode", method = RequestMethod.POST)
    public HttpBaseDto checkAuthCode(@RequestParam String tel, @RequestParam String code) {
        if (StringUtils.isAllEmpty( tel, code )) {
            throw new BizException( RespCode.NOTEXIST_PARAM );
        }
        if (this.authcodeService.verifyAuthCode( tel, code )) {
            HttpBaseDto dto = new HttpBaseDto();
            return dto;
        } else {
            // 验证码错误
            logger.info( "验证码验证失败, tel:" + tel + ",code:" + code );
            throw new BizException( RespCode.U_AUTHCODE_NOTEXIST );
        }

    }

}
