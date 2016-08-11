package com.kjuns.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kjuns.annotation.IgnoreVerify;
import com.kjuns.out.BaseOutJB;
import com.kjuns.service.SystemService;
import com.kjuns.util.ErrorCode;
import com.kjuns.util.qniucloud.QiNiuUtils;

/**
 * <b>Function: </b> 系统   
 * @author James
 * @date 2015-8-19
 * @file SystemController.java
 * @package com.kjuns.controller
 * @project kjuns
 * @version 2.0
 */
@Controller
@RequestMapping("/system")
public class SystemController extends BaseController{
	
	@Autowired
	private SystemService systemService;
	
	/**
	 * 获取青牛token
	 * @param model
	 */
	@IgnoreVerify
	@RequestMapping(value = "/requestUploadToken", method = RequestMethod.POST)
	public void requestUploadToken(Model model) throws Exception{
		try {
			String token = QiNiuUtils.getInstance().getNewToken();
			Map<String, Object> p = new HashMap<>();
			p.put("token", token);
 			sendResponseContent(model, ErrorCode.SUCCESS, p);
		} catch (Exception ex) {
			logger.error("requestUploadToken >>> {}", ex.getMessage());
			throw ex;
		}
	}
	
	/**
	 * 系统常量
	 */
	@IgnoreVerify
	@RequestMapping(value = "/config", method = RequestMethod.GET)
	public void appConfig(Double levelVer, Model model) {
		try{
			Map<String, Object> result = systemService.getConfigConstants(levelVer);
			BaseOutJB out =new BaseOutJB(ErrorCode.SUCCESS, result);
			sendResponseContent(model, out);
		} catch (Exception ex) {
			logger.error("appConfig >>> {}", ex.getMessage());
			throw ex;
		}
	}
	
	/**
	 * 版本升级 // 123:Android 123:ios
	 */
	@RequestMapping(value = "/upgrade", method = RequestMethod.GET)
	public void upgrade(String ver, String platform, Model model) {
		try {
			String userAgent = ver + ":" + platform;
			BaseOutJB out = systemService.getUpgradeInfo(userAgent);
 			sendResponseContent(model, out);
		} catch (Exception ex) {
			logger.error("upgrade >>> {}", ex.getMessage());
			throw ex;
		}
	}
	
}
