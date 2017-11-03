package com.bracelet.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientGet {
	private static Logger logger = LoggerFactory.getLogger(HttpClientGet.class);
	private static final String defaultCharset = "utf-8";
	private static CloseableHttpClient httpClient = HttpClientBuilder.create().build();
	private static RequestConfig requestConfig = null;
	static {
		requestConfig = RequestConfig.custom().setConnectionRequestTimeout(100).setConnectTimeout(3000)
				.setSocketTimeout(3000).build();
	}

	public static String get(String url) {
		HttpResponse httpResponse = null;
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("user-agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.131 Safari/537.36");
		// 添加head方法二
		httpGet.addHeader(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8"));
		httpGet.setConfig(requestConfig);
		String reponseStr = null;
		try {
			httpResponse = httpClient.execute(httpGet);
			reponseStr = EntityUtils.toString(httpResponse.getEntity(), defaultCharset);
		} catch (Exception e) {
			logger.error("http error.", e);
		}

		return reponseStr;
	}
}
