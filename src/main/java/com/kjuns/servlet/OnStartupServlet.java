package com.kjuns.servlet;

import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kjuns.validation.FieldValiRule;
import com.kjuns.validation.Vali;
import com.kjuns.validation.ValidationUrlMapping;


/**
 * Servlet implementation class OnStartupServlet
 */
public class OnStartupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//private UserService userService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OnStartupServlet() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		logger.info("初始化系统数据 init...");
		logger.info("初始化校验规则...");
		for (Map.Entry<String, FieldValiRule[]>  entry : ValidationUrlMapping.urlValiMapping.entrySet()) {
			String url = entry.getKey();
			FieldValiRule[] fvrs = entry.getValue();
			logger.info("Validate target:{}", url);
			for (FieldValiRule fvr : fvrs) {
				logger.info("Fields:'{}', Type:'{}'", fvr.getName(), fvr.getDataType());
				Map<String,Vali> rules = fvr.getRules();
				logger.info("Rules:");
				for (Map.Entry<String,Vali> ruleEntry : rules.entrySet()) {
				    Vali vali = ruleEntry.getValue();
					logger.info("rule='{}',value='{}',msg='{}'", ruleEntry.getKey(), vali.getValue(), vali.getMsg());
				}
			}
		}
		
//		logger.info("初始化APP版本信息...");
//		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext()); 
//		VersionService versionService = (VersionService) wac.getBean("versionService");
//		List<Version> listAndriodVer = versionService.queryAndriodVer();
//		List<Version> listIosVer = versionService.queryIosVer();
//		
//		DefaultInterceptor.andriodLatestVer = listAndriodVer.get(0).getInternalNo();
//		DefaultInterceptor.iosLatestVer = listIosVer.get(0).getInternalNo();
//				
//		for (Version version : listAndriodVer) {
//			DefaultInterceptor.andriodVerMap.put(version.getInternalNo(), version.getForceUpdate());
//		}
//		for (Version version : listIosVer) {
//			DefaultInterceptor.iosVerMap.put(version.getInternalNo(), version.getForceUpdate());
//		}
//		logger.info("Andriod版本：{}",DefaultInterceptor.andriodVerMap);
//		logger.info("IOS版本：{}",DefaultInterceptor.iosVerMap);
//		logger.info("APP当前最新版本号：Andriod:[{}],IOS:[{}]",DefaultInterceptor.andriodLatestVer,DefaultInterceptor.iosLatestVer);
//		
//		DefaultInterceptor.andriodDownLink = versionService.getLatestAndriodDownLink();
//		logger.info("得到最新的app下载地址：{}",DefaultInterceptor.andriodDownLink);
//		
		//DefaultInterceptor.andriodVer = versionService.queryAndriodVer(); 
		//DefaultInterceptor.iosVer = versionService.queryIosVer(); 
		
		/*Map<String, Integer> verInfo = versionService.queryLatestVersionInfo();
		DefaultInterceptor.andriodVer = verInfo.get("andriodVer");
		DefaultInterceptor.iosVer = verInfo.get("iosVer");*/
		
	}

}
