package com.ucenter.util;

import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Utils {

	public final static String accessKeyId = "LTAIIqNyt5D3mDM0";
	public final static String accessKeySecret = "acfLjKRCYBPcait4TDAIljSjWO9axl";
	public final static String accessKeyIdOfBeidou = "LTAI7YNEkaz5J7Vy";
	public final static String accessKeySecretOfBeidou = "EIHW3lpcPMXnxsdW7CW9jnriovXTch";
	public final static String MD5_MAGIC = "water";

	public static String randomString(int len) {
		if (len <= 0) {
			len = 32;
		}
		String chars = "ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678";
		int maxPos = chars.length();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			sb.append(chars.charAt((int) Math.floor(Math.random() * maxPos)));
		}
		return sb.toString();
	}

	public static int randomInt(int low, int high) {
		return (int) Math.floor(Math.random() * (high - low) + low);
	}

	/**
	 * 生成md5
	 *
	 * @param message
	 */
	public static String getMD5(String message) {
		String md5str = "";
		try {
			// 1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
			MessageDigest md = MessageDigest.getInstance("MD5");

			// 2 将消息变成byte数组
			byte[] input = message.getBytes();

			// 3 计算后获得字节数组,这就是那128位了
			byte[] buff = md.digest(input);

			// 4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串
			md5str = bytesToHex(buff);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return md5str;
	}

	/**
	 * 二进制转十六进制
	 *
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(byte[] bytes) {
		StringBuffer md5str = new StringBuffer();
		// 把数组每一字节换成16进制连成md5字符串
		int digital;
		for (int i = 0; i < bytes.length; i++) {
			digital = bytes[i];

			if (digital < 0) {
				digital += 256;
			}
			if (digital < 16) {
				md5str.append("0");
			}
			md5str.append(Integer.toHexString(digital));
		}
		return md5str.toString().toUpperCase();
	}

	/**
	 * 计算两个坐标点距离
	 * @param lngA
	 * @param latA
	 * @param lngB
	 * @param latB
	 * @return 单位：米
	 */
	public static double calcDistance(double lngA, double latA, double lngB, double latB) {
		double earthR = 6371000;
		double x = Math.cos(latA * Math.PI / 180.) * Math.cos(latB * Math.PI / 180.)
				* Math.cos((lngA - lngB) * Math.PI / 180);
		double y = Math.sin(latA * Math.PI / 180.) * Math.sin(latB * Math.PI / 180.);
		double s = x + y;
		if (s > 1)
			s = 1;
		if (s < -1)
			s = -1;
		double alpha = Math.acos(s);
		return alpha * earthR;
	}

	public static String format14DateString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = dateFormat.format(date);
		return dateString;
	}

	public static String format14DateString(long time) {
		return format14DateString(new Date(time));
	}

	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	/**
	 * 90mmHg<收缩压<140mmHg
	 * 60mmHg<舒张压<90mmHg
	 */
	public static List<Map<String, Object>> checkHeartPressure(Integer maxHeartPressure, Integer minHeartPressure) {
		List<Map<String, Object>> resultList = new LinkedList<Map<String, Object>>();
		Map<String, Object> maxResultMap = new HashMap<>();
		if (maxHeartPressure < 90) {
			maxResultMap.put("type", 1);
			maxResultMap.put("code", 2);
			maxResultMap.put("msg", "偏低");
		} else if (maxHeartPressure > 140) {
			maxResultMap.put("type", 1);
			maxResultMap.put("code", 1);
			maxResultMap.put("msg", "偏高");
		} else {
			maxResultMap.put("type", 1);
			maxResultMap.put("code", 0);
			maxResultMap.put("msg", "正常");
		}
		Map<String, Object> minResultMap = new HashMap<>();
		if (minHeartPressure < 60) {
			minResultMap.put("type", 2);
			minResultMap.put("code", 2);
			minResultMap.put("msg", "偏低");
		} else if (minHeartPressure > 90) {
			minResultMap.put("type", 2);
			minResultMap.put("code", 1);
			minResultMap.put("msg", "偏高");
		} else {
			minResultMap.put("type", 2);
			minResultMap.put("code", 0);
			minResultMap.put("msg", "正常");
		}
		resultList.add(maxResultMap);
		resultList.add(minResultMap);
		return resultList;
	}

	/**
	 * 60～100次/分
	 */
	public static Map<String, Object> checkHeartRate(Integer heartRate) {
		Map<String, Object> resultMap = new HashMap<>();
		if (heartRate < 60) {
			resultMap.put("code", 2);
			resultMap.put("msg", "偏慢");
		} else if (heartRate > 100) {
			resultMap.put("code", 1);
			resultMap.put("msg", "偏快");
		} else {
			resultMap.put("code", 0);
			resultMap.put("msg", "正常");
		}
		return resultMap;
	}

}
