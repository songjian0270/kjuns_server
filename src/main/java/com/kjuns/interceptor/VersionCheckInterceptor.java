package com.kjuns.interceptor; 

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kjuns.interceptor.base.BaseInterceptor;

/**
 * <b>Function: </b>     
 * @author James
 * @date 2015-07-28
 * @file VersionCheckInterceptor.java
 * @package com.kjuns.controller
 * @project kjuns
 * @version 1.0
 */
public class VersionCheckInterceptor extends BaseInterceptor {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
  //private static final String ENCODING_UTF8 = "UTF-8";
	private static final String VERSION_FORMAT_REG = "^(kjuns_\\d{1,10}:)(iPhone_.+|Android_.+)$";//"(kjuns_\\d{1,10}:iPhone_.+)|(kjuns_\\d{1,10}:Android_.+)";

	public static Integer andriodLatestVer = 0;
	public static Integer iosLatestVer = 0;/**/
	
	public static String andriodDownLink = null;
	public static final String iosDownLink = "https://itunes.apple.com/cn/app/870422896";
	
	/** version - forceUpdate */
	public static Map<Integer,Integer> andriodVerMap = new HashMap<>();
	/** version - forceUpdate */
	public static Map<Integer,Integer> iosVerMap = new HashMap<>();

	@SuppressWarnings("unused")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String userAgent = request.getHeader("User-Agent");
		logger.info("User-Agent:" + userAgent);
		
		if("AutoyolEs_web".equals(userAgent) || "AutoyolEs_console".equals(userAgent)){
			return true;
		}
		
		//request.getSession().setAttribute("userAgent", userAgent);
		if(!userAgent.matches(VERSION_FORMAT_REG)){
		//	sendResponseContent(response, ErrorCode.CLIENT_VERSION_LOW);
			return false;
		}
		
		int idx = userAgent.indexOf(":");
		Integer versionNo = Integer.valueOf(userAgent.substring(userAgent.indexOf("_")+1, idx));
		String platform = userAgent.substring(idx+1, userAgent.lastIndexOf("_"));
		
		Integer lastestVer = null;
		String downLink = null;
		Integer verForce = null;
		if("Android".equals(platform)){
			lastestVer = andriodLatestVer;
			downLink = andriodDownLink;
			verForce = andriodVerMap.get(versionNo);
		}else{
			lastestVer = iosLatestVer;
			downLink = iosDownLink;
			verForce = iosVerMap.get(versionNo);
		}
		
		if(verForce == null){
		//	sendResponseContentWithDownLink(response, ErrorCode.CLIENT_VERSION_LOW, downLink);
			return false;
		}else{
			if(versionNo > lastestVer){
			//	sendResponseContentWithDownLink(response, ErrorCode.CLIENT_VERSION_LOW, downLink);
				return false;
			}else{
				if(verForce == 1 && versionNo < lastestVer){
			//		sendResponseContentWithDownLink(response, ErrorCode.CLIENT_VERSION_LOW, downLink);
					return false;
				}
			}
		}
		
		return super.preHandle(request, response, handler);
	}
	
}
 