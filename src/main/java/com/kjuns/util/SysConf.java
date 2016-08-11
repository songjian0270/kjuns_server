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
	
	private static final String JDBC = "jdbc";
	
	private static final String UMENG = "umeng";
	
	private static final String WXPAY = "wxpay";
	
	/** 1:正式环境，2：测试环境   */
	public static final boolean ENV = getEnv();
	
	//===================== 日志文件相关   ============================================================
	/** 日志文件目录  */
	public static final String LOG_PATH = SysConf.getBundleString(CONF, "logPath");
	/** 日志文件目录文件数量  */
	public static final Integer LOG_FILE_CNT = getLogFileCnt(); 
	
	//===================== 间隔条数           ============================================================
	public static final Integer INTERVAL_NUMBER = SysConf.getBundleInteger(CONF, "interval_number");

	//===================== 备用域名设置   ============================================================
	/** 备用主域名  */
	public static final String BACK_MASTER_SERVER_URL = SysConf.getBundleString(CONF, "back_master_server_url");
	/** 备用搜索域名 */
	public static final String BACK_SEARCH_SERVER_URL = SysConf.getBundleString(CONF, "back_search_server_url");

	//===================== jdbc设置   ============================================================
	/** jdbc.url  */
	public static final String JDBC_URL = SysConf.getBundleString(JDBC, "user.db.url");
	/** jdbc.username  */
	public static final String JDBC_USERNAME = SysConf.getBundleString(JDBC, "user.db.user");
	/** jdbc.password  */
	public static final String JDBC_PWD = SysConf.getBundleString(JDBC, "user.db.password");
	
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
	
	private static String getBundleString(String propertiesName, String key){
		return ResourceBundle.getBundle(propertiesName, Locale.CHINA).getString(key);
	}

}
 