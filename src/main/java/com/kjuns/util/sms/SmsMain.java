package com.kjuns.util.sms;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.axis.client.Call;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kjuns.util.CommonConstants;
import com.kjuns.util.CommonUtils;
import com.kjuns.util.SysConf;
import com.kjuns.util.db.SmsLogger;
import com.kjuns.util.sms.common.ISms;
import com.kjuns.util.sms.common.SmsTool;

/**
 * <b>Function: </b>  
 * @author James     
 * @date 2015-04-12
 * @file SmsMain.java
 * @package com.gtbang.util.sms
 * @project gtbang
 * @version 1.0
 */
public class SmsMain {

	
	private static Logger logger = LoggerFactory.getLogger(SmsMain.class);
	
	//APP SERVER(注册、密码找回验证码;交易信息)
	public static final String SMS_USERNAME = SysConf.SMS_USER;//"JC2364";
    public static final String SMS_PASSWORD = SysConf.SMS_PWD;//"622301";
    
	private static final String SUCCESS = "success";
	
	private static final String FAILURE = "failure";
	
	private static final String ERROR = "error";
    
    private static ISms sms = null;
    
    private static boolean bKeepAlive = false;
    
	private static Call call=null; 

	private static int WARN_COUNT = SysConf.SMS_LIMIT;
	
	private static ExecutorService executor = Executors.newCachedThreadPool();
    
    static {
    	if(sms == null){
    		sms = new CHttpSoap();
    	}
    }
   
    /**
     * 余额提示接口
     * @return
     */
    public static String getSurplusSmsCount(String[] mobiles, String reqIp){
    	String resultString = null;
    	try {
        	StringBuffer nBalance=new StringBuffer("");
    		int result = sms.QueryBalance(nBalance, SysConf.SMS_SERVICE_URL, SMS_USERNAME, SMS_PASSWORD, bKeepAlive, call);
    		logger.error(result+"");
    		if(result == 0){
    			int mongateQueryBalance = Integer.parseInt(nBalance.toString());
    			if(mongateQueryBalance > WARN_COUNT){
    				if(CommonUtils.notEmpty(mobiles)){
    					String warnTxt = "员工您好，感谢您对此次测试的配合。";
    					for (String mobile : mobiles) {
    						try {
    							sendSms("webapp", mobile, warnTxt, 5, 3, reqIp);	
    						} catch (Exception e) {
    							logger.error("",e);
    						}
    					}
    				}
    			}
    		}
    	} catch (Exception e) {
    		logger.error("",e);
    	}
    	return resultString;
    }
    
    /**
     * 短信息发送接口（相同内容群发，可自定义流水号）
     * @param mobile  手机号码英文,隔开
     * @param content 发送内容
     * @param remark  描述
     * @param sender  发送短信的系统，1：网站，2：管理后台，3：APP服务端，4:手动发送，5：短信接口（余量警报）,6:CRM
     * @param type    1：用户注册验证码，2：“忘记密码”短信验证码，3：消息通知
     * @param reqIp   请求IP
     * @return
     * @throws Exception
     */
    public static String sendSms(String channelType, String mobile, String content, int sender, int type, String reqIp) throws Exception{
		String sendResult = null;
    	String resultString = "";
		String createTime = null;
    	try {
    		if(CommonUtils.notEmpty(mobile)){
    			 String [] mobiles = mobile.split(",");
    			 for(String mb: mobiles){
    				 if(CommonUtils.notEmpty(mb) && mb.length() == 11 && SmsTool.isUnSignDigit(mb) ){
    			    		StringBuffer ptmsg = new StringBuffer("");
    			    		int result=0;
    			    		String userName = "";
    			    		String sendUrl = "";
    			    		switch (channelType) {
							case "webapp":
								userName = SMS_USERNAME;
								sendUrl = SysConf.SMS_SERVICE_URL;
								if("1".equals(SysConf.SMS_ENV)){
									result=sms.SendSms(ptmsg, SysConf.SMS_SERVICE_URL, SMS_USERNAME, SMS_PASSWORD, mb, content, "*", "0", bKeepAlive, call);
								}else{
									ptmsg.append("压力测试");
								}
								break;
							default:
								break;
							}
    			    		createTime = CommonConstants.DATETIME_SEC.format(new Date());
    			    		if(result==0){
    			    			 sendResult= SUCCESS;
    	    		    		 logger.error("sendSms >> {} :短信发送成功 ......", mb);
    			    		}else{
    			    			 sendResult= FAILURE;
    	    		    		 logger.error("sendSms >> {} :{} ......", mb, errorCode(result));
    			    		}
    			    		executor.execute(new SmsLogger(mb, sender, type, content, errorCode(result) + "[" + sendUrl + "]"+"[" + userName + "]" + "[" + ptmsg + "]", reqIp, createTime));
    				 }else{
    					 sendResult= ERROR;
    		    		 logger.error("sendSms >> {} :无效手机号码 ......", mb);
    		    		 resultString = "发送数据有误";
    		 	    	 executor.execute(new SmsLogger(mb, sender, type, content, resultString, reqIp, createTime));
    				 }
    			 }
    		}else{
    			sendResult= ERROR;
    			logger.error("sendSms 手机串不能为空......");
    		}
		} catch (Exception ex) {
			sendResult= ERROR;
			logger.error("sendSms 手机发送失败：{}" ,ex.getMessage());
		}
    	return sendResult;
    }
    
    private static String errorCode(Integer code){
    	switch (code) {
    	case 0:
			return "成功";
		case -1:
			return "参数为空。信息、电话号码等有空指针，登陆失败";
		case -12:
			return "异常电话号码";
		case -14:
			return "实际号码个数超过100";
		case -999:
			return "服务器内部错误";
		case -10001:
			return "用户登陆不成功(帐号不存在/停用/密码错误)";
		case -10002:
			return "参数错误";
		case -10003:
			return "用户余额不足";
		case -10011:
			return "信息内容超长";
		case -10029:
			return "此用户没有权限从此通道发送信息(用户没有绑定该性质的通道，比如：用户发了小灵通的号码)";
		case -10030:
			return "不能发送移动号码";
		case -10031:
			return "手机号码(段)非法";
		case -10057:
			return "IP受限";
		case -10056:
			return "连接数超限";
		default:
			return "服务器异常：" + code;
		}
    }
     
    public static void main(String[] args) throws Exception {
    	//getSurplusSmsCount(new String[]{"15001798048"}, "127.0.0.1");
    	sendSms("webapp", "15001798048", "加入看军事,我们一起驰骋星辰大海:2525", 3, 1, "127.0.0.1");
	}
   
}
