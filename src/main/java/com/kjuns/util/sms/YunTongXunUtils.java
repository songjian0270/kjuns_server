package com.kjuns.util.sms;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.kjuns.util.HttpClientUtil;
import com.kjuns.util.SysConf;

import net.sf.json.JSONObject;

public class YunTongXunUtils {
	
	private static Logger logger = LoggerFactory.getLogger(YunTongXunUtils.class);
	
	private static CCPRestSmsSDK restAPI;
	private static YunTongXunUtils instance = null;

	private static String serverUrl = SysConf.Y_SERVER_URL;
	private static String serverPort = SysConf.Y_SERVER_PORT;
	private static String accountSid = SysConf.Y_ACCOUNT_SID;
	private static String accountToken = SysConf.Y_ACCOUNT_TOKEN;
	private static String appId = SysConf.Y_APP_ID;
	private static String checkCodeTemplate = SysConf.Y_SMS_TEMPLATE;
	private static String isms360CheckCodeTemplate = SysConf.Y_ISMS360_TEMPLATE;

	private static String smsAPIKey = SysConf.Y_API_KEY;
	// 查账户信息的http地址
	private static String getUserInfoUrl = SysConf.Y_GETUSERINFO_URL;
	// 通用发送接口的http地址
	private static String sendSMSUrl = SysConf.Y_SENDSMS_URL;
	// 模板发送接口的http地址
	private static String tplSendSMSurl = SysConf.Y_TPLSENDSMS_URL;

	/* 保证各线程访问的均是main memory中的变量而非线程memory中的一份cache */
	public static YunTongXunUtils getInstance() throws Exception {
		if (instance == null) {
			synchronized (YunTongXunUtils.class) {
				if (instance == null) {
					instance = new YunTongXunUtils();
				}
			}
		}
		return instance;
	}

	private YunTongXunUtils() throws Exception {
		restAPI = new CCPRestSmsSDK();
		// ******************************注释*********************************************
		// *初始化服务器地址和端口 *
		// *沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
		// *生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883"); *
		// *******************************************************************************
		restAPI.init(serverUrl, serverPort);
		// ******************************注释*********************************************
		// *初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN *
		// *ACOUNT SID和AUTH TOKEN在登陆官网后，在“应用-管理控制台”中查看开发者主账号获取*
		// *参数顺序：第一个参数是ACOUNT SID，第二个参数是AUTH TOKEN。 *
		// *******************************************************************************
		restAPI.setAccount(accountSid, accountToken);
		// ******************************注释*********************************************
		// *初始化应用ID *
		// *测试开发可使用“测试Demo”的APP ID，正式上线需要使用自己创建的应用的App ID *
		// *应用ID的获取：登陆官网，在“应用-应用列表”，点击应用名称，看应用详情获取APP ID*
		// *******************************************************************************
		restAPI.setAppId(appId);
	}
	
	/**
	 * 
	 * @param cellPhoneNumber 发送电话
	 * @param data 验证码
	 * @return
	 * @throws IOException
	 */
	public Boolean sendSMS(String cellPhoneNumber, String data) throws IOException {
		String result = sendSms(smsAPIKey, "【偶像计划】您的口令是 " + data, cellPhoneNumber);
		JSONObject obj = JSONObject.fromObject(result);
		if ("0".equals(obj.get("code").toString())) {
			return true;
		} else {
			// 异常返回输出错误码和错误信息
			logger.error("错误码=" + obj.get("code") + " 错误信息= " + obj.get("msg"));
			return false;
		}
	}

	/**
	 * 
	 * @param cellPhoneNumber
	 * @param data new String[]{"6532","5"}
	 * @return
	 * @throws IOException
	 */
	public Boolean sendTemplateSMS(String cellPhoneNumber, String[] data) throws IOException {
		HashMap<String, Object> result =restAPI.sendTemplateSMS(cellPhoneNumber, checkCodeTemplate, data);
		if ("000000".equals(result.get("statusCode"))) {
			return true;
		} else {
			// 异常返回输出错误码和错误信息
			logger.error("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
			return false;
		}
	}

	// 国际短信
	public Boolean iSMS360InternationalSMSSend(String diallingCode, String cellPhoneNumber, String msg) {
		String content = isms360CheckCodeTemplate;
		String ip = "210.51.190.233";
		int port = 8085;
		content = String.format(content, msg);
		String hex = WebNetEncode.encodeHexStr(8, content);
		hex = hex.trim() + "&codec=8";
		HttpCoreUtil util = new HttpCoreUtil(ip, port, "/mt/MT3.ashx");
		String result = util.sendPostMessage("qian951", "963574128", "", diallingCode + cellPhoneNumber, "", hex);
		if (result.equals("")) {
			return false;
		}
		return true;
	}
	
	/**
	 * 取账户信息
	 *
	 * @return json格式字符串
	 * @throws java.io.IOException
	 */
	public static String getUserInfo(String apikey) throws IOException, URISyntaxException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("apikey", apikey);
		return HttpClientUtil.sendHttpPost(getUserInfoUrl, params);
	}

	/**
	 * 通用接口发短信(推荐)
	 *
	 * @param apikey
	 *            apikey
	 * @param text
	 *            短信内容 
	 * @param mobile
	 *            接受的手机号
	 * @return json格式字符串
	 * @throws IOException
	 */
	public static String sendSms(String apikey, String text, String mobile) throws IOException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("apikey", apikey);
		params.put("text", text);
		params.put("mobile", mobile);
		return HttpClientUtil.sendHttpPost(sendSMSUrl, params);
	}

	/**
	 * 通过模板号发送短信(推荐)
	 *
	 * @param apikey
	 *            apikey
	 * @param tpl_id
	 *            模板id
	 * @param tpl_value
	 *            模板变量值
	 * @param mobile
	 *            接受的手机号
	 * @return json格式字符串
	 * @throws IOException
	 */
	public static String tplSendSms(String apikey, long tpl_id, String tpl_value, String mobile) throws IOException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("apikey", apikey);
		params.put("tpl_id", String.valueOf(tpl_id));
		params.put("tpl_value", tpl_value);
		params.put("mobile", mobile);
		return HttpClientUtil.sendHttpPost(tplSendSMSurl, params);
	}
	
	public static void main(String [] arg) throws IOException, Exception{
		YunTongXunUtils.getInstance().sendTemplateSMS("15001798048", new String[]{"7767","5"});

	}

}
