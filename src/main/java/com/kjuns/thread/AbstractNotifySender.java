package com.kjuns.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kjuns.util.PropertiesUtil;
import com.kjuns.util.PushConstants;

/**
 * 
 * 推送公共类
 *
 */
public abstract class AbstractNotifySender extends Thread{

	private static Logger logger = LoggerFactory.getLogger(AbstractNotifySender.class);
	
	/***短信内容描述**/
	public static Map<String,String> smsMsgDescMap = new HashMap<String,String>();
	
	static{
		//smsMsgDescMap.put(key, value)
	}
	
	/**推送消息的flag**/
	public static Map<String,String> pushMsgFlagMap = new HashMap<String,String>();
	
	static{
		pushMsgFlagMap.put(PushConstants.PUSH_TEST, "22");
	}
	
	/** **/
//	public boolean pushContent() {
//		String mobile = getMobile();
//		String content = getContent();
//		if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(content)){
//			return false;
//		}
//		return JPushUtils.sendCustomMsgByAlias(alias, content, orderNo, flag)
//	}
	
	/**
	 * 从属性文件中获取短信模板，并将参数替换
	 * @param contextKey
	 * @param contextParamMap
	 * @return
	 */
	protected String getSmsContentTemplate(String contentKey,Map<String,Object> contentParamMap){
		try {
			String templateContent = PropertiesUtil.getSmsContentTemplate(contentKey);
			if(StringUtils.isEmpty(templateContent)){
				return null;
			}
			return replaceTemplateContent(templateContent,contentParamMap);
		} catch (Exception e) {
			logger.error("发送短信获取短信内容报错!",e);
		}
		return null;
	}
	
	/**
	 * 获取推送消息
	 * @param contentKey
	 * @param contentParamMap
	 * @return
	 */
	protected String getPushContentTemplate(String contentKey,Map<String,Object> contentParamMap){
		try {
			String templateContent = PropertiesUtil.getPushContentTemplate(contentKey);
			if(StringUtils.isEmpty(templateContent)){
				return  null;
			}
			return replaceTemplateContent(templateContent,contentParamMap);
		} catch (Exception e) {
			logger.error("推送消息获取消息内容报错!",e);
		}
		return null;
	}
	
	/**
	 * 替换短信模板中的参数值
	 * @param tempalteContext 模板内容
	 * @param contextParamMap 模板参数值
	 * @return
	 */
	private String replaceTemplateContent(String tempalteContent,Map<String,Object> contentParamMap){
		Scanner sc = new Scanner(tempalteContent);
		StringBuffer buf = new StringBuffer();
		try{
			Pattern p = Pattern.compile("[$]([^$]*?)[$]");
			while (sc.hasNext()) {
			    Matcher m = p.matcher(sc.nextLine());
			    while (m.find()) { //查找并替换参数
			        m.appendReplacement(buf, contentParamMap.get(m.group(1)) != null ? contentParamMap.get(m.group(1)).toString() : "");
			    }
			    m.appendTail(buf);
			}
			if(StringUtils.isEmpty(buf.toString())){
				buf.append(tempalteContent);
			}
		}catch(Exception e){
			logger.error("替换短信模板内容报错！",e);
		}finally{
			sc.close();
		}
		return buf.toString();
	}
	
	/**
	 * 获取短信类型
	 * @return
	 */
	protected abstract String getContent();
	
	/**
	 * 获取手机号码
	 * @return
	 */
	protected abstract String getMobile();
	
	/**
	 * 
	 * @return
	 */
	protected abstract int getSender();
	
	/**
	 * 获取短信内容描述
	 * @return
	 */
	protected abstract String getMsgDesc();
	
}
