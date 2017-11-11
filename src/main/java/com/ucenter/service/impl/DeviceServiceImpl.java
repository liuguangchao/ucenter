package com.ucenter.service.impl;

import com.ucenter.entity.DeviceInfo;
import com.ucenter.entity.UserInfo;
import com.ucenter.service.IDeviceService;
import com.ucenter.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;


@Slf4j
@Service
public class DeviceServiceImpl implements IDeviceService {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public List<DeviceInfo> getListByUserId(Long userId) {
       return jdbcTemplate.query("select * from   uc_user_device where user_id=?",
                new Object[] { userId }, new BeanPropertyRowMapper<DeviceInfo>(DeviceInfo.class));
    }

    @Override
    public DeviceInfo getById(Long id) {
        return jdbcTemplate.queryForObject("select * from   uc_user_device where id=?",
                new Object[] { id }, new BeanPropertyRowMapper<DeviceInfo>(DeviceInfo.class));
    }

    @Override
    public boolean bind(int type, int idtype, String mac, String imei, String sn, Long userId) {
        String tempSql="";
        String tempValue="";
        Timestamp now = Utils.getCurrentTimestamp();
        switch (idtype){
            case 1:
                tempSql="mac=?";
                tempValue=mac;
                break;
            case 2:
                tempSql="imei=?";
                tempValue=imei;
                break;
            case 3:
                tempSql="sn=?";
                tempValue=sn;
                break;
            default:
                tempSql="mac=?";
                tempValue=mac;

        }
        String sql=" update uc_user_device set user_id=? and bindingtime=? where type=? and "+tempSql;
        return jdbcTemplate.update( sql,new Object[]{userId,now,type,tempValue} )==0?false:true;
    }

    @Override
    public boolean unbind(Long id, Long userId) {
        String sql=" update uc_user_device set user_id='' and bindingtime='' where id=? and user_id=?";
        return jdbcTemplate.update( sql,new Object[]{id,userId} )==0?false:true;
    }
}
