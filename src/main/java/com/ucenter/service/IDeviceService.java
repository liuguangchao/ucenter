package com.ucenter.service;

import com.ucenter.entity.DeviceInfo;

import java.util.List;

public interface IDeviceService {

	List<DeviceInfo> getListByUserId(Long userId);

	DeviceInfo  getById(Long id);

	boolean bind(int type,int idtype,String mac,String imei,String sn,Long userId);

	boolean unbind(Long id,Long userId);


}
