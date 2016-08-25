package com.kjuns.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;

public class CommonUtils {

	private static final char[] charAndNumArr = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
			'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z' };
	private static final char[] UpCharAndNumArr = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
			'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9' };
	// private static final char[] charArr =
	// {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	private static final char[] numArr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	private static final char[] numArrNoZero = { '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	private static final Random random = new Random();
	
	public static final Long ONE_DAY_SECOND = 86400l;

	/**
     * 检测字符串是否不为空(null,"","null")
     * 
     * @param s
     * @return 不为空则返回true，否则返回false
     */
    public static boolean notEmpty(Object s) {
        return null != s && !"".equals(s) && !"null".equals(s);
    }
    
	/**
     * 检测List是否不为空(null,> 0)
     * 
     * @param s
     * @return 不为空则返回true，否则返回false
     */
    @SuppressWarnings("rawtypes")
	public static boolean notListFEmpty(List list) {
        return null != list && list.size() > 0;
    }
    
    /**
     * 检测Set是否不为空(null,> 0)
     * 
     * @param s
     * @return 不为空则返回true，否则返回false
     */
    @SuppressWarnings("rawtypes")
	public static boolean notSetFEmpty(Set set) {
        return null != set && set.size() > 0;
    }
    
    /**
     * 检测Map是否不为空(null,> 0)
     * 
     * @param s
     * @return 不为空则返回true，否则返回false
     */
    @SuppressWarnings("rawtypes")
	public static boolean notMapFEmpty(Map map) {
        return null != map && map.size() > 0;
    }
    
    /**
     * 检测Long是否不为空(null,> 0)
     * 
     * @param s
     * @return 不为空则返回true，否则返回false
     */
	public static boolean notLongFEmpty(Long i) {
        return null != i && i > 0;
    }
	
	/**
    * 返回元素集合(已去重)
    * @param list 备选号码
    * @param selected 备选数量
    * @return
    */
   public static List<String> getRandomNum(List<String> list, int selected) {
       List<String> reList = new ArrayList<String>();

       Random random = new Random();
       // 先抽取，备选数量的个数
       if (list.size() >= selected) {
           for (int i = 0; i < selected; i++) {
               // 随机数的范围为0-list.size()-1;
               int target = random.nextInt(list.size());
               reList.add(list.get(target));
               list.remove(target);
           }
       } else {
           selected = list.size();
           for (int i = 0; i < selected; i++) {
               // 随机数的范围为0-list.size()-1;
               int target = random.nextInt(list.size());
               reList.add(list.get(target));
               list.remove(target);
           }
       }
      
       return reList;
   }
   
   /** 
    * 随机指定范围内N个不重复的数 
    * 最简单最基本的方法 
    * @param min 指定范围最小值 
    * @param max 指定范围最大值 
    * @param n 随机数个数 
    */  
   public static int[] randomCommon(int min, int max, int n){  
       if (n > (max - min + 1) || max < min) {  
              return null;  
          }  
       int[] result = new int[n];  
       int count = 0;  
       while(count < n) {  
           int num = (int) (Math.random() * (max - min)) + min;  
           boolean flag = true;  
           for (int j = 0; j < n; j++) {  
               if(num == result[j]){  
                   flag = false;  
                   break;  
               }  
           }  
           if(flag){  
               result[count] = num;  
               count++;  
           }  
       }  
       return result;  
   }  
   
   /**
    * 返回简化版的数字
    * @param count
    * @return
    */
   public static String shortNum(Long count){
	   String result = "0";
	    if (count!=null && count!=0) { // 数字不为0
	        if (count < 10000) { // 不足10000：直接显示数字，比如786、7986
	        	result = String.format("%d", count);
	        } else { // 达到10000：显示xx.x万，不要有.0的情况
	            double wan = count / 10000.0;
	            result = String.format("%.1f万", wan);
	            // 将字符串里面的.0去掉
	            result = result.replace(".0", "");
	        }
	    }
	    return result;
   }

    /**
     * 检测字符串是否为空(null,"","null")
     * 
     * @param s
     * @return 为空则返回true，不否则返回false
     */
    public static boolean isEmpty(Object s) {
        return null == s || "".equals(s) || "null".equals(s);
    }

    public static String getImage(String image){
    	return getImage(image,false);
    }
    
    public static String getImage(String image,boolean addWatermark){
    	if(addWatermark){
    		image = image+"!wm";
    	}
    	if (CommonUtils.notEmpty(image)) {
			if (!image.startsWith("http")) {
				return SysConf.QN_BUCKET_URL + image ;
			}
		}
    	return image;
    }
    
    public static String getImage(String image,String style){
		image = image+style;
    	if (CommonUtils.notEmpty(image)) {
			if (!image.startsWith("http")) {
				return SysConf.QN_BUCKET_URL + image ;
			}
		}
    	return image;
    }
    
    /**
     * 必返字段为空时，为 long
     * @param obj
     * @return
     */
    public static Long getLong(String str){
    	return str == null? 0l : Long.valueOf(str);
    }
    
    /**
     * 必返字段为空时，为 long
     * @param obj
     * @return
     */
    public static Long getLong(Long str){
    	return str == null? 0l : Long.valueOf(str);
    }
    
    /**
     * 必返字段为空时，为 Integer
     * @param obj
     * @return
     */
    public static Integer getInteger(String str){
    	return str == null? 0 : Integer.parseInt(str);
    }
    
    /**
     * 必返字段为空时，为""
     * @param obj
     * @return
     */
    public static String getStr(String str){
    	return str == null? "" : str;
    }
    
    /**
     * 必返字段为空时，为""
     * @param obj
     * @return
     */
    public static String getStrHex(String str){
    	return str == null? "" : toStringHex16(str);
    }
    
    /**
     * 对于Value类型判断并拼接有效字符串
     * @param obj
     * @return
     */
    public static String getSqlStr(Object obj){
    	String result = "";
    	if(obj != null){
    		if(obj instanceof Date){
    			result = "'" + dateToStr((Date)obj) + "'";
    		}else if(obj instanceof String){
    			result = "'" + obj + "'";
    		}else{
    			result = obj.toString() ;
    		}
    	}
    	return result;
    }
	
	/**
	 * 日期/时间格式  "yyyy-MM-dd HH:mm:ss" 
	 * @param date
	 * @return
	 */
	public static String dateToStr(Date date){
		return CommonConstants.DATETIME_SEC.format(date);
	}
	
	/**
	 * 日期/时间格式  "yyyy-MM-dd HH:mm:ss" 
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static Date strToDate(String date) throws ParseException{
		return CommonConstants.DATETIME_SEC.parse(date);
	}
	
	/**
	 * 将Unix时间戳转换成日期
	 * @param timestamp
	 * @return
	 */
	public static String unixTimestampToDate(long timestamp) {
		CommonConstants.DATETIME_SEC.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return CommonConstants.DATETIME_SEC.format(new Date(timestamp));
	}

	/**
	 * 将指定的日期转换成Unix时间戳
	 * @param date 需要转换的日期 yyyy-MM-dd HH:mm:ss,sss
	 * @return
	 */
	public static long dateToUnixTimestamp(String date) {
		long timestamp = 0 ;
		try {
			timestamp = CommonConstants.DATETIME_FULL.parse(date).getTime();
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return timestamp;
	}
	
	/**
	 * 获取当前时间到零点的时间差
	 * @return
	 */
	public static Integer currentDateToAfterTomorrowZeroTimeSecond(){
		Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.DATE, 2);
        return (int) ((c.getTime().getTime()- date.getTime())/1000);
	}
	
	public static Integer todayZeroTimeSecond(){

		Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return (int)( (c.getTime().getTime())/1000l);
	}

	//** 当前时间到0点时间差
	public static Integer currentDateToTomorrowZeroTimeSecond(){

		Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.DATE, 1);
        return (int) ((c.getTime().getTime()- date.getTime())/1000);
	}
	
	public static Integer currentDateToTomorrowOneTimeSecond(){

		Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 1);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.DATE, 1);
        return (int) ((c.getTime().getTime()- date.getTime())/1000);
	}
	
	/**
	 * 将指定的日期转换成Unix时间戳
	 * @param date 无制定
	 * @return
	 */
	public static long dateToUnixTimestamp(String date, SimpleDateFormat sd) {
		long timestamp = 0;
		try {
			timestamp = sd.parse(date).getTime();
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return timestamp;
	}
	
	/**
	 * 获取当前时间为本年第几周：周1为第一天
	 * @return
	 */
	public static String dateToWeekOfYear(){
		Date date = new Date();
	    Calendar calendar = Calendar.getInstance();
	    calendar.setFirstDayOfWeek(Calendar.SUNDAY);
	    calendar.setTime(date);
	    int week = calendar.get(Calendar.WEEK_OF_YEAR);
	    String year = CommonConstants.DATE_YEAR.format(date);
	    return year + week;
	}
	
	/**
	 * 获取当前时间为本年第几周上n周：周1为第一天
	 * @return
	 */
	public static String dateToWeekOfYear(int n){
		Date date = new Date();
	    Calendar calendar = Calendar.getInstance();
	    calendar.setFirstDayOfWeek(Calendar.SUNDAY);
	    calendar.setTime(date);
	    int week = calendar.get(Calendar.WEEK_OF_YEAR);
	    if(n < week){
	    	week = week - n;
	    }
	    String year = CommonConstants.DATE_YEAR.format(date);
	    return year + week;
	}
	
	/**
	 * 12个长度的订单号
	 * 
	 * @param currTime
	 * @return
	 */
	public static String getOrderNo(Date currTime) {// yyMMdd+6位随机数 再反转字符串
		StringBuilder sb = new StringBuilder(CommonConstants.DATE_SHORT_STR.format(currTime));
		for (int i = 0; i < 5; i++) {
			sb.append(numArr[random.nextInt(10)]);
		}
		sb.append(numArrNoZero[random.nextInt(9)]);
		sb.reverse();
		return sb.toString();
	}

	/**
	 * 9位的随机数字（用户/车辆注册号）
	 * 
	 * @return
	 */
	public static String getRandomRegNo() {

		return getRandomNum(9);
	}

	/**
	 * 4位短信验证码
	 * 
	 * @return
	 */
	public static String getSmsCode() {
		return String.valueOf(Math.round(Math.random() * 8999 + 1000));
	}

	public static String getRandomNum(int length) {
		return getRandom(numArr, length);
	}

	public static String getRandomNumUpChar(int length) {
		return getRandom(UpCharAndNumArr, length);
	}

	/**
	 * 返回包含字母（大、小写）、数字的 随机字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomCharAndNum(int length) {
		return getRandom(charAndNumArr, length);
	}

	private static String getRandom(char[] charArray, int length) {
		StringBuilder sb = new StringBuilder();
		int range = charArray.length;
		for (int i = 0; i < length; i++) {
			sb.append(charArray[random.nextInt(range)]);
		}
		return sb.toString();
	}

	/**
	 * 判断字符串格式的日期(yyMMdd)是否正确
	 * 
	 * @param strDate
	 * @return
	 */
	public static boolean isDateStr(String strDate) {
		CommonConstants.DATE_SHORT_STR.setLenient(false);
		try {
			CommonConstants.DATE_SHORT_STR.parse(strDate);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	/**
	 * 生成长度为“fullLenght”的序列号，不足的位补“0”
	 * 
	 * @param fullLenght
	 *            总长度
	 * @param seq
	 *            序列
	 * @return
	 */
	public static String fixedLengthSeq(int fullLenght, int seq) {
		fullLenght = fullLenght + 1;
		String seqStr = String.valueOf(seq);
		for (int i = 0; i < (fullLenght - seqStr.length()); i++) {
			seqStr = "0" + seqStr;
		}
		return seqStr;
	}

	/**
	 * 读取远程文件
	 * 
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	public static String imgRemoteToBase64(String fileName) throws FileNotFoundException {
		String base64Str = null;
		InputStream in = null;
		try {
			URL url = new URL(fileName);
			in = url.openStream();
			byte[] bytes = new byte[in.available()];
			in.read(bytes);
			// base64Str = new BASE64Encoder().encode(bytes);
			base64Str = Base64.encodeBase64String(bytes);
		} catch (FileNotFoundException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return base64Str;
	}

	public static String imgToBase64(String fileName) throws FileNotFoundException {
		String base64Str = null;
		InputStream in = null;
		try {
			in = new FileInputStream(fileName);
			byte[] bytes = new byte[in.available()];
			in.read(bytes);
			// base64Str = new BASE64Encoder().encode(bytes);
			base64Str = Base64.encodeBase64String(bytes);
		} catch (FileNotFoundException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return base64Str;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map convertBean(Object bean)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		Class type = bean.getClass();
		Map returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}

	@SuppressWarnings("rawtypes")
	public static Object map2Bean(Class type, Map map)
			throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
		Object obj = type.newInstance(); // 创建 JavaBean 对象
		BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类信息
		PropertyDescriptor[] proDesc = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < proDesc.length; i++) {
			PropertyDescriptor descriptor = proDesc[i];
			String propertyName = descriptor.getName();
			// descriptor.
			if (map.containsKey(propertyName)) {
				Object value = map.get(propertyName);
				descriptor.getWriteMethod().invoke(obj, value);
			} else {

			}
		}
		return obj;
	}

	@SuppressWarnings("rawtypes")
	public static Object mapToBean(Class type, Map map)
			throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
		Object obj = type.newInstance(); // 创建 JavaBean 对象
		// 给 JavaBean 对象的属性赋值
		Field fieldlist[] = type.getDeclaredFields();
		for (Field field : fieldlist) {
			String propertyName = field.getName();
			// Annotation[] anns = field.getAnnotations();
			System.out.print("propertyName:" + propertyName);
			Class classType = field.getType();
			/*
			 * int ttt = 3; String t = (classType)ttt;
			 */
			if (classType.equals(String.class)) {
				System.out.println("是字符串");
			}
			if (classType.equals(Integer.class)) {
				System.out.println("是数字");
			}

			// 得到这个属性的set方法
			if (map.containsKey(propertyName)) {
				String value = String.valueOf(map.get(propertyName));
				PropertyDescriptor pd = new PropertyDescriptor(propertyName, type);
				Method setMethod = pd.getWriteMethod();
				setMethod.invoke(obj, value);
			}
		}
		return obj;
	}

	public static final SimpleDateFormat sdf_MM = new SimpleDateFormat();

	/**
	 * 
	 * @param picAbsPath
	 *            图片绝对路径
	 * @param picName
	 *            图片名
	 * @param picContent
	 *            图片Base64编码
	 * @param logger
	 * @return
	 */
	public static ErrorCode saveFile(String picAbsPath, String picName, String picContent, Logger logger) {
		ErrorCode errorCode = ErrorCode.FAILED;
		boolean dirFlag = true;
		File file = new File(picAbsPath);
		if ((dirFlag = file.exists()) == false) {// 判断路径是否存在
			dirFlag = file.mkdirs();
		}
		if (dirFlag == false) {
			logger.error("创建图片目录失败");
		} else {
			// String filePath = picPath+ File.separator + jb.getId() + extName;
			String filePath = picAbsPath + picName;
			if (base64ToImg(picContent, filePath)) {
				// 存储的图片名为 ID + 图片原来扩展名
				logger.info("图片上传成功{}", filePath);
				errorCode = ErrorCode.SUCCESS;
			} else {
				logger.error("图片上传失败！{}", filePath);
			}
		}
		return errorCode;
	}

	public static boolean base64ToImg(String base64Str, String fileNameWithPath) {
		ByteArrayInputStream in = null;
		FileOutputStream out = null;
		try {
			byte[] bytes = Base64.decodeBase64(base64Str);
			in = new ByteArrayInputStream(bytes);
			out = new FileOutputStream(fileNameWithPath);
			int byteRead = 0;
			byte[] buffer = new byte[1024];
			while ((byteRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, byteRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		File file = new File(fileNameWithPath);
		return file.exists();
	}

	/**
	 * 过滤掉 emoji表情符
	 * 
	 * @param source
	 * @return
	 */
	public static String filterEmoji(String str) {
		StringBuilder sb = new StringBuilder(str);
		for (int len = str.length(), i = len - 1; i >= 0; --i) {
			int codePoint = str.codePointAt(i);
			if (codePoint >= 127744) { // Emoji表情所在码位为U+1F300 – U+1F64F
				sb.deleteCharAt(i);
			}
		}
		return sb.toString();
	}

	/**
	 * 判断是否还有emoji表情符号(未验证)
	 * 
	 * @param source
	 * @return
	 */
	public static boolean containsEmoji(String str) {
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
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}
	
	/**
	 * 字符串转十六进制编码
	 * @param hex
	 * @return
	 */
	public static final String toHex16String(String str) throws UnsupportedEncodingException {  
		byte[] bArray = str.getBytes("utf-8");
	    StringBuffer sb = new StringBuffer(bArray.length);   
	    String sTemp;   
	    for (int i = 0; i < bArray.length; i++) {   
	    	sTemp = Integer.toHexString(0xFF & bArray[i]);   
	    	if (sTemp.length() < 2)   
	    		sb.append(0);   
	    		sb.append(sTemp.toUpperCase());   
	    }   
	    return sb.toString();   
	}  

	/**
	 * 转化十六进制编码为字符串  
	 * @param s
	 * @return
	 */
	public static String toStringHex16(String s) {  
		byte[] baKeyword = new byte[s.length() / 2];  
		for (int i = 0; i < baKeyword.length; i++) {  
			try {  
				baKeyword[i] = (byte) (0xff & Integer.parseInt(
						s.substring( i * 2, i * 2 + 2), 16));  
		    } catch (Exception ex) {  
		     ex.printStackTrace();  
		    }  
		}  
		try {  
		    s = new String(baKeyword, "utf-8");// UTF-16le:Not  
		} catch (Exception ex) {  
		    ex.printStackTrace();  
		}  
		return s;  
	}  

	/**
	 * 转换成中文形式的日期
	 * 
	 * @param dateTime
	 *            yyyyMMddHHmmss
	 * @return yyyy年mm月dd日
	 */
	public static String date2ChDate(long dateTime) {
		String orderDateTmp = String.valueOf(dateTime);
		String fDate = "";
		if (orderDateTmp != null && orderDateTmp.length() >= 8) {
			fDate = orderDateTmp.substring(0, 4) + "年" + orderDateTmp.substring(4, 6) + "月"
					+ orderDateTmp.substring(6, 8) + "日";
		}
		return fDate;
	}

	/**
	 * 提供精确的乘法运算并且保留两位小数
	 */
	public static double multiply(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return format(b1.multiply(b2).doubleValue());
	}

	/**
	 * 提供精确的除法运算并且保留两位小数
	 */
	public static double divide(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, 2, BigDecimal.ROUND_DOWN).doubleValue();
	}

	/**
	 * 提供精确的减法运算并且保留两位小数
	 */
	public static double subtract(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return format(b1.subtract(b2).doubleValue());
	}

	/**
	 * 提供精确的加法运算并且保留两位小数
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return format(b1.add(b2).doubleValue());
	}

	/**
	 * 格式化保留2位小数
	 * 
	 * @param dou
	 * @return
	 */
	public static double format(double dou) {
		BigDecimal bd = new BigDecimal(String.valueOf(dou));
		BigDecimal setScale = bd.setScale(2, BigDecimal.ROUND_DOWN);
		return setScale.doubleValue();
	}
	
	public static String getCacheKey(String id, String key){
		return id + ":" + key;
	}
	
	/**
	 * MD532加密
	 * 
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException 
	 */
	public static String md5Encode32(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(str.getBytes("utf8"));
		byte b[] = md.digest();
		int i;
		StringBuilder buf = new StringBuilder("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString().toUpperCase();
	}

	/**
	 * 获得客户端真实IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public static String replaceDiallingCode(String phoneNum){
		if(phoneNum.indexOf("-")<0){
			if(phoneNum.indexOf("+86")==0){
				return "86-"+phoneNum.substring(3);
			}else if(phoneNum.indexOf("86")==0){
				return "86-"+phoneNum.substring(2);
			}else{
				return "86-"+phoneNum;
			}
		}else{
			return phoneNum;
		}
	}

    /**
     * 得到指定日期的下一天的开始时间
     * 
     * @param date 指定Date
     * @return 下一天的开始时间
     */
    public static Date beginOfNextDay(Date date)
    {
        date = nextDay(date);
        return beginOfDay(date);
    }

    /**
     * 得到指定日期的下一天
     * 
     * @param date 日期
     * @return 传入日期的下一天
     */
    public static Date nextDay(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();
        return date;
    }
	
	/**
     * 得到某一天的开始时间，精确到毫秒
     * 
     * @param date 日期
     * @return 某一天的0时0分0秒0毫秒的那个Date
     */
    public static Date beginOfDay(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        date = c.getTime();
        return date;
    }
	
	/**
     * 判断一个日期是否在指定的时间段内
     * 
     * @return String
     */
    public static boolean inTimeSegment(Date start, Date end, Date date)
    {
        Date s = beginOfDay(start);
        Date e = beginOfNextDay(end);
        if ((s.getTime() >= s.getTime()) && date.before(e))
        {
            return true;
        }
        return false;
    }
    
    /**
     * 获取最大key的value
     * @param map
     * @return
     */
    public static Long getMaxKeyValue(Map<Object, Long> map) {
    	if (map == null) return null;
        Set<Object> set = map.keySet();
        Object[] obj = set.toArray();
        Arrays.sort(obj);
        Object key = obj[obj.length - 1];
        return map.get(key);
    }
    
    /**
     * 随机字符串
     * @param length
     * @return
     */
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
        Random random = new Random();   
        StringBuffer sb = new StringBuffer();   
        for (int i = 0; i < length; i++) {   
            int number = random.nextInt(base.length());   
            sb.append(base.charAt(number));   
        }   
        return sb.toString();   
     }  
    
    /**
     * 获取今天 0 明天 1 后天 2 
     * @param type
     * @return
     */
    public static Long getTT(Integer type){
		Date now = new Date();   //当前时间
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(now);
		if(type == 0){ //今天
			calendar.add(Calendar.DAY_OF_MONTH, 0); 
		}else if(type == 1){ // 明天
			calendar.add(Calendar.DAY_OF_MONTH, 1); 
		}else if(type == 2){
			calendar.add(Calendar.DAY_OF_MONTH, 2); 
		}
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String monthstr = null;
		String daystr = null;
		if(month < 10){
			monthstr = "0" + month;
		}else{
			monthstr = month +"";
		}
		if(day < 10){
			daystr = "0" + day;
		}else{
			daystr = day +"";
		}
		Date d = null;
		try {
			d = CommonConstants.DATE.parse(year + "-" + monthstr + "-" + daystr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long datetime =Long.valueOf(CommonConstants.DATE_STR.format(d));
		return datetime;
    }

}
