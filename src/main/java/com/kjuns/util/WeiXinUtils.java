package com.kjuns.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.qq.connect.utils.json.JSONException;
import com.qq.connect.utils.json.JSONObject;

public class WeiXinUtils {
    //编码格式。发送编码格式统一用UTF-8
    private static String ENCODING = "UTF-8";
	public static JSONObject checkAuth(String token,String openId) throws JSONException{
		HashMap<String, String> params = new HashMap<String, String>();
        params.put("access_token", token);
        params.put("openid", openId);
		return new JSONObject(post("https://api.weixin.qq.com/sns/auth",params));
	}
	
	public static JSONObject getUserInfo(String token,String openId) throws JSONException{
		HashMap<String, String> params = new HashMap<String, String>();
        params.put("access_token", token);
        params.put("openid", openId);
		return new JSONObject(post("https://api.weixin.qq.com/sns/userinfo",params));
	}
	
	public static String post(String url, Map<String, String> paramsMap) {
        HttpClient client = new HttpClient();
        try {
            PostMethod method = new PostMethod(url);
            if (paramsMap != null) {
                NameValuePair[] namePairs = new NameValuePair[paramsMap.size()];
                int i = 0;
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new NameValuePair(param.getKey(), param.getValue());
                    namePairs[i++] = pair;
                }
                method.setRequestBody(namePairs);
                HttpMethodParams param = method.getParams();
                param.setContentCharset(ENCODING);
            }
            client.executeMethod(method);
            return method.getResponseBodyAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
