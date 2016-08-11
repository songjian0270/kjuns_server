package com.kjuns.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.StringUtils;

/**
 * 属性文件工具类
 * @author Jack
 *
 */
/**
 * <b>Function: </b> 属性文件工具类
 * @author James
 * @date 2015-9-9
 * @file PropertiesUtil.java
 * @package com.kjuns.util
 * @project kjuns
 * @version 2.0
 */
public class PropertiesUtil {

	private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	
	/**短信内容**/
	private static Map<String,String> smsContentMap = new HashMap<String,String>();
	
	/**推送消息**/
	private static Map<String,String> pushContentMap = new HashMap<String,String>();
	
	/**
	 * 获取短信模板内容
	 * @param contentKey
	 * @return
	 */
	public static String getSmsContentTemplate(String contentKey){
		String smsContent = smsContentMap.get(contentKey);
		if(!StringUtils.isEmpty(smsContent)){
			return smsContent;
		}
		Properties p = getProperties("conf/smsContent.properties");
		if(p != null){
			smsContent = p.getProperty(contentKey);
			smsContentMap.put(contentKey, smsContent);
		}
		return smsContent;
	}
	
	/**
	 * 获取推送消息
	 * @param contentKey
	 * @return
	 */
	public static String getPushContentTemplate(String contentKey){
		String pushContent = pushContentMap.get(contentKey);
		if(!StringUtils.isEmpty(pushContent)){
			return pushContent;
		}
		Properties p = getProperties("conf/pushContent.properties");
		if(p != null){
			pushContent = p.getProperty(contentKey);
			pushContentMap.put(contentKey, pushContent);
		}
		return pushContent;
	}
	
	/**
	 * 根据路径获取属性文件
	 * @param path
	 * @return
	 */
	public static Properties getProperties(String path){
		try{
			Resource resource = new ClassPathResource(path);
			Properties p = PropertiesLoaderUtils.loadProperties(resource);
			return p;
		}catch(Exception exp){
			logger.error("获取属性文件报错！",exp);
		}
		return null;
	}
}
