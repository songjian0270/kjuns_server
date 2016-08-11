package com.kjuns.util.pili;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.kjuns.util.SerializeUtil;
import com.kjuns.util.SysConf;

public class PiliIO {
	
	private static final String API_BASE_URL = SysConf.PLL_BASE_URL;
	private static final String VERSION = "0.2.1";
	private static final String GET = "GET";
	private static final String POST = "POST";
	private static final String DELETE = "DELETE";
	public static final String IS_PRIVATE = "is_private";
	public static final String KEY = "key";
	public static final String COMMENT = "comment";
	public static final String PUSH_URL = "push_url";
	public static final String LIVE_URL = "live_url";

	private static PiliIO instance = null;

	private PiliIO() {}
	
	/* 保证各线程访问的均是main memory中的变量而非线程memory中的一份cache */
	public static PiliIO getInstance() {
		if (instance == null) {
			synchronized (PiliIO.class) {
				if (instance == null) {
					instance = new PiliIO();
				}
			}
		}
		return instance;
	}

	@SuppressWarnings("unused")
	private Map<String, String> setHeaders(String url, String body) throws Exception {
		Map<String, String> key = new HashMap<String, String>();
		key.put("Content-Type", "application/json");
		key.put("user-agent", Utils.getUserAgent(VERSION));
		key.put("Authorization", Utils.signRequests(SysConf.PLL_ACCESS_KEY, SysConf.PLL_SECRET_KEY, url, body));
		return key;
	}

	public String request(String method, String url) throws Exception {
		return request(method, url, null);
	}

	private String session_value;

	private String request(String method, String url, String body) throws Exception {
		URL console = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) console.openConnection();

		// Map<String, String> header = this.setHeaders(url, body);

		// Iterator<String> it = header.keySet().iterator();
		// while (it.hasNext()) {
		// String key = it.next().toString();
		// String value = header.get(key);
		// conn.setRequestProperty(key, value);
		// }
		conn.setRequestProperty("Cookie", "JSESSIONID=" + session_value);
		conn.setRequestMethod(method);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		// BufferedOutputStream hurlBufOus = new BufferedOutputStream(
		// conn.getOutputStream());
		// hurlBufOus.write(body.toString().getBytes());
		// hurlBufOus.flush();

		InputStream is = conn.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		if (conn.getHeaderField("Set-Cookie") != null) {
			session_value = conn.getHeaderField("Set-Cookie").substring("JSESSIONID=".length(),
					"D9600D0B58A78C1450A8D6EE463ACCE7".length() + "JSESSIONID=".length());
		} else {
			session_value = "";
		}
		return sb.toString();
	}

	public String listStreams() throws Exception {
		String url = API_BASE_URL + "streams";
		return this.request(GET, url);
	}

	public String getStream(String streamId) throws Exception {
		String url = API_BASE_URL + "streams/" + streamId;
		return this.request(GET, url);
	}

	public String setStream(String streamId, HashMap<String, Object> hashMap) throws Exception {
		String url = API_BASE_URL + "streams/" + streamId;
		String body = hashMap == null ? "{}" : SerializeUtil.serializeStr(hashMap);
		return this.request(POST, url, body);
	}

	public String delStream(String streamId) throws Exception {
		String url = API_BASE_URL + "streams/" + streamId;
		return request(DELETE, url);
	}

	public String getStreamStatus(String streamId) throws Exception {
		String url = API_BASE_URL + "streams/" + streamId + "/status";
		return this.request(GET, url);
	}

	public String getStreamSegments(String streamId, long startTime, long endTime) throws Exception {
		String url = API_BASE_URL + "streams/" + streamId + "/segments?starttime=" + startTime + "&endtime=" + endTime;
		return this.request(GET, url);
	}

	public String playStreamSegments(String streamId, long startTime, long endTime) throws Exception {
		String url = API_BASE_URL + "streams/" + streamId + "/segments/play?starttime=" + startTime + "&endtime="
				+ endTime;
		return this.request(GET, url);
	}

	public String delStreamSegments(String streamId, String startTime, String endTime) throws Exception {
		String url = API_BASE_URL + "streams/" + streamId + "/segments?starttime=" + startTime + "&endtime=" + endTime;
		return this.request(DELETE, url);
	}

	public String createStream(HashMap<String, Object> hashMap) throws Exception {
		String url = API_BASE_URL + "streams";
		String body = hashMap == null ? "{}" : SerializeUtil.serializeStr(hashMap);
		return this.request("POST", url, body);
	}

	public String signPushUrl(String url, String streamKey, String nonce) {
		if (nonce == null) {
			nonce = String.valueOf(System.currentTimeMillis());
		}
		url += "?nonce=" + nonce;
		url += "&token=" + Utils.sign(streamKey, url);
		return url;
	}

	public String signPlayUrl(String url, String streamKey, String expiry) {
		url += "?expiry=" + expiry;
		url += "&token=" + Utils.sign(streamKey, url);
		return url;
	}

	public static void main(String[] args) throws Exception {

		PiliIO pli = PiliIO.getInstance();
		for (int i = 0; i < 40000; i++) {
			Thread.sleep(100);
			pli.request("GET", "http://7u2fe1.com2.z0.glb.qiniucdn.com/extend_1344f59bb99a19c327679d0e7a85c7e9");
			System.out.println(i);
		}

	}
}
