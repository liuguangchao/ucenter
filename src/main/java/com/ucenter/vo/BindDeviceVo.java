package com.ucenter.vo;

import lombok.Data;

@Data
public class BindDeviceVo {
    private String token;
    private int type;
    private int idtype;
    private String mac;
    private String imei;
    private String sn;
}
