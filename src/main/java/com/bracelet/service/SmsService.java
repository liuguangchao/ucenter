package com.bracelet.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.bracelet.service.ISmslogService;
import com.bracelet.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SmsService {

	//产品名称:云通信短信API产品,开发者无需替换
	private static final String product = "Dysmsapi";
	//产品域名,开发者无需替换
	private static final String domain = "dysmsapi.aliyuncs.com";
	private Logger logger = LoggerFactory.getLogger(SmsService.class);

	@Autowired
	private ISmslogService smslogService;

	public SendSmsResponse sendSms(String name, String mobile, String tplCode, String tplParam) {
		logger.info("开始发送短信[mobile:" + mobile + ",tplCode:" + tplCode + ",tplParam:" + tplParam + "]");
		//可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		SendSmsResponse result = null;
		Integer rstatus = 0;
		String rmsg = "";
		try {
			//初始化acsClient,暂不支持region化
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", Utils.accessKeyIdOfBeidou,
					Utils.accessKeySecretOfBeidou);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);
			//组装请求对象-具体描述见控制台-文档部分内容
			SendSmsRequest request = new SendSmsRequest();
			//必填:待发送手机号
			request.setPhoneNumbers(mobile);
			//必填:短信签名-可在短信控制台中找到
			request.setSignName("昊天智城北斗");
			//必填:短信模板-可在短信控制台中找到
			request.setTemplateCode(tplCode);
			//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
			request.setTemplateParam(tplParam);
			//hint 此处可能会抛出异常，注意catch
			result = acsClient.getAcsResponse(request);
			rmsg = result.getMessage();
			logger.info("收到短信结果[mobile:" + mobile + ",tplCode:" + tplCode + ",tplParam:" + tplParam + "] ->: " + "Code="
					+ result.getCode() + ", Message=" + result.getMessage() + ", RequestId=" + result.getRequestId() + ", BizId="
					+ result.getBizId());
		} catch (Exception e) {
			rstatus = 1;
			rmsg = e.getMessage();
			logger.info("短信发送错误:", e);
		}
		smslogService.insert(name, mobile, tplCode, tplParam, rstatus, rmsg);
		return result;
	}

	public QuerySendDetailsResponse querySendDetails(String mobile, String bizId) {
		QuerySendDetailsResponse result = null;
		//可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		try {
			//初始化acsClient,暂不支持region化
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", Utils.accessKeyIdOfBeidou,
					Utils.accessKeySecretOfBeidou);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);
			//组装请求对象
			QuerySendDetailsRequest request = new QuerySendDetailsRequest();
			//必填-号码
			request.setPhoneNumber(mobile);
			//可选-流水号
			request.setBizId(bizId);
			//必填-发送日期 支持30天内记录查询，格式yyyyMMdd
			SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
			request.setSendDate(ft.format(new Date()));
			//必填-页大小
			request.setPageSize(10L);
			//必填-当前页码从1开始计数
			request.setCurrentPage(1L);
			//hint 此处可能会抛出异常，注意catch
			result = acsClient.getAcsResponse(request);
		} catch (Exception e) {
			logger.info("查询短信记录发送错误:", e);
		}
		return result;
	}

}
