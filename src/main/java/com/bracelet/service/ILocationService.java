package com.bracelet.service;

import java.sql.Timestamp;
import java.util.List;

import com.bracelet.entity.Location;

public interface ILocationService {

	Location getLatest(Long user_id);

	List<Location> getFootprint(Long user_id, String type);

	boolean insert(Long user_id, String location_type, String lat, String lng, Integer accuracy, Integer status,
			Timestamp timestamp);

	Location getRealtimeLocation(Long user_id, Integer status);

}
