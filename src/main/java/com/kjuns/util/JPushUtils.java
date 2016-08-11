 package com.kjuns.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * <b>Function: </b>     
 * @author James
 * @date 2015-9-8
 * @file JPushUtils.java
 * @package com.kjuns.util
 * @project kjuns
 * @version 2.0
 */
public class JPushUtils {
	
	private static Logger logger = LoggerFactory.getLogger(JPushUtils.class);
	
	private static final String APP_KEY = "f28f1ca5af67f1ba5e0c2242";
	private static final String MASTER_SECRET = "b608f38e60b7bca46818d2ac"; //Portal上注册应用时生成的 masterSecret

	private static JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, 3);
	
	
	private static Options options = getPushOptions();
	
	private static Options getPushOptions(){
		boolean proEnv = "1".equals(SysConf.ENV) ? true : false;//false 表示要推送测试环境
		return Options.newBuilder().setApnsProduction(proEnv).build();
	}
	
	
//	//alias  指定标记Audience
//	public static boolean sendCustomMsgByAlias(String alias, String content, String title){
//		 boolean res = true;
//		// PushPayload payload = buildPushObject_android_and_ios(alias, content, title);
//		// PushPayload payload = buildPushObject_all_all_alert("生日快乐");
//		// PushPayload payload = buildPushObject_all_alias_alerts("设置成功");
//		 PushPayload payload = buildPushObject_android_and_ios(new String[]{alias}, content, title);
//	        try {
//	            PushResult result = jpushClient.sendPush(payload);
//	            logger.info("Got result - " + result);
//	            logger.info("对alias[{}]发推送，Got result:{}", alias, result);
//	        } catch (APIConnectionException e) {
//	        	res = false;
//	        	logger.error("Connection error. Should retry later. ", e);
//	        } catch (APIRequestException e) {
//	        	res = false;
//	        	logger.error("Error response from JPush server. Should review and fix it. ", e);
//	        	logger.info("HTTP Status:{}", e.getStatus());
//	        	logger.info("Error Code:{}", e.getErrorCode());
//	        	logger.info("Error Message:{}", e.getErrorMessage());
//	        }
//		return res;
//	}
	
	public static boolean sendCustomMsgByMutilAlias(String[] alias, String content, String title,String liveId){
		 boolean res = true;
		// PushPayload payload = buildPushObject_android_and_ios(alias, content, title);
		// PushPayload payload = buildPushObject_all_all_alert("生日快乐");
		// PushPayload payload = buildPushObject_all_alias_alerts("设置成功");
		 PushPayload payload = buildPushLive_android_and_ios(alias, content, title,liveId);
	        try {
	            PushResult result = jpushClient.sendPush(payload);
	            logger.info("Got result - " + result);
	            logger.info("对alias[{}]发推送，Got result:{}", alias, result);
	        } catch (APIConnectionException e) {
	        	res = false;
	        	logger.error("Connection error. Should retry later. ", e);
	        } catch (APIRequestException e) {
	        	res = false;
	        	logger.error("Error response from JPush server. Should review and fix it. ", e);
	        	logger.info("HTTP Status:{}", e.getStatus());
	        	logger.info("Error Code:{}", e.getErrorCode());
	        	logger.info("Error Message:{}", e.getErrorMessage());
	        }
		return res;
	}
	
//	private static PushPayload buildPushObject(String alias,String content, String orderNo,String flag){
//		AndroidNotification androidNotification = AndroidNotification.newBuilder()
//				.addExtra("orderNo",orderNo).addExtra("flag",flag).build();
//		IosNotification iosNotification = IosNotification.newBuilder()
//				.addExtra("orderNo",orderNo).addExtra("flag",flag).setSound("default").build();
//		Notification notification = Notification.newBuilder()
//				.setAlert(content)
//				.addPlatformNotification(androidNotification)
//				.addPlatformNotification(iosNotification)
//				.build();
//		PushPayload payLoad = PushPayload.newBuilder()
//                .setPlatform(Platform.all())
//                .setAudience(Audience.alias(alias))
//                .setNotification(notification)
//                .setOptions(options)
//                .build();
//		
//		return payLoad;
//	}
	
//	/*** 所有平台，所有设备，内容为 ALERT 的通知 **/
//    public static PushPayload buildPushObject_all_all_alert(String alert) {  
//        return PushPayload.alertAll(alert);  
//    }  
//      
//    /*** 所有平台，通知内容为 ALERT **/
//    public static PushPayload buildPushObject_all_alias_alert(String alert) {  
//        return PushPayload.newBuilder()  
//                .setPlatform(Platform.all())//设置接受的平台  
//                .setAudience(Audience.all())//Audience设置为all，说明采用广播方式推送，所有用户都可以接收到  
//                .setNotification(Notification.alert(alert))
//                .setOptions(options)
//                .build();  
//    }  
//    
//    /*** 所有平台，通知内容为 ALERT **/
//    public static PushPayload buildPushObject_all_alias_alerts(String alert) {  
//        return PushPayload.newBuilder()  
//                .setPlatform(Platform.all())//设置接受的平台  
//                .setAudience(Audience.tag("1211wanguyuguang122")) 
//                .setNotification(Notification.alert(alert))
//                .setOptions(options)
//                .build();  
//    }  
//    
//    /***所有平台，通知内容为 alert 标题为 title **/
//    public static PushPayload buildPushObject_android_tag_alertWithTitle(String alert, String title) {  
//        return PushPayload.newBuilder()  
//                .setPlatform(Platform.android())  
//                .setAudience(Audience.all())  
//                .setNotification(Notification.android(alert, title, null))  
//                .setOptions(options)
//                .build();  
//    }  
      
//    /*** 平台是 Android IOS，目标是 tag 为 "tag1" 的设备 **/
//    public static PushPayload buildPushObject_android_and_ios(String[] alias, String alert, String title) {  
//        return PushPayload.newBuilder()  
//                .setPlatform(Platform.android_ios())  
//               // .setAudience(Audience.alias(alias))  
//                .setAudience(Audience.alias(alias))
//                .setNotification(Notification.newBuilder()  
//                        .setAlert(alert)  
//                        .addPlatformNotification(AndroidNotification.newBuilder()  
//                                .setTitle(title).build())  
//                        .addPlatformNotification(IosNotification.newBuilder()  
//                                .incrBadge(1)
//                                .setSound("Remote.mp3")  
//                                .addExtra("extra_key", "extra_value").build())  
//                        .build())  
//                .setOptions(options)
//                .build();  
//    }  
    public static PushPayload buildPushLive_android_and_ios(String[] alias, String alert, String title,String liveId) {  
        return PushPayload.newBuilder()  
                .setPlatform(Platform.android_ios())  
               // .setAudience(Audience.alias(alias))  
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder()  
                        .setAlert(alert)  
                        .addPlatformNotification(AndroidNotification.newBuilder()  
                                .setTitle(title)
                                .addExtra("url", "kjunsproject://live/"+liveId).build())  
                        .addPlatformNotification(IosNotification.newBuilder()  
                                .incrBadge(1)  
                                .setSound("SoundEffectTypeNotifi.mp3")  
                                .addExtra("url", "kjunsproject://live/"+liveId).build())  
                        .build())  
                .setOptions(options)
                .build();  
    }  
      
//    /*** 平台是 iOS，推送目标是 "tag1", "tag_all" 的交集
//     * 	 通知信息是 alert，角标数字为 5，通知声音为 "happy"，并且附加字段 from = "JPush"；消息内容是 msgContent
//     ***/
//    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage(String alert, String msgContent) {  
//        return PushPayload.newBuilder()  
//                .setPlatform(Platform.ios())  
//                .setAudience(Audience.tag_and("tag1", "tag_all"))  
//                .setNotification(Notification.newBuilder()  
//                        .addPlatformNotification(IosNotification.newBuilder()  
//                                .setAlert(alert)  
//                                .setBadge(5)  
//                                .setSound("happy")  
//                                .addExtra("from", "JPush")  
//                                .build())  
//                        .build())  
//                 .setMessage(Message.content(msgContent))  
//                 .setOptions(options)  
//                 .build();  
//    }  
//      
//    /*** 平台是 Andorid 与 iOS，推送目标是 "tag1" 与 "tag2" 的并集且 "alias1" 与 "alias2" 的并集
//     * 	 推送内容是 - 内容为 MSG_CONTENT 的消息，并且附加字段 from = JPush
//     ***/
//    public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras(String msgContent) {  
//        return PushPayload.newBuilder()  
//                .setPlatform(Platform.android_ios())  
//                .setAudience(Audience.newBuilder()  
//                        .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))  
//                        .addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))  
//                        .build())  
//                .setMessage(Message.newBuilder()  
//                        .setMsgContent(msgContent)  
//                        .addExtra("from", "JPush")  
//                        .build())  
//                .setOptions(options)  
//                .build();  
//    }  
	
	
//	public static void main(String[] args) {
//		String currTime = CommonConstants.DATETIME_SEC.format(new Date());
//		sendCustomMsgByAlias("6c05b69e2bfd401fbc349b48b534a316", currTime+"：推送测试一，二，三，四，五，六，七，八，九，十，十一，十二，.......", "123456");
//		System.out.println("发送时间："+currTime);
//	}
}
