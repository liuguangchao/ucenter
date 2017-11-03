package com.bracelet.service;

import com.bracelet.entity.Fence;

public interface IFenceService {

	Fence getOne(Long user_id);

	boolean insert(Long user_id, String lat, String lng, Integer radius);

	boolean update(Long id, Long user_id, String lat, String lng, Integer radius);

	boolean delete(Long id, Long user_id);

	void checkFenceArea(Long user_id, String imei, String lat, String lng, long time);

	boolean updateStatus(Long id, Integer status);

}
