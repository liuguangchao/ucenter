package com.ucenter.entity;

import lombok.Data;

import java.util.Date;

@Data
public class DeviceInfo {

    private Long id;
    private String mac;
    private String imei;
    private String sn;
    private Integer type;
    private String dv;
    private String sd;
    private Long user_id;
    private Date createtime;
    private Date bindingtime;

}
