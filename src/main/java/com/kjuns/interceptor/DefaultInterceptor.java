package com.kjuns.interceptor; 

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.method.HandlerMethod;

import com.kjuns.annotation.IgnoreVerify;
import com.kjuns.interceptor.base.BaseInterceptor;
import com.kjuns.util.CommonUtils;
import com.kjuns.util.ErrorCode;
import com.kjuns.util.security.EssSecurityUtils;
import com.kjuns.validation.FieldValiRule;
import com.kjuns.validation.ValidationUrlMapping;
import com.kjuns.validation.Validator;

/**
 * <b>Function: </b>     
 * @author James
 * @date 2015-07-28
 * @file DefaultInterceptor.java
 * @package com.kjuns.controller
 * @project kjuns
 * @version 2.0
 */
public class DefaultInterceptor extends BaseInterceptor {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
//	private static final Map<String, String> RSA_KEY = new HashMap<String, String>() { 
//		private static final long serialVersionUID = -1388612799003153805L;{
//			put("kjuns", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyWeQnBDUSVcnz3Lh/QLxFrFnGmoYAkwfOdQuinGL+NM+9ILuB1YIttU11VShdCgrJre9wywMEAKBzx0yqmv4XQK1VgDu7S+CrzN0wpA9gGrkp5vZ88WYf2PHMQQWKf7eYsrDWpF47N2Tf2J9azWAc9LNzYEgOyIA2kWZpXRpjSk7N8hl0H5DqAHQ4oKL7MeGdRm34tQxAhMWuy681X3J6taR0E3odInZ9wVN1AC76TWrJFfO/d9G/FuwHINcw3Tmzw5/ljsD8Wcfeyk5RwHgHRKOk5ijrsVGqer5w6UODnsQx1tvR5GhJZK260NUzF+1pf6MvmEgKomCsp2L0zj+iwIDAQAB");		   	   
//		}
//	};
	
	private static final Map<String, String> MD5_KEY = new HashMap<String, String>() { 
		private static final long serialVersionUID = -1388612799003153805L;{
			put("kjuns", "QcZs3U5bpMdPCszaw3IbR8aA88FZsuLdLuVB2max9/mEO+YW7hNoHpW3nelGSyQpWzZSMwfbLEoVRVWEuqoPng==");		   	   
		}
	};
	
	private static final  Map<String, String> kjuns_NO = new HashMap<String, String>() {
		private static final long serialVersionUID = -1388612799003153805L;{
			put("kjuns-app", "kjuns");		   //app
	}};
		
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		request.setCharacterEncoding("utf-8");
		String reqMethod = request.getMethod();
		HandlerMethod hm = null;
		//输入校验
		LinkedHashMap<String, Object> data = null;
		if("GET".equals(reqMethod)){
			try {
			   	 hm = (HandlerMethod) handler;
			} catch (Exception e) {
				sendResponseContent(request, response, ErrorCode.CALL_URL_ERROR);
				return false;
			}  
			String valiKey = hm.getBeanType().getName() + "." + hm.getMethod().getName();
			data = handleRequestDate(request);
			//判断GET请求是否需要验证
			if(hm.getMethodAnnotation(IgnoreVerify.class) == null){
				if(!verify(request, response, valiKey, data)){
			   		return false;
			   	}	
			}	
		}
		
		if("POST".equals(reqMethod) || "PUT".equals(reqMethod) || "DELETE".equals(reqMethod)){
			String userAgent = request.getHeader("User-Agent");
			logger.info("DefaultInterceptor execute... User-Agent:" + userAgent);
			String kjunsNo = kjuns_NO.get(userAgent);
			try {
			   	 hm = (HandlerMethod) handler;
			} catch (Exception e) {
				sendResponseContent(request, response, ErrorCode.CALL_URL_ERROR);
				return false;
			}  
			if(hm.getMethodAnnotation(IgnoreVerify.class) == null){
				if(CommonUtils.isEmpty(kjunsNo)){//判断请求来源
					sendResponseContent(request, response, ErrorCode.CLIENT_INVALID);
					return false;
				}
			}
		  //HandlerMethod hm = (HandlerMethod) handler;
		   	String valiKey = hm.getBeanType().getName() + "." + hm.getMethod().getName();
		   	data = handleRequestDate(request);
		   	if(hm.getMethodAnnotation(IgnoreVerify.class) == null){
				if(!verify(request, response, valiKey, data)){
			   		return false;
			   	}			
				logger.info("********************************");
				//logger.info("SysConf.AOTU_CALL_IP_VATE:"+SysConf.AOTU_CALL_IP_VATE);
				logger.info("ApiUtils.getIpAddr(request):"+CommonUtils.getIpAddr(request));
				logger.info("********************************");
			    String signature = (String)data.remove("signature");
				//签名验证
			    TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
			    treeMap.putAll(data);
				if(!EssSecurityUtils.verifySign(treeMap, signature, MD5_KEY.get(kjunsNo))){
				  	logger.error("验签错误，reqData:{}", data);
//				  	sendResponseContent(request, response, ErrorCode.VERIFY_SIGNATURE_ERROR);
//					return false;
				}
			
//				String ip = CommonUtils.getIpAddr(request);
//				String url = request.getRequestURL().toString();
//				Object repeatSubmi = data.get("repeatSubmi");
//				if(CommonUtils.notEmpty(repeatSubmi)){
					//sendResponseContent(request, response, ErrorCode.PARAM_ERROR, "参数repeatSubmi不能为空!");
					//return false;
//					String key = "req_submit_".concat(url).concat(ip).concat(repeatSubmi.toString());
//					long count = JedisPoolCacheUtils.incrBy(key, 1L);
//					JedisPoolCacheUtils.expire(key, 30);
//					if (count > 1) {
////						logger.error("不能重复提交");
////						sendResponseContent(request, response, ErrorCode.REPEAT_SUBMIT_ERROR, "不能重复提交");
////						return false;
//					}
	//			}

		    //RSA
		//		signature = signature.replace(" ", "+");
		//		if(SecurityUtils.verifySign(data, signature, RSA_KEY.get(kjunsNo)) == false){
		//			logger.error("验签错误，reqData:{}", data);
		//			sendResponseContent(request, response, ErrorCode.ES_VERIFY_SIGN_ERROR);
		//			return false;
		//		}
				request.setAttribute("content", data);
		   	}
		}
		return super.preHandle(request, response, handler);
	}
		
	private boolean verify(HttpServletRequest request, HttpServletResponse response, String valiKey, LinkedHashMap<String, Object> data) throws Exception{
		String msg = null;
		FieldValiRule[] fvr = ValidationUrlMapping.urlValiMapping.get(valiKey);
		if (fvr != null) {
			if ((msg = Validator.validateDataMap(data, fvr)) != null) {
				logger.error("输入错误：{}",msg);
				sendResponseContent(request, response, ErrorCode.PARAM_ERROR, msg);
				return false;	
			}
		} else {
			logger.error("FieldValiRule is null !");
			sendResponseContent(request, response, ErrorCode.SYS_ERROR);
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	private LinkedHashMap<String,Object> handleRequestDate(HttpServletRequest request) throws UnsupportedEncodingException{
		LinkedHashMap<String,Object> data = new LinkedHashMap<>();//有序的map，顺序同validation的xml文件中配置的字段顺序，即字段校验顺序
		Enumeration<String> pNames = request.getParameterNames();
		while(pNames.hasMoreElements()){
			String fieldName = pNames.nextElement();
			String fieldVal = request.getParameter(fieldName);
			data.put(fieldName, fieldVal);
		}
		return data;
	}
	
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			Model model) throws Exception {
		logger.info("所有值"+model.asMap().entrySet());
		logger.info("postHandle执行完整......");
	}
	
	public void afterCompletion(    
	    HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)    
	       throws Exception { 
		logger.info("afterCompletion执行完整......");
	}    
	
}
 