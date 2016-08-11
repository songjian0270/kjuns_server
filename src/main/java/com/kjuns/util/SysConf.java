package com.kjuns.util; 

import java.util.Locale;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * <b>Function: </b> 常量配置
 * @author James
 * @date 2015-07-28
 * @file SysConf.java
 * @package com.kjuns.util
 * @project kjuns
 * @version 2.0
 */
public class SysConf {
	
	private static Logger logger = LoggerFactory.getLogger(SysConf.class);
	
	private static final String CONF = "conf";
	
	private static final String REDIS = "redis";
	
	private static final String JDBC = "jdbc";
	
	private static final String YUNTONGXUN = "yuntongxun";
	
	private static final String UMENG = "umeng";
	
	private static final String WXPAY = "wxpay";
	
	/** 1:正式环境，2：测试环境   */
	public static final boolean ENV = getEnv();
	
	//===================== 日志文件相关   ============================================================
	/** 日志文件目录  */
	public static final String LOG_PATH = SysConf.getBundleString(CONF, "logPath");
	/** 日志文件目录文件数量  */
	public static final Integer LOG_FILE_CNT = getLogFileCnt(); 

	//===================== 备用域名设置   ============================================================
	/** 备用主域名  */
	public static final String BACK_MASTER_SERVER_URL = SysConf.getBundleString(CONF, "back_master_server_url");
	/** 备用搜索域名 */
	public static final String BACK_SEARCH_SERVER_URL = SysConf.getBundleString(CONF, "back_search_server_url");
	public static final String SIMULATION = SysConf.getBundleString(CONF, "simulation");
	
	//===================== im 环信   ============================================================
	public static Integer TTN_RATE = SysConf.getBundleInteger(CONF, "nnt_rate");
	// API_SERVER_HOST
	public static String IM_API_SERVER_HOST = SysConf.getBundleString(CONF, "im_api_server_host");
	// APPKEY
	public static String IM_APP_KEY = SysConf.getBundleString(CONF, "im_app_key");
	// APP_CLIENT_ID
	public static String IM_APP_CLIENT_ID = SysConf.getBundleString(CONF, "im_app_client_id");
	// APP_CLIENT_SECRET
	public static String IM_APP_CLIENT_SECRET = SysConf.getBundleString(CONF, "im_app_client_secret");

	//===================== jdbc设置   ============================================================
	/** jdbc.url  */
	public static final String JDBC_URL = SysConf.getBundleString(JDBC, "user.db.url");
	/** jdbc.username  */
	public static final String JDBC_USERNAME = SysConf.getBundleString(JDBC, "user.db.user");
	/** jdbc.password  */
	public static final String JDBC_PWD = SysConf.getBundleString(JDBC, "user.db.password");
	
	//=====================  2.1首页显示常量配置   ============================================================
	/** 潜力kjuns显示常量 */
	public static final Integer CAPACITY_kjuns = SysConf.getBundleInteger(CONF, "capacity_kjuns"); 
	/** 新入生显示常量 */
	public static final Integer NEW_kjuns = SysConf.getBundleInteger(CONF, "new_kjuns"); 
	/** 最排行显示常量 */
	public static final Integer RANKING_NUM = SysConf.getBundleInteger(CONF, "ranking_num"); 
	
	//=====================  微信   ============================================================ 
	/** 微信JSSDK签名app */
	public static final String WX_JS_SDK_APP_ID = SysConf.getBundleString(CONF, "wx_js_sdk_app_id"); 
	/** 微信JSSDK签名secret */
	public static final String WX_JS_SDK_APP_SECRET= SysConf.getBundleString(CONF, "wx_js_sdk_app_secret"); 
	/** 是否启用微信支付  */
	public static final String WX_PAYMENT_ENABLE = SysConf.getBundleString(CONF, "wx_payment_enable"); 
	/** 支付回调接口地址  */
	public static final String WX_PAYMENT_NOTIFICATION = SysConf.getBundleString(CONF, "wx_payment_notification"); 
	/** 客户端轮询时间设置 */
	public static final Integer UN_READ_POLLINGTIME_INTERVAL =SysConf.getBundleInteger(CONF, "unReadPollingTimeInterval"); 
	/** 交易兑现额度 */
	public static final Integer WX_PAYMENT_RATIO =SysConf.getBundleInteger(CONF, "wx_payment_ratio"); 
	
	//=====================  阿里   ============================================================
	/** 阿里partnerId */
	public static final String ALI_PARTNER_ID = SysConf.getBundleString(CONF, "ali_partner_id"); 
	/** 阿里seller_email */
	public static final String ALI_SELLER_EMAIL = SysConf.getBundleString(CONF, "ali_seller_email"); 
	/** 阿里 private secret */
	public static final String ALI_PRIVATE_SECRET= SysConf.getBundleString(CONF, "ali_private_secret"); 
	/** 阿里 public secret */
	public static final String ALI_PUBLIC_SECRET= SysConf.getBundleString(CONF, "ali_public_secret"); 
	/** 是否启用阿里支付  */
	public static final String ALI_PAYMENT_ENABLE = SysConf.getBundleString(CONF, "ali_payment_enable"); 
	/** 阿里支付回调通知地址  */
	public static final String ALI_PAYMENT_NOTIFICATION = SysConf.getBundleString(CONF, "ali_payment_notification"); 
	/** 阿里交易兑现额度 */
	public static final Integer ALI_PAYMENT_RATIO =SysConf.getBundleInteger(CONF, "ali_payment_ratio");
	
	//=====================  经验配置信息   ==========================================================
	/** 练习生发动态 */
	public static final Long kjuns_SEND_CONTENT_EXP = SysConf.getBundleLong(CONF, "kjunsSendContentExp");
	/** 练习生回复他人评论 */
	public static final Long kjuns_REPLY_CONTENT_EXP = SysConf.getBundleLong(CONF, "kjunsReplyContentExp");
	/** 被喂豆 */
	public static final Long kjuns_PASSIVE_DEAL_EXP = SysConf.getBundleLong(CONF, "kjunsPassiveDealExp");
	/** 被分享 */
	public static final Long kjuns_PASSIVE_SHARE_EXP = SysConf.getBundleLong(CONF, "kjunsPassiveShareExp");
	/**	被关注 */
	public static final Long kjuns_PASSIVE_FOCUS_EXP = SysConf.getBundleLong(CONF, "kjunsPassiveFocusExp");
	/** 被评论 */
	public static final Long kjuns_PASSIVE_COMMENT_EXP = SysConf.getBundleLong(CONF, "kjunsPassiveCommentExp");
	/** 被点赞 */
	public static final Long kjuns_PASSIVE_LIKE_EXP = SysConf.getBundleLong(CONF, "kjunsPassiveLikeExp");
	
	/** 练习生发动态单日上限 */
	public static final Long MAX_kjuns_SEND_CONTENT_EXP = SysConf.getBundleLong(CONF, "maxkjunsSendContentExp");
	/** 练习生分享单日上限 */
	public static final Long MAX_kjuns_PASSIVE_SHARE_EXP = SysConf.getBundleLong(CONF, "maxkjunsPassiveShareExp");
	/** 练习生关注单日上限 */
	public static final Long MAX_kjuns_PASSIVE_FOCUS_EXP = SysConf.getBundleLong(CONF, "maxkjunsPassiveFocusExp");
	/** 练习生评论单日上限 */
	public static final Long MAX_kjuns_PASSIVE_COMMENT_EXP = SysConf.getBundleLong(CONF, "maxkjunsPassiveCommentExp");
	/** 练习生点赞单日上限 */
	public static final Long MAX_kjuns_PASSIVE_LIKE_EXP = SysConf.getBundleLong(CONF, "maxkjunsPassiveLikeExp");
	
	/** 粉丝喂豆 */
	public static final Long USER_DEAL_EXP = SysConf.getBundleLong(CONF, "userDealExp");
	/** 粉丝分享 */
	public static final Long USER_SHAR_EXP = SysConf.getBundleLong(CONF, "userShareExp");
	/** 粉丝评论 */
	public static final Long USER_COMMENT_EXP = SysConf.getBundleLong(CONF, "userCommentExp");
	/** 粉丝评论人 */
	public static final Long USER_PERSON_EXP = SysConf.getBundleLong(CONF, "userPersonExp");
	/** 粉丝点赞 */
	public static final Long USER_LIKE_EXP = SysConf.getBundleLong(CONF, "userLikeExp");
	
	/** 粉丝分享单日上限 */
	public static final Long MAX_USER_SHARE_EXP = SysConf.getBundleLong(CONF, "maxUserShareExp");
	/** 粉丝评论人单日上限 */
	public static final Long MAX_USER_COMMENT_EXP = SysConf.getBundleLong(CONF, "maxUserCommentExp");
	/** 粉丝点赞单日上限 */
	public static final Long MAX_USER_LIKE_EXP = SysConf.getBundleLong(CONF, "maxUserLikeExp");

	/** 签到1~5级经验 第一种经验*/
	public static final Long SIGN_EXP_1_TO_5_0 = SysConf.getBundleLong(CONF, "sign_exp_1_to_5_0");
	/** 签到1~5级经验 第二种经验*/
	public static final Long SIGN_EXP_1_TO_5_1 = SysConf.getBundleLong(CONF, "sign_exp_1_to_5_1");
	/** 签到1~5级经验 第三种经验*/
	public static final Long SIGN_EXP_1_TO_5_2 = SysConf.getBundleLong(CONF, "sign_exp_1_to_5_2");
	
	/** 签到5~11级经验 第一种经验*/
	public static final Long SIGN_EXP_5_TO_11_0 = SysConf.getBundleLong(CONF, "sign_exp_5_to_11_0");
	/** 签到5~11级经验 第二种经验*/
	public static final Long SIGN_EXP_5_TO_11_1 = SysConf.getBundleLong(CONF, "sign_exp_5_to_11_1");
	/** 签到5~11级经验 第三种经验*/
	public static final Long SIGN_EXP_5_TO_11_2 = SysConf.getBundleLong(CONF, "sign_exp_5_to_11_2");
	
	/** 签到11~max级经验 第一种经验*/
	public static final Long SIGN_EXP_11_TO_MAX_0 = SysConf.getBundleLong(CONF, "sign_exp_11_to_max_0");
	/** 签到11~max级经验 第二种经验*/
	public static final Long SIGN_EXP_11_TO_MAX_1 = SysConf.getBundleLong(CONF, "sign_exp_11_to_max_1");
	/** 签到11~max级经验 第三种经验*/
	public static final Long SIGN_EXP_11_TO_MAX_2 = SysConf.getBundleLong(CONF, "sign_exp_11_to_max_2");
	
	//=====================  设备相关信息   ==========================================================
	/** 是否允许jspatch更新  */
	public static final String JSPATCH_CHECK = SysConf.getBundleString(CONF, "jspatch_check"); 
	/** 是否强制更新  */
	public static final String IOS_IS_UPDATE = SysConf.getBundleString(CONF, "ios_is_update"); 
	/** 是否强制更新  */
	public static final String ANDRIOD_IS_UPDATE = SysConf.getBundleString(CONF, "andriod_is_update"); 
	/** 最新版本号[ios]  */
	public static final String IOS_LATEST_VER = SysConf.getBundleString(CONF, "ios_latest_ver"); 
	/** 最新版本号[andriod]  */
	public static final String ANDRIOD_LATEST_VER = SysConf.getBundleString(CONF, "andriod_latest_ver"); 
	/** 下载apk包地址[ios]  */
	public static final String IOS_DOWN_LINK = SysConf.getBundleString(CONF, "ios_down_link"); 
	/** 下载apk包地址[andriod]  */
	public static final String ANDRIOD_DOWN_LINK = SysConf.getBundleString(CONF, "andriod_down_link"); 
	/** 更新描述[ios]  */
	public static final String IOS_DISCRIPTION = SysConf.getBundleString(CONF, "ios_discription"); 
	/** 更新描述[andriod]  */
	public static final String ANDRIOD_DISCRIPTION = SysConf.getBundleString(CONF, "andriod_discription"); 
	
	/** 等级版本号[level]  */
	public static final Double LEVEL_VER = SysConf.getBundleDouble(CONF, "level_ver"); 
	
	//===================== REDIS相关   ============================================================
	/** REDIS地址0  */
	public static final String REDIS_HOST0 = SysConf.getBundleString(REDIS, "redis.host0"); 
	/** REDIS端口0  */
	public static final Integer REDIS_PORT0 = SysConf.getBundleInteger(REDIS, "redis.port0"); 
	/** REDIS地址1  */
	public static final String REDIS_HOST1 = SysConf.getBundleString(REDIS, "redis.host1"); 
	/** REDIS端口1  */
	public static final Integer REDIS_PORT1 = SysConf.getBundleInteger(REDIS, "redis.port1"); 
	/** REDIS地址2  */
	public static final String REDIS_HOST2 = SysConf.getBundleString(REDIS, "redis.host2"); 
	/** REDIS端口2  */
	public static final Integer REDIS_PORT2 = SysConf.getBundleInteger(REDIS, "redis.port2"); 
	/** REDIS地址3  */
	public static final String REDIS_HOST3 = SysConf.getBundleString(REDIS, "redis.host3"); 
	/** REDIS端口3  */
	public static final Integer REDIS_PORT3 = SysConf.getBundleInteger(REDIS, "redis.port3"); 
	/** REDIS地址3  */
	public static final String REDIS_HOST4 = SysConf.getBundleString(REDIS, "redis.host4"); 
	/** REDIS端口3  */
	public static final Integer REDIS_PORT4 = SysConf.getBundleInteger(REDIS, "redis.port4"); 
	/** REDIS地址3  */
	public static final String REDIS_HOST5 = SysConf.getBundleString(REDIS, "redis.host5"); 
	/** REDIS端口3  */
	public static final Integer REDIS_PORT5 = SysConf.getBundleInteger(REDIS, "redis.port5"); 
	/** REDIS地址3  */
	public static final String REDIS_HOST6 = SysConf.getBundleString(REDIS, "redis.host6"); 
	/** REDIS端口3  */
	public static final Integer REDIS_PORT6 = SysConf.getBundleInteger(REDIS, "redis.port6"); 
	/** REDIS地址4  */
	public static final String REDIS_HOST7 = SysConf.getBundleString(REDIS, "redis.host7"); 
	/** REDIS端口4  */
	public static final Integer REDIS_PORT7 = SysConf.getBundleInteger(REDIS, "redis.port7"); 
	/** REDIS地址4  */
	public static final String REDIS_HOST8 = SysConf.getBundleString(REDIS, "redis.host8"); 
	/** REDIS端口4  */
	public static final Integer REDIS_PORT8 = SysConf.getBundleInteger(REDIS, "redis.port8"); 
	/** REDIS MASTER1  */
	public static final String REDIS_SHARD0 = SysConf.getBundleString(REDIS, "redis.shard0"); 
	/** REDIS MASTER1  */
	public static final String REDIS_SHARD1 = SysConf.getBundleString(REDIS, "redis.shard1"); 
	/** REDIS MASTER2  */
	public static final String REDIS_SHARD2 = SysConf.getBundleString(REDIS, "redis.shard2"); 
	/** REDIS MASTER3  */
	public static final String REDIS_SHARD3 = SysConf.getBundleString(REDIS, "redis.shard3"); 
	/** REDIS MASTER4  */
	public static final String REDIS_SHARD4 = SysConf.getBundleString(REDIS, "redis.shard4"); 
	/** REDIS SENTINEL地址1  */
	public static final String REDIS_SENTINEL_HOST1 = SysConf.getBundleString(REDIS, "redis.sentinel1.host"); 
	/** REDIS SENTINEL地址2  */
	public static final String REDIS_SENTINEL_HOST2 = SysConf.getBundleString(REDIS, "redis.sentinel2.host");
	/** REDIS SENTINEL地址3  */
	public static final String REDIS_SENTINEL_HOST3 = SysConf.getBundleString(REDIS, "redis.sentinel3.host");
	/** REDIS SENTINEL地址4  */
	public static final String REDIS_SENTINEL_HOST4 = SysConf.getBundleString(REDIS, "redis.sentinel4.host"); 
	/** REDIS SENTINEL地址3  */
	public static final String REDIS_SENTINEL_HOST5 = SysConf.getBundleString(REDIS, "redis.sentinel5.host");
	/** REDIS SENTINEL地址4  */
	public static final String REDIS_SENTINEL_HOST6 = SysConf.getBundleString(REDIS, "redis.sentinel6.host"); 
	/** REDIS SENTINEL端口1  */
	public static final String REDIS_SENTINEL_PORT1 = SysConf.getBundleString(REDIS, "redis.sentinel1.port"); 
	/** REDIS SENTINEL端口2  */
	public static final String REDIS_SENTINEL_PORT2 = SysConf.getBundleString(REDIS, "redis.sentinel2.port");
	/** REDIS SENTINEL端口3  */
	public static final String REDIS_SENTINEL_PORT3 = SysConf.getBundleString(REDIS, "redis.sentinel3.port");
	/** REDIS SENTINEL端口4  */
	public static final String REDIS_SENTINEL_PORT4 = SysConf.getBundleString(REDIS, "redis.sentinel4.port");
	/** REDIS SENTINEL端口3  */
	public static final String REDIS_SENTINEL_PORT5 = SysConf.getBundleString(REDIS, "redis.sentinel5.port");
	/** REDIS SENTINEL端口4  */
	public static final String REDIS_SENTINEL_PORT6 = SysConf.getBundleString(REDIS, "redis.sentinel6.port");
	
	
	/** REDIS超时  */
	public static final Integer REDIS_TIMEOUT = SysConf.getBundleInteger(REDIS, "redis.timeout"); 	
	/** REDIS密码  */
	public static final String REDIS_PASSWORD = SysConf.getBundleString(REDIS, "redis.password"); 

	public static final Integer REDIS_MAXIDLE = SysConf.getBundleInteger(REDIS, "redis.maxIdle"); 
	public static final Integer REDIS_MAXACTIVE = SysConf.getBundleInteger(REDIS, "redis.maxActive"); 
	public static final Integer REDIS_MAXWAIT = SysConf.getBundleInteger(REDIS, "redis.maxWait"); 
	public static final boolean REDIS_TEST_ON_BORROW = SysConf.getBundleBoolean(REDIS ,"redis.testOnBorrow");
	public static final boolean REDIS_TEST_ON_RETURN = SysConf.getBundleBoolean(REDIS ,"redis.testOnReturn");

	//===================== 直播   ============================================================
	public static final String RTMP_PUSH_URL = SysConf.getBundleString(CONF, "rtmp_push_url"); 
	public static final String RTMP_PULL_URL = SysConf.getBundleString(CONF, "rtmp_pull_url");
	public static final String HLS_PULL_URL = SysConf.getBundleString(CONF, "hls_pull_url");
	public static final Integer LIVE_MAX_RETURN_IMG_URL_SIZE = SysConf.getBundleInteger(CONF, "live_max_return_img_url_size");
	
	//===================== 青牛   ============================================================
	public static final String QN_BUCKET_URL = SysConf.getBundleString(CONF, "qn_bucket_url"); 
	public static final String QN_BUCKET_NAME = SysConf.getBundleString(CONF, "qn_bucket_name");
	public static final String QN_ACCESS_KEY = SysConf.getBundleString(CONF, "qn_access_key");
	public static final String QN_SECRET_KEY = SysConf.getBundleString(CONF, "qn_secret_key");
	
	//==================== 
	public static final String PLL_BASE_URL = SysConf.getBundleString(CONF, "pll_base_url");
	public static final String PLL_ACCESS_KEY = SysConf.getBundleString(CONF, "pll_access_key");
	public static final String PLL_SECRET_KEY = SysConf.getBundleString(CONF, "pll_secret_key");
	
	//===================== 友盟推送   ============================================================
	public static final String UM_KEY = SysConf.getBundleString(UMENG, "umeng_key"); 
	public static final String UM_SECRET = SysConf.getBundleString(UMENG, "umeng_secret");
	
	//===================== 云通讯   ============================================================
	public static final String Y_SERVER_URL = SysConf.getBundleString(YUNTONGXUN, "server_url"); 
	public static final String Y_SERVER_PORT = SysConf.getBundleString(YUNTONGXUN, "server_port");
	public static final String Y_ACCOUNT_SID = SysConf.getBundleString(YUNTONGXUN, "account_sid");
	public static final String Y_ACCOUNT_TOKEN = SysConf.getBundleString(YUNTONGXUN, "account_token");
	public static final String Y_APP_ID = SysConf.getBundleString(YUNTONGXUN, "app_id");
	public static final String Y_SMS_TEMPLATE = SysConf.getBundleString(YUNTONGXUN, "sms_template");
	public static final String Y_ISMS360_TEMPLATE = SysConf.getBundleString(YUNTONGXUN, "isms360_template");
	
	public static final String Y_API_KEY = SysConf.getBundleString(YUNTONGXUN, "api_key"); 
	public static final String Y_GETUSERINFO_URL = SysConf.getBundleString(YUNTONGXUN, "getUserInfo_url"); 
	public static final String Y_SENDSMS_URL = SysConf.getBundleString(YUNTONGXUN, "sendSms_url"); 
	public static final String Y_TPLSENDSMS_URL = SysConf.getBundleString(YUNTONGXUN, "tplSendSms_url"); 
	
	//===================== 人人   ============================================================
	public static final String RR_API_KEY = SysConf.getBundleString(CONF, "renren_api_key"); 
	public static final String RR_SECRET_KEY = SysConf.getBundleString(CONF, "renren_secret_key"); 
	
	//===================== facebook  ============================================================
	public static final String FB_API_KEY = SysConf.getBundleString(CONF, "fb_api_key"); 
	public static final String FB_SECRET_KEY = SysConf.getBundleString(CONF, "fb_secret_key"); 
	
	//===================== twitter  ============================================================
	public static final String TWI_API_KEY = SysConf.getBundleString(CONF, "twi_api_key"); 
	public static final String TWI_SECRET_KEY = SysConf.getBundleString(CONF, "twi_secret_key"); 
	
	//===================== wxpay  ============================================================
	public static final String WX_PAY_KEY = SysConf.getBundleString(WXPAY, "key");
	
	private SysConf(){}//私有构造函数（不允许实例化）
	
	/**
	 * 取得日志文件应保留文件数
	 * @return
	 */
	private static Integer getLogFileCnt(){
		String logFileCntStr=SysConf.getBundleString(CONF, "logFileCnt"); 
		Integer logFileCnt=null;
		try {
			logFileCnt = Integer.valueOf(logFileCntStr);
		} catch (NumberFormatException e) {
			logger.error("取得日志文件应保留文件数出错:",e);
		}
		return logFileCnt;
	}
	
	//======================= private method ==========================================

	/**
	 * 获取环境配置参数
	 * @return
	 */
	private static boolean getEnv(){
		try {
			String env = getBundleString(CONF,"env");//ResourceBundle.getBundle(BUNDLE_NAME, Locale.CHINA).getString("env");
			logger.info("SysConf.env={}",env);
			if(StringUtils.hasText(env)){
				if(!env.matches("[1-3]")){
					logger.error("环境参数“env”配置错误，使用默认配置（1）");
					return false;
				}else if(env.equals("1")){
					return true;
				}else{
					return false;
				}
			}else{
				logger.error("没有配置环境参数“env”配置参数出错，使用默认配置（1）");
				return false;
			}
		} catch (Exception e) {
			logger.error("获取环境配置参数“env”出错，使用默认配置（1）", e);
			return false;
		}
	}
	
	private static Boolean getBundleBoolean(String propertiesName, String key) {
		String str = getBundleString(propertiesName, key);
		Boolean  boVal = null;
		if (StringUtils.hasText(str)) {
			try {
				boVal = Boolean.valueOf(key);
			} catch (NumberFormatException e) {
				logger.error("相关参数配置错误：“{}”，将使用默认配置", str);
			}
		}
		return boVal;
	}
	
	private static Double getBundleDouble(String propertiesName, String key) {
		String str = getBundleString(propertiesName, key);
		Double doubleVal = null;
		if (StringUtils.hasText(str)) {
			try {
				doubleVal = Double.valueOf(str);
			} catch (NumberFormatException e) {
				logger.error("相关参数配置错误：“{}”，将使用默认配置", str);
			}
		}
		return doubleVal;
	}
	
	private static Integer getBundleInteger(String propertiesName, String key) {
		String str = getBundleString(propertiesName, key);
		Integer intVal = null;
		if (StringUtils.hasText(str)) {
			try {
				intVal = Integer.valueOf(str);
			} catch (NumberFormatException e) {
				logger.error("相关参数配置错误：“{}”，将使用默认配置", str);
			}
		}
		return intVal;
	}
	
	private static Long getBundleLong(String propertiesName, String key) {
		String str = getBundleString(propertiesName, key);
		Long longVal = null;
		if (StringUtils.hasText(str)) {
			try {
				longVal = Long.valueOf(str);
			} catch (NumberFormatException e) {
				logger.error("相关参数配置错误：“{}”，将使用默认配置", str);
			}
		}
		return longVal;
	}
	
	private static String getBundleString(String propertiesName, String key){
		return ResourceBundle.getBundle(propertiesName, Locale.CHINA).getString(key);
	}

}
 