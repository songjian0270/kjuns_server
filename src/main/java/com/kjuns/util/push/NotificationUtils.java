package com.kjuns.util.push;

import com.kjuns.util.SysConf;
import com.kjuns.util.push.android.AndroidBroadcast;
import com.kjuns.util.push.ios.IOSCustomizedcast;

@SuppressWarnings("unused")
public class NotificationUtils {
	
	private static String appkey = null;
	private static String appMasterSecret = null;
	private static String timestamp = null;
	
	//是否启用
	private static String production_mode = "true";
	
	private static String IOS = "ios";
	private static String ANDROID = "android";
	
	static {
		try {
			appkey = SysConf.UM_KEY;
			appMasterSecret = SysConf.UM_SECRET;
			timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	@SuppressWarnings("static-access")
	public void sendAliascast(long alias,String aliasType,String msg, String type) throws Exception {
		switch (type) {
		case "ios":
			IOSCustomizedcast customizedcast = new IOSCustomizedcast();
			customizedcast.setAppMasterSecret(appMasterSecret);
			customizedcast.setPredefinedKeyValue("appkey", this.appkey);
			customizedcast.setPredefinedKeyValue("timestamp", this.timestamp);
			customizedcast.setPredefinedKeyValue("alias", alias);
			customizedcast.setPredefinedKeyValue("alias_type", aliasType);
			customizedcast.setPredefinedKeyValue("alert", msg);
			customizedcast.setPredefinedKeyValue("badge", 1);
			customizedcast.setPredefinedKeyValue("sound", "chime");
			customizedcast.setPredefinedKeyValue("production_mode", production_mode);
			customizedcast.send();
			break;
		case "android":
			AndroidBroadcast broadcast = new AndroidBroadcast();
			broadcast.setAppMasterSecret(appMasterSecret);
			broadcast.setPredefinedKeyValue("appkey", this.appkey);
			broadcast.setPredefinedKeyValue("timestamp", this.timestamp);
			broadcast.setPredefinedKeyValue("alias", alias);
			broadcast.setPredefinedKeyValue("alias_type", aliasType);
			broadcast.setPredefinedKeyValue("alert", msg);
			broadcast.setPredefinedKeyValue("badge", 1);
			broadcast.setPredefinedKeyValue("sound", "chime");
			broadcast.setPredefinedKeyValue("production_mode", production_mode);
			broadcast.send();
			break;
		default:
			break;
		}
	}

}
