package com.kjuns.validation;

import java.util.Map;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.kjuns.util.CommonConstants;

public class Validator {
	private static Logger logger = LoggerFactory.getLogger(Validator.class);
	private static final String ENCODING_UTF8 = "UTF-8";
	
	/** 不允许为null  */
	private static final String NOT_NULL = "notNull";
	/** 不允许为空（null或""）  */
	private static final String NOT_EMPTY = "notEmpty";
	/** 字段值可以为空  （null或""）*/
	private static final String EMPTY_ABLE = "emptyAble";
	/** 指定长度  */
	private static final String FIXED_LEN = "fixedLen";
	/** 最小长度  */
	private static final String MIN_LEN = "minLen";
	/** 最大长度  */
	private static final String MAX_LEN = "maxLen";
	/** 最小值  */
	private static final String MIN = "min";
	/** 最大值  */
	private static final String MAX = "max";
	/** 必须为 数字  */
	private static final String NUMERAL = "numeral";
	/** 必须为指定的值(可多个，用“,”分隔) */
	private static final String SPECIFIED = "specified";//指定的值
	/** Email格式 */
	private static final String EMAIL = "email";
	/** 匹配的正则表达式（注意xml中不需要转义符“\”） */
	private static final String REGULAR = "regular";
	/** 字段可以不上送  */
	public static final String OPTIONAL = "optional";
	/** 禁止emoji表情符  */
	public static final String EMOJI_FORBID = "emojiForbid";
	
	/**
	 * 根据map里定义的校验规则校验数据
	 * @param dataMap 待校验的数据
	 * @param valiRule 校验规则
	 * @return
	 * @throws Exception 
	 */
	public static String validateDataMap(Map<String,Object> dataMap,  FieldValiRule[] fvr) throws Exception {
		String msg = null;
		long start1 = System.currentTimeMillis();
		//String error = null;
		for (FieldValiRule fieldValiRule : fvr) {
			String fieldName = fieldValiRule.getName();//字段名
			Object reqObj = dataMap.get(fieldName);//请求的数据
			logger.info("校验“{}”,value=[{}]",fieldName, reqObj);
			
			//校验规则
			Map<String, Vali> rules = fieldValiRule.getRules();
			//校验字段是否上送
			if(!dataMap.containsKey(fieldName)){
				if(rules.containsKey(OPTIONAL)){
					continue;//字段没上送，但字段为 “optional”，则忽略此字段，直接校验下一个字段
				}else{
					logger.info("字段缺失，没有上送字段：{}",fieldName);
					return "字段缺失，没有上送字段："+fieldName;
				}
			}
			if(reqObj == null){
				if(rules.containsKey(EMPTY_ABLE)){
					continue;//字段上送值为空，且校验规则为“可以为空”，则不校验此字段，直接校验下一个字段
				}
				Vali vali = null;
				if((vali = rules.get(NOT_NULL)) != null || (vali = rules.get(NOT_EMPTY)) != null){
					return vali.getMsg();
				}
			}
			String reqStr = (reqObj == null) ? null : String.valueOf(reqObj); 
			//校验字段是否为必填
			if(!StringUtils.hasText(reqStr)){//是否可以为空（null或""）
				if(rules.containsKey(EMPTY_ABLE)){
					continue;//字段上送值为空，且校验规则为“可以为空”，则不校验此字段，直接校验下一个字段
				}
				Vali vali = null;
				if((vali = rules.get(NOT_EMPTY)) != null){
					return vali.getMsg();
				}
			}
			
			//遍历字段的校验规则 进行校验
			for (Map.Entry<String, Vali> rule : rules.entrySet()) {
				String validate = rule.getKey();
				Vali vali = rule.getValue();//校验的“比较值”和 “校验信息”
				if((msg = validateRule(fieldName, reqStr, validate, vali)) != null){
					return msg;
				}
			}
			
			String dateType = fieldValiRule.getDataType();//定义的数据类型
			convertAndRewriteData(fieldName, dateType, reqStr, reqObj, dataMap);
		}
		logger.info("校验报文输入用时：{}ms",System.currentTimeMillis()-start1);
		return msg;
	}
	
	/**
	 * 
	 * @param fieldName 字段名
	 * @param reqStr 字段上送的数据
	 * @param validate 校验规则
	 * @param vali 校验“比较值” 和 “校验信息”
	 * @param rules 字段的所有校验规则
	 * @return
	 * @throws Exception
	 */
	private static String validateRule(String fieldName, String reqStr, String validate, Vali vali) throws Exception{
		switch (validate) {
		case NOT_EMPTY:
			if(!StringUtils.hasText(reqStr)){
				return vali.getMsg();
			}
			break;
		case NOT_NULL://可以为空  （null或""）
			if(reqStr == null || reqStr.length()==0){
				return vali.getMsg();
			}
			break;
		case FIXED_LEN:
			if(!StringUtils.hasLength(reqStr) || reqStr.getBytes(ENCODING_UTF8).length != Integer.parseInt(vali.getValue())){
				return vali.getMsg();
			}
			break;
		case MIN_LEN:
			if(StringUtils.hasLength(reqStr) && reqStr.getBytes(ENCODING_UTF8).length <= Integer.parseInt(vali.getValue())){
				return vali.getMsg();
			}
			break;
		case MAX_LEN:
			if(StringUtils.hasLength(reqStr) && reqStr.getBytes(ENCODING_UTF8).length >= Integer.parseInt(vali.getValue())){
				return vali.getMsg();
			}
			/*if(!StringUtils.hasLength(reqStr) || reqStr.getBytes(ENCODING_UTF8).length > Integer.parseInt(vali.getValue())){
				return vali.getMsg();
			}*/
			break;
		case MIN:
			if(StringUtils.isEmpty(reqStr) && Double.parseDouble(reqStr) < Integer.parseInt(vali.getValue())){
				return vali.getMsg();
			}
			break;
		case MAX:
			if(!StringUtils.isEmpty(reqStr) && Double.parseDouble(reqStr) > Integer.parseInt(vali.getValue())){
				return vali.getMsg();
			}
			/*if(!StringUtils.hasLength(reqStr) || reqStr.getBytes(ENCODING_UTF8).length > Integer.parseInt(vali.getValue())){
				return vali.getMsg();
			}*/
			break;
		case NUMERAL:
			if(StringUtils.hasLength(reqStr) && !reqStr.matches(CommonConstants.REGULAR_NUMERAL)){
				return vali.getMsg();
			}
			/*if(!StringUtils.hasLength(reqStr) || !reqStr.matches(CommonConstants.REGULAR_NUMERAL)){
				return vali.getMsg();
			}*/
			break;
		case EMAIL://Email格式校验
			if(StringUtils.hasLength(reqStr) && !reqStr.matches(CommonConstants.REGULAR_EMAIL)){
				return vali.getMsg();
			}
			/*if(!StringUtils.hasLength(reqStr) || !reqStr.matches(CommonConstants.REGULAR_EMAIL)){
				return vali.getMsg();
			}*/
			break;
		case SPECIFIED:
			if(!StringUtils.hasLength(reqStr)){
				return vali.getMsg();//return fieldName + "不符合输入规则";
			}
			String vals = vali.getValue();
			if(vals.indexOf(",") == -1){
				if(!vals.equals(reqStr)){
					return vali.getMsg();//return fieldName + "不符合输入规则";
				}
			}else{
				StringTokenizer st = new StringTokenizer(vals,",");
				boolean machesFlag = false;
				while (st.hasMoreElements()) {
					String str = (String)st.nextElement();
					if(str.equals(reqStr)){
						machesFlag = true;
						break;
					}
				}
				if(machesFlag == false){
					return vali.getMsg();//return fieldName + "不符合输入规则";
				}
			}
			break;
		case REGULAR: 
			String valiVal = vali.getValue();//得到正则表达式（主意 “\” 转义符）
			//System.out.println("值："+reqStr+"表达式："+valiVal);
			//System.out.println(reqStr.matches(valiVal));
			if(reqStr != null && !reqStr.matches(valiVal)){
				return vali.getMsg();
			}
			break;
		case EMPTY_ABLE:
			//do nothing
			break;
		case EMOJI_FORBID:
			if(containsEmoji(reqStr)){
				return vali.getMsg();
			}
			break;
		default:
			logger.warn("Unknow Rule![{}]",validate);
			break;
		}
		return null;
	}
	private static void convertAndRewriteData(String fieldName, String dateType, String reqStr, Object reqObj, Map<String,Object> data){
		switch (dateType) {
		case "Integer":
			reqStr = (!StringUtils.hasLength(reqStr)) ? "0" : reqStr;
			reqObj = Integer.valueOf(reqStr);
			break;
		case "Long":
			reqStr = (!StringUtils.hasLength(reqStr)) ? "0" : reqStr;
			reqObj = Long.valueOf(reqStr);
			break;
		case "Double":
			reqStr = (!StringUtils.hasLength(reqStr)) ? "0" : reqStr;
			reqObj = Double.valueOf(reqStr);
			break;
		default:
			break;
		}
		data.put(fieldName, reqObj);
	}
	
	/**
	 * 判断是否还有emoji表情符号
	 * @param source
	 * @return
	 */
	private static boolean containsEmoji(String str) {
		int len = str.length();
		for (int i = 0; i < len; i++) {
			char codePoint = str.charAt(i);
			if (isEmojiCharacter(codePoint)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isEmojiCharacter(char codePoint) {  
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) ||  
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||  ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));  
    } 
	/*public static void main(String[] args){
		
	}*/
}
