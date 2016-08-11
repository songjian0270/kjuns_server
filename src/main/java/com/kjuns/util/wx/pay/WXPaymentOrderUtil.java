package com.kjuns.util.wx.pay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kjuns.model.WXPaymentOrderQuery;
import com.kjuns.model.WXPaymentPrevOrder;
import com.kjuns.util.CommonConstants;
import com.kjuns.util.SysConf;
import com.tencent.common.Configure;
import com.tencent.common.MD5;
import com.tencent.common.RandomStringGenerator;
import com.tencent.common.XMLParser;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

public class WXPaymentOrderUtil {

	public static WXPaymentPrevOrder getTextAiDou() {
		return new WXPaymentPrevOrder("test", 1);
	}

	public static WXPaymentPrevOrder getAiDou(Integer num, Integer amount) {
		return new WXPaymentPrevOrder(CommonConstants.AIDOU_BODY.replace("{num}", num.toString()),  amount);
	}

	public static final String APPID = "wx5cfc8e1ebbe81712";
	public static final String MCH_ID = "1240087402";
	private static boolean hasInited = false;

	private static void init() {
		hasInited = true;
		Configure.setKey(SysConf.WX_PAY_KEY);
	}

	/**
	 * 获取预订单
	 * 
	 * @param userId
	 *            用户id
	 * @param tradeNo
	 *            商户订单号
	 * @param request
	 *            HttpServletRequest
	 * @param deviceInfo
	 *            硬件信息
	 * @param notifyUrl
	 *            回调地址
	 * @param order
	 *            订单
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String, Object> getPrePayId(Long userId, String tradeNo, String createIp,
			String deviceInfo, String notifyUrl, WXPaymentPrevOrder order) throws Exception {
		
		order.setAppid(APPID);
		order.setMch_id(MCH_ID);
		order.setDevice_info(deviceInfo);
		order.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
		order.setOut_trade_no(tradeNo);
		order.setSpbill_create_ip(createIp);
		order.setNotify_url(notifyUrl);
		order.setTrade_type("APP");
		String sign = getSign(order.toMap());

		order.setSign(sign);
		// 解决XStream对出现双下划线的bug
		XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
		xStreamForRequestPostData.alias("xml", WXPaymentPrevOrder.class);
		// 将要提交给API的数据对象转换成XML格式数据Post给API
		String postDataXML = xStreamForRequestPostData.toXML(order);
		String response = sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", postDataXML);

		Map<String, Object> map = XMLParser.getMapFromXML(response);
		return (HashMap<String, Object>) map;
	}

	/**
	 * 订单状态查询
	 * 
	 * @param tradeNO
	 *            商户订单号
	 * @param transactionId
	 *            微信支付订单号
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String, Object> checkOrderByTradeNoOrTransactionId(String tradeNO, String transactionId)
			throws Exception {
		WXPaymentOrderQuery query = new WXPaymentOrderQuery();
		query.setAppid(APPID);
		query.setMch_id(MCH_ID);
		if (transactionId != null) {
			query.setTransaction_id(transactionId);
		} else {
			query.setOut_trade_no(tradeNO);
		}
		query.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
		String sign = getSign(query.toMap());

		query.setSign(sign);
		XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
		xStreamForRequestPostData.alias("xml", WXPaymentOrderQuery.class);
		// 将要提交给API的数据对象转换成XML格式数据Post给API
		String postDataXML = xStreamForRequestPostData.toXML(query);

		String response = sendPost("https://api.mch.weixin.qq.com/pay/orderquery", postDataXML);
		Map<String, Object> map = XMLParser.getMapFromXML(response);
		return (HashMap<String, Object>) map;
	}

	/**
	 * 关闭的订单
	 * 
	 * @param tradeNO
	 *            商户订单号
	 * @param transactionId
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String, Object> closeOrder(String tradeNO) throws Exception {
		// 可以复用查询订单vo
		WXPaymentOrderQuery query = new WXPaymentOrderQuery();
		query.setAppid(APPID);
		query.setMch_id(MCH_ID);
		query.setOut_trade_no(tradeNO);
		query.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
		String sign = getSign(query.toMap());

		query.setSign(sign);
		XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
		xStreamForRequestPostData.alias("xml", WXPaymentOrderQuery.class);
		// 将要提交给API的数据对象转换成XML格式数据Post给API
		String postDataXML = xStreamForRequestPostData.toXML(query);

		String response = sendPost("https://api.mch.weixin.qq.com/pay/closeorder", postDataXML);
		Map<String, Object> map = XMLParser.getMapFromXML(response);
		return (HashMap<String, Object>) map;
	}

	/**
	 * 获取微信通知的消息体
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return map 字典
	 * @throws Exception
	 */
	public static Map<String, Object> getWXNotification(HttpServletRequest request) throws Exception {

		BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
		String line = "";

		StringBuffer strBuf = new StringBuffer();

		while ((line = in.readLine()) != null) {
			strBuf.append(line).append("\n");
		}

		in.close();
		String responseXml = strBuf.toString().trim();
		Map<String, Object> map = XMLParser.getMapFromXML(responseXml);
		return map;
	}

	/**
	 * 对字典签名
	 * 
	 * @param map
	 * @return
	 */
	public static String getSign(Map<String, Object> map) {// 初始化密钥
		if (!hasInited) {
			init();
		}
		ArrayList<String> list = new ArrayList<String>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() != "") {
				list.add(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		result += "key=" + Configure.getKey();
		// Util.log("Sign Before MD5:" + result);
		result = MD5.MD5Encode(result).toUpperCase();
		// Util.log("Sign Result:" + result);
		return result;
	}

	/**
	 * 通过Https往API post xml数据
	 *
	 * @param url
	 *            API地址
	 * @param xmlObj
	 *            要提交的XML数据对象
	 * @return API回包的实际数据
	 * @throws Exception
	 */
	public static String sendPost(String urlPath, String postDataXML) throws Exception {

		URL url = new URL(urlPath);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setConnectTimeout(3000); // 设置连接主机超时（单位：毫秒)

		conn.setReadTimeout(3000); // 设置从主机读取数据超时（单位：毫秒)

		conn.setDoOutput(true); // post请求参数要放在http正文内，顾设置成true，默认是false

		conn.setDoInput(true); // 设置是否从httpUrlConnection读入，默认情况下是true

		conn.setUseCaches(false); // Post 请求不能使用缓存

		// 设定传送的内容类型是可序列化的java对象(如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)

		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		conn.setRequestMethod("POST");// 设定请求的方法为"POST"，默认是GET

		conn.setRequestProperty("Content-Length", postDataXML.length() + "");

		String encode = "utf-8";

		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), encode);

		out.write(postDataXML.toString());

		out.flush();

		out.close();
		String response = getOut(conn);
		return response;
	}

	public static String getOut(HttpURLConnection conn) throws IOException {

		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {

			return null;

		}

		// 获取响应内容体

		BufferedReader in = new BufferedReader(new InputStreamReader(

		conn.getInputStream(), "UTF-8"));

		String line = "";

		StringBuffer strBuf = new StringBuffer();

		while ((line = in.readLine()) != null) {

			strBuf.append(line).append("\n");

		}

		in.close();

		return strBuf.toString().trim();

	}

}
